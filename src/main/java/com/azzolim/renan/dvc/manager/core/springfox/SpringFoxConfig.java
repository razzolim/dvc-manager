package com.azzolim.renan.dvc.manager.core.springfox;

import com.azzolim.renan.dvc.manager.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("v1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.azzolim.renan.dvc.manager.api"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class,
                        File.class, InputStream.class)
                .apiInfo(apiInfoV1())
                .tags(new Tag("Device Types", "Manages device types"),
                        new Tag("Devices", "Manages all devices"),
                        new Tag("Service Types", "Manages all service types"),
                        new Tag("Services", "Manages all services"),
                        new Tag("Service Costs", "Manages all cost for each service"),
                        new Tag("Consolidator", "Presents monthly visualization of costs"));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .responseModel(new ModelRef("Problem"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Resource doesn't have representation to be consumed.")
                        .build()
        );
    }

    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid requisition (client error)")
                        .responseModel(new ModelRef("Problem"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .responseModel(new ModelRef("Problem"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Resource doesn't have representation to be consumed.")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Refused requisition due to body in not supported format.")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.CONFLICT.value())
                        .message("Record already exists.")
                        .responseModel(new ModelRef("Problem"))
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid requisition (client error)")
                        .responseModel(new ModelRef("Problem"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .responseModel(new ModelRef("Problem"))
                        .build()
        );
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("Remote Monitoring and Management")
                .description("It is a platform that helps IT professionals manage a " +
                        "fleet of Devices with Services associated with them.")
                .version("1")
                .contact(new Contact("Renan Azzolim", "https://www.linkedin.com/in/renan-azzolim/", "azzolimrenan2@gmail.com"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
