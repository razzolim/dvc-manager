package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceTypeInput;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
import com.azzolim.renan.dvc.manager.domain.service.impl.ServiceTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ServiceTypeControllerTest extends ControllerTest {

    private static final String RESOURCE = "/v1/service-types";

    @MockBean
    private ServiceTypeServiceImpl service;


    @Test
    void shouldSaveNewServiceType() throws Exception {
        when(this.service.save(Mockito.any(ServiceType.class)))
                .thenReturn(ServiceType.builder().build());
        ServiceTypeInput input = new ServiceTypeInput();
        input.setName("Teste");

        mockMvc.perform(post(RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    void findAll() throws Exception {
        when(this.service.findAll()).thenReturn(List.of(ServiceType.builder().build()));

        mockMvc.perform(get(RESOURCE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}