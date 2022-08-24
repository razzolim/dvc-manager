package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.ServiceCost;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceCostRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCostServiceImplTest {

    @Mock
    private ServiceCostRepository repository;

    @InjectMocks
    private ServiceCostServiceImpl service;

    private ServiceCost cost;

    @BeforeEach
    void setup() {
        this.cost = ServiceCost.builder().build();
    }

    @Test
    void save() {
        when(this.repository.save(any(ServiceCost.class))).thenReturn(cost);

        var persistedCost = this.service.save(cost);

        assertNotNull(persistedCost);
        assertEquals(cost.getId(), persistedCost.getId());
    }

    @Test
    void saveWhenAlreadyExists() {
        when(this.repository.save(any(ServiceCost.class))).thenThrow(DataIntegrityViolationException.class);

        var exception = assertThrows(EntityAlreadyExistsException.class, () -> {
           this.service.save(cost);
        });

        assertNotNull(exception);
        assertEquals("This service cost already exists for this type of device and service.", exception.getMessage());
    }

    @Test
    void findAll() {
        when(this.repository.findAll()).thenReturn(List.of(cost));

        var list = this.service.findAll();

        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void findById() {
        when(this.repository.findById(anyLong())).thenReturn(Optional.of(cost));

        var returnedCost = this.service.findById(1L);

        assertNotNull(returnedCost);
    }

    @Test
    void findByIdWhenNotExists() {
        when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> {
            this.service.findById(1L);
        });

        assertNotNull(exception);
        assertEquals("No service cost with ID '1' found.",exception.getMessage());
    }

    @Test
    void delete() {
        this.service.delete(cost);
        verify(this.repository, atMostOnce()).delete(any(ServiceCost.class));
    }

}