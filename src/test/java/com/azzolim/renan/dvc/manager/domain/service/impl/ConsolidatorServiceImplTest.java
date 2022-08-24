package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsolidatorServiceImplTest {

    @Mock
    private DeviceRepository repository;

    @Mock
    private DeviceServiceImpl deviceService;

    @InjectMocks
    private ConsolidatorServiceImpl service;

    @Test
    void consolidateAll() {
        when(this.repository.sumAllServices()).thenReturn(BigDecimal.TEN);

        var total = this.service.consolidateAll();

        assertNotNull(total);
        assertEquals(BigDecimal.TEN, total);
    }

    @Test
    void consolidateByIdWhenRecordExists() {
        when(this.repository.sumAllServicesByDeviceId(anyLong())).thenReturn(BigDecimal.TEN);
        var total = this.service.consolidateByDevice(1L);

        assertNotNull(total);
        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    void consolidateByIdWhenRecordDoesNotExist() {
        when(this.repository.sumAllServicesByDeviceId(anyLong())).thenReturn(null);
        when(this.deviceService.findById(anyLong()))
                .thenThrow(new EntityNotFoundException(String.format("No device with ID '1' found.")));

        var exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.service.consolidateByDevice(1L);
        });

        assertEquals("No device with ID '1' found.", exception.getMessage());
    }

}