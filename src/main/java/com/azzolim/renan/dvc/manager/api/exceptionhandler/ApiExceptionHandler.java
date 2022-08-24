package com.azzolim.renan.dvc.manager.api.exceptionhandler;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO ADD TRATAMENTO JAVAX

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Problem.builder().timestamp(OffsetDateTime.now()).title(status.getReasonPhrase())
                    .status(status.value()).build();
        } else if (body instanceof String) {
            body = Problem.builder().timestamp(OffsetDateTime.now()).title((String) body).status(status.value()).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleRecordAlreadyExists(EntityAlreadyExistsException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problemType = ProblemType.ALREADY_EXISTS;
        var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problemType = ProblemType.NOT_FOUND;
        var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problemType = ProblemType.IN_USE;
        var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problemType = ProblemType.ALREADY_EXISTS;
        final var detail = "According to the informed params, a record already exists.";
        var problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.UNKNOWN_ERROR;
        String detail = "Unexpected error has happened. Try again and if the problem persists, please contact admin.";

        log.error(ex.getMessage(), ex);

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "One or more fields are incorrect. Fill it correctly and try again.";

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

            String name = objectError.getObjectName();

            if (objectError instanceof FieldError) {
                name = ((FieldError) objectError).getField();
            }

            return Problem.Object.builder().name(name).userMessage(message).build();
        }).collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detail).objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                    HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_PARAM;

        String detail = String.format(
                "URL parameter '%s' received '%s', "
                        + "which is invalid. Please inform a compatible value according to %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
