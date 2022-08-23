package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.exceptionhandler.ProblemType;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.DeviceInput;
import com.azzolim.renan.dvc.manager.domain.exception.EntityAlreadyExistsException;
import com.azzolim.renan.dvc.manager.domain.model.Device;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.service.impl.DeviceServiceImpl;
import com.azzolim.renan.dvc.manager.domain.service.impl.DeviceTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DeviceControllerTest extends ControllerTest {

    private static final String RESOURCE = "/v1/devices";

    @MockBean
    private DeviceServiceImpl service;

    @MockBean
    private DeviceTypeServiceImpl dtService;

    @Test
    void save() throws Exception {
        final var name = "Teste";
        var deviceType = DeviceType.builder().id(1L).name(name).build();
        var device = Device.builder().id(1L).systemName(name).deviceType(deviceType).build();
        Mockito.when(this.dtService.findById(anyLong())).thenReturn(deviceType);
        Mockito.when(this.service.save(Mockito.any(Device.class))).thenReturn(device);

        DeviceInput input = new DeviceInput();
        input.setSystemName(name);
        input.setDeviceTypeId(1L);

        mockMvc.perform(post(RESOURCE)
                        .content(this.mapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.systemName").value(name))
                .andExpect(jsonPath("$.deviceType.id").value(1L))
                .andExpect(jsonPath("$.deviceType.name").value(name));
    }

    @Test
    void saveShouldThrow409() throws Exception {
        final var name = "Teste";
        var deviceType = DeviceType.builder().id(1L).name(name).build();
        var device = Device.builder().id(1L).systemName(name).deviceType(deviceType).build();
        Mockito.when(this.dtService.findById(anyLong())).thenReturn(deviceType);

        DeviceInput input = new DeviceInput();
        input.setSystemName(name);
        input.setDeviceTypeId(1L);

        when(this.service.save(any(Device.class))).thenThrow(EntityAlreadyExistsException.class);

        mockMvc.perform(post(RESOURCE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.mapper.writeValueAsString(device)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value(ProblemType.ALREADY_EXISTS.getTitle()));
    }

    @Test
    void findAll() throws Exception {
        var dt1 = DeviceType.builder().name("dt1").build();
        var d1 = Device.builder().id(1L).systemName("d1").deviceType(dt1).build();

        var dt2 = DeviceType.builder().name("dt2").build();
        var d2 = Device.builder().id(1L).systemName("d2").deviceType(dt2).build();

        when(this.service.findAll()).thenReturn(List.of(d1, d2));

        mockMvc.perform(get(RESOURCE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(d1.getId()))
                .andExpect(jsonPath("$.[0].deviceType.id").value(dt1.getId()));
    }

    @Test
    void findById() throws Exception {
        var dt1 = DeviceType.builder().name("dt1").build();
        var d1 = Device.builder().id(1L).systemName("d1").deviceType(dt1).build();

        when(service.findById(anyLong())).thenReturn(d1);

        mockMvc.perform(get(RESOURCE + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(d1.getId()))
                .andExpect(jsonPath("$.systemName").value(d1.getSystemName()))
                .andExpect(jsonPath("$.deviceType.id").value(d1.getDeviceType().getId()));
    }

    @Test
    void delete() throws Exception {
        var dt1 = DeviceType.builder().name("dt1").build();
        var d1 = Device.builder().id(1L).systemName("d1").deviceType(dt1).build();
        when(service.findById(anyLong())).thenReturn(d1);

        mockMvc.perform(MockMvcRequestBuilders.delete(RESOURCE + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void update() throws Exception {
        var input = new DeviceInput();
        input.setSystemName("Test");
        input.setDeviceTypeId(1L);

        var dt1 = DeviceType.builder().name("dt1").build();
        var d1 = Device.builder().id(1L).systemName("d1").deviceType(dt1).build();

        when(this.service.update(anyLong(), any(Device.class))).thenReturn(d1);

        mockMvc.perform(MockMvcRequestBuilders.put(RESOURCE + "/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.mapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(d1.getId()))
                .andExpect(jsonPath("$.deviceType.id").value(d1.getDeviceType().getId()));
    }

}