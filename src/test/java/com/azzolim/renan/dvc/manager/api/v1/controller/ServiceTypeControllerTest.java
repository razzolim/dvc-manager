package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceTypeInput;
import com.azzolim.renan.dvc.manager.domain.service.impl.ServiceTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ServiceTypeControllerTest extends ControllerTest {

    private static final String RESOURCE = "/v1/service-types";

    @MockBean
    private ServiceTypeServiceImpl service;


    @Test
    void shouldSaveNewServiceType() throws Exception {
        ServiceTypeInput input = new ServiceTypeInput();
        input.setName("Teste");

        mockMvc.perform(post(RESOURCE)
                .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

}