package com.azzolim.renan.dvc.manager.domain.service.impl;

import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.model.Service;
import com.azzolim.renan.dvc.manager.domain.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @InjectMocks
    private DeviceServiceImpl service;

    @Mock
    private DeviceRepository repository;

    @Mock
    private DeviceTypeServiceImpl dtService;


    @Test
    void save() {
        final var name = "Teste";
        var deviceType = DeviceType.builder().name(name).build();
        var device = Device.builder().id(1L).systemName(name).deviceType(deviceType).build();
        when(this.repository.save(any(Device.class))).thenReturn(device);

        Device persistedDevice = this.service.save(device);

        assertNotNull(persistedDevice);
        assertEquals(device.getId(), persistedDevice.getId());
        assertEquals(device.getSystemName(), persistedDevice.getSystemName());

        assertNotNull(persistedDevice.getDeviceType());
        assertEquals(device.getDeviceType().getId(), persistedDevice.getDeviceType().getId());
        assertEquals(device.getDeviceType().getName(), persistedDevice.getDeviceType().getName());
    }

    @Test
    void saveThrowsEntityAlreadyExistsException() {
        final var name = "Teste";
        var deviceType = DeviceType.builder().name(name).build();
        var device = Device.builder().id(1L).systemName(name).deviceType(deviceType).build();
        when(this.repository.save(any(Device.class))).thenThrow(DataIntegrityViolationException.class);

        var exception = assertThrows(EntityAlreadyExistsException.class, () -> {
           this.service.save(device);
        });

        assertEquals("Device already registered.", exception.getMessage());
    }

    @Test
    void findAll() {
        final var name = "Teste";
        var deviceType = DeviceType.builder().name(name).build();
        var device = Device.builder().id(1L).systemName(name).deviceType(deviceType).build();

        when(this.repository.findAll()).thenReturn(List.of(device));

        var list = this.service.findAll();

        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(device.getId(), list.get(0).getId());
    }

    @Test
    void findById() {
        final var name = "Teste";
        var deviceType = DeviceType.builder().name(name).build();
        var device = Device.builder().id(1L).systemName(name).deviceType(deviceType).build();

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(device));

        var returnedDevice = this.service.findById(1L);

        assertNotNull(returnedDevice);
        assertEquals(device.getId(), returnedDevice.getId());
        assertEquals(device.getDeviceType().getId(), returnedDevice.getDeviceType().getId());
    }

    @Test
    void findByIdAndThrowEntityNotFoundException() {
        when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

        var exception = assertThrows(EntityNotFoundException.class, () -> {
            this.service.findById(1L);
        });

        assertEquals("No device with ID '1' found.", exception.getMessage());
    }

    @Test
    void delete() {
        final var name = "Teste";
        var deviceType = DeviceType.builder().name(name).build();
        var device = Device.builder().id(1L).systemName(name).deviceType(deviceType).build();

        this.service.delete(device);

        verify(this.repository, atMostOnce()).delete(any(Device.class));
    }

    @Test
    void deleteShouldThrowEntityInUseException() {
        doThrow(DataIntegrityViolationException.class).when(this.repository).delete(any(Device.class));
        final var name = "Teste";
        var deviceType = DeviceType.builder().name(name).build();
        var device = Device.builder().id(1L).systemName(name).deviceType(deviceType).build();

        var exception = assertThrows(EntityInUseException.class, () -> {
            this.service.delete(device);
        });

        assertEquals("This record cannot be deleted because it is in use by another entity.", exception.getMessage());
    }

    @Test
    void update() {
        var deviceType = DeviceType.builder().id(1L).name("dt1").build();
        var device = Device.builder().id(1L).systemName("d1").deviceType(deviceType).build();

        var dt2 = DeviceType.builder().id(2L).name("dt2").build();
        var dev2 = Device.builder().systemName("d2").deviceType(dt2).build();

        var mockUpdated = Device.builder().id(1L).systemName("d2").deviceType(dt2).build();

        when(this.repository.findById(anyLong())).thenReturn(Optional.of(device));
        when(this.dtService.findById(anyLong())).thenReturn(dt2);
        when(this.repository.save(any(Device.class))).thenReturn(mockUpdated);

        Device updatedDevice = this.service.update(1L, dev2);

        assertNotNull(updatedDevice);
        assertEquals(device.getId(), updatedDevice.getId());
        assertEquals(dev2.getDeviceType().getId(), updatedDevice.getDeviceType().getId());
    }

    @Test
    void addService() {
        var device = Device.builder().services(new HashSet<>()).build();
        var service = Service.builder().id(1L).build();
        this.service.addService(device, service);

        verify(this.repository, atMostOnce()).save(any(Device.class));
    }

    @Test
    void addServiceWhenAlreadyExists() {
        var service = Service.builder().id(1L).build();
        var set = new HashSet<Service>();
        set.add(service);
        var device = Device.builder().services(set).build();

        assertThrows(EntityAlreadyExistsException.class, () -> {
            this.service.addService(device, service);
        });

        verify(this.repository, never()).save(any(Device.class));
    }

    @Test
    void removeService() {
        var service = Service.builder().id(1L).build();
        var set = new HashSet<Service>();
        set.add(service);
        var device = Device.builder().services(set).build();

        this.service.removeService(device, 1L);

        verify(this.repository, atMostOnce()).save(any(Device.class));
    }

    @Test
    void removeServiceWhenDoesNotExist() {
        var device = Device.builder().services(new HashSet<Service>()).build();

        assertThrows(EntityNotFoundException.class, () -> {
            this.service.removeService(device, 1L);
        });

        verify(this.repository, never()).save(any(Device.class));
    }

}