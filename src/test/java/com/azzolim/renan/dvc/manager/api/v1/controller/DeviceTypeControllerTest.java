package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.exceptionhandler.ProblemType;
import com.azzolim.renan.dvc.manager.api.v1.controller.in.DeviceTypeInput;
import com.azzolim.renan.dvc.manager.domain.exception.EntityInUseException;
import com.azzolim.renan.dvc.manager.domain.exception.EntityNotFoundException;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.service.impl.DeviceTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeviceTypeControllerTest extends ControllerTest {

    private static final String RESOURCE = "/v1/device-types";

    @MockBean
    private DeviceTypeServiceImpl service;

    @Test
    void shouldSaveNewDeviceType() throws Exception {
        final var deviceName = "Mac";

        DeviceTypeInput requestBody = new DeviceTypeInput(deviceName);
        DeviceType deviceType = DeviceType.builder().id(1L).name(deviceName).build();

        when(service.save(any(DeviceType.class))).thenReturn(deviceType);

        mockMvc.perform(post(RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(deviceType.getId()))
                .andExpect(jsonPath("$.name").value(deviceType.getName()));
    }

    @Test
    void shouldRetrieveAListOfDeviceTypes() throws Exception {
        var dt1 = DeviceType.builder().id(1L).name("DeviceTypeOne").build();
        var dt2 = DeviceType.builder().id(2L).name("DeviceTypeTwo").build();
        when(service.findAll()).thenReturn(List.of(dt1, dt2));

        mockMvc.perform(get(RESOURCE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[1].id").value(2L));
    }

    @Test
    void findById() throws Exception {
        var dt1 = DeviceType.builder().id(1L).name("DeviceTypeOne").build();
        when(service.findById(anyLong())).thenReturn(dt1);

        mockMvc.perform(get(RESOURCE + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dt1.getId()))
                .andExpect(jsonPath("$.name").value(dt1.getName()));
    }

    @Test
    void findByIdShouldThrow404() throws Exception {
        when(service.findById(anyLong())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get(RESOURCE + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value(ProblemType.NOT_FOUND.getTitle()));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(RESOURCE + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldThrow409() throws Exception {
        when(service.findById(anyLong())).thenReturn(DeviceType.builder().id(1L).name("DeviceTypeOne").build());
        doThrow(EntityInUseException.class).when(service).delete(any(DeviceType.class));
        mockMvc.perform(MockMvcRequestBuilders.delete(RESOURCE + "/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value(ProblemType.IN_USE.getTitle()));
    }

}
