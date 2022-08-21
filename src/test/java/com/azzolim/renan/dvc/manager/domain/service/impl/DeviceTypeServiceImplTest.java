package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceTypeServiceImplTest {

    private static final String DEVICE_TYPE_NAME = "Test";

    @Mock
    private DeviceTypeRepository repository;

    @InjectMocks
    private DeviceTypeServiceImpl service;

    @Test
    void whenSaveNewDeviceTypeShouldReturnValidObject() {
        when(repository.save(Mockito.any(DeviceType.class)))
                .thenReturn(DeviceType.builder().id(1L).name(DEVICE_TYPE_NAME).build());

        DeviceType savedDeviceType = service.save(DeviceType.builder().name(DEVICE_TYPE_NAME).build());

        Assertions.assertNotNull(savedDeviceType);
        Assertions.assertNotNull(savedDeviceType.getId());
        Assertions.assertEquals(DEVICE_TYPE_NAME, savedDeviceType.getName());
    }

    @Test
    void whenSaveDuplicatedDeviceTypeShouldThrowEntityAlreadyExistsException() {
        when(repository.save(Mockito.any(DeviceType.class))).thenThrow(new DataIntegrityViolationException(""));

        var exception = Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            this.service.save(DeviceType.builder().name(DEVICE_TYPE_NAME).build());
        });

        Assertions.assertEquals("Device type with name Test already exists.", exception.getMessage());
    }

    @Test
    void findAll() {
        var dt1 = DeviceType.builder().id(1L).name("DeviceTypeOne").build();
        var dt2 = DeviceType.builder().id(2L).name("DeviceTypeTwo").build();
        when(repository.findAll()).thenReturn(List.of(dt1, dt2));

        var list = this.service.findAll();

        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void findById() {
        var deviceType = DeviceType.builder().id(1L).name("DeviceName").build();
        when(repository.findById(anyLong())).thenReturn(Optional.of(deviceType));

        DeviceType returnedDeviceType = this.service.findById(1L);

        Assertions.assertNotNull(returnedDeviceType);
        Assertions.assertEquals(1L, returnedDeviceType.getId());
        Assertions.assertEquals("DeviceName", returnedDeviceType.getName());
    }

    @Test
    void findByIdShouldThrowEntityNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            this.service.findById(1L);
        });

        Assertions.assertEquals("No device type with ID '1' found.", exception.getMessage());
    }

    @Test
    void delete() {
        this.service.delete(DeviceType.builder().build());
        verify(repository, atMostOnce()).delete(any(DeviceType.class));
    }

    @Test
    void deleteShouldThrowEntityInUseException() {
        doThrow(DataIntegrityViolationException.class).when(this.repository).delete(any(DeviceType.class));

        var exception = Assertions.assertThrows(EntityInUseException.class, () -> {
            this.service.delete(DeviceType.builder().build());
        });

        Assertions.assertEquals("This record cannot be deleted because it is in use by another entity.", exception.getMessage());
    }
}