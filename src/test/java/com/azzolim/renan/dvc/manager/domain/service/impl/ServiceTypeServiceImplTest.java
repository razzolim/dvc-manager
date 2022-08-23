package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTypeServiceImplTest {

    @Mock
    private ServiceTypeRepository repository;

    @InjectMocks
    private ServiceTypeServiceImpl service;

    @Test
    void shouldSaveServiceType() {
        var st = ServiceType.builder().id(1L).name("Test").build();
        when(this.repository.save(any(ServiceType.class))).thenReturn(st);

        var stToPersist = ServiceType.builder().name("Test").build();
        var persistedST = this.service.save(stToPersist);

        assertNotNull(persistedST);
        assertEquals(1L, persistedST.getId());
        assertEquals(st.getName(), persistedST.getName());
    }

    @Test
    void shouldNotSaveAndThrowEntityAlreadyExists() {
        when(this.repository.save(any(ServiceType.class))).thenThrow(DataIntegrityViolationException.class);

        var exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            this.service.save(ServiceType.builder().build());
        });

        assertNotNull(exception);
        assertEquals("Service type already registered.", exception.getMessage());
    }

    @Test
    void findById() {
        var st = ServiceType.builder().id(1L).name("Test").build();
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(st));

        var foundST = this.service.findById(1L);

        assertNotNull(foundST);
        assertEquals(st.getId(), foundST.getId());
        assertEquals(st.getName(), foundST.getName());
    }

    @Test
    void shouldNotFindByIdAndThrowEntityNotFoundException() {
        when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> {
            this.service.findById(1L);
        });

        assertNotNull(exception);
        assertEquals("No service type with ID '1' found.", exception.getMessage());
    }

    @Test
    void findAll() {
        var st1 = ServiceType.builder().id(1L).name("Test").build();
        var st2 = ServiceType.builder().id(1L).name("Test").build();

        when(this.repository.findAll()).thenReturn(List.of(st1, st2));

        var list = this.service.findAll();

        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void delete() {
        this.service.delete(ServiceType.builder().build());
        verify(this.repository, atMostOnce()).delete(any(ServiceType.class));
    }

    @Test
    void shouldNotDeleteAndThrowEntityInUseException() {
        doThrow(DataIntegrityViolationException.class).when(this.repository).delete(any(ServiceType.class));

        var exception = assertThrows(EntityInUseException.class, () -> {
            this.service.delete(ServiceType.builder().build());
        });

        assertNotNull(exception);
        assertEquals("This record cannot be deleted because it is in use by another entity.", exception.getMessage());


    }

}