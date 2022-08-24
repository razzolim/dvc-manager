package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.domain.service.impl.ConsolidatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ConsolidatorControllerTest extends ControllerTest {

    private static final String RESOURCE = "/v1/consolidator/costs";

    @MockBean
    private ConsolidatorServiceImpl service;

    @Test
    void consolidateForAll() throws Exception {
        Mockito.when(this.service.consolidateAll()).thenReturn(BigDecimal.TEN);

        mockMvc.perform(get(RESOURCE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(BigDecimal.TEN));
    }

    @Test
    void consolidateById() throws Exception {
        Mockito.when(this.service.consolidateByDevice(anyLong())).thenReturn(BigDecimal.TEN);

        mockMvc.perform(get(RESOURCE + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(BigDecimal.TEN));
    }

}