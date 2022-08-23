package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.Service;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceImplTest {

    @Mock
    private ServiceRepository repository;

    @InjectMocks
    private ServiceServiceImpl serviceImpl;

    private Service service;

    @BeforeEach
    void setUp() {
        var st = ServiceType.builder().name("st1").build();
        this.service = Service.builder().name("s1").serviceType(st).build();
    }

    @Test
    void save() {
        when(this.repository.save(Mockito.any(Service.class))).thenReturn(service);

        var persistedService = this.serviceImpl.save(service);

        assertNotNull(persistedService);
        assertEquals(service.getId(), persistedService.getId());
        assertEquals(service.getServiceType().getId(), persistedService.getServiceType().getId());
    }

    @Test
    void shouldNotSaveAndThrowEntityAlreadyExists() {
        when(this.repository.save(any(Service.class))).thenThrow(DataIntegrityViolationException.class);

        var exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            this.serviceImpl.save(service);
        });

        assertNotNull(exception);
        assertEquals("Service already registered.", exception.getMessage());
    }

    @Test
    void findAll() {
        when(this.repository.findAll()).thenReturn(List.of(service, service));

        var list = this.serviceImpl.findAll();

        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void findBy() {
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(service));

        var returnedService = this.serviceImpl.findById(1L);

        assertNotNull(returnedService);
        assertEquals(service.getId(), returnedService.getId());
        assertEquals(service.getServiceType().getId(), returnedService.getServiceType().getId());
    }

    @Test
    void shouldNotFindByIdAndThrowEntityNotFound() {
        when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> {
           this.serviceImpl.findById(1L);
        });

        assertNotNull(exception);
        assertEquals("No service with ID '1' found.", exception.getMessage());
    }

    @Test
    void delete() {
        this.serviceImpl.delete(service);
        verify(this.repository, atMostOnce()).delete(any(Service.class));
    }

    @Test
    void shouldNotDeleteAndThrowEntityInUseException() {
        doThrow(DataIntegrityViolationException.class).when(this.repository).delete(any(Service.class));

        var exception = assertThrows(EntityInUseException.class, () -> {
            this.serviceImpl.delete(service);
        });

        assertNotNull(exception);
        assertEquals("This record cannot be deleted because it is in use by another entity.", exception.getMessage());
    }

}