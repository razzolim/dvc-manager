package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.model.ServiceCost;
import com.azzolim.renan.dvc.manager.domain.repository.ServiceCostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void delete() {
        this.service.delete(cost);
        verify(this.repository, atMostOnce()).delete(any(ServiceCost.class));
    }

}