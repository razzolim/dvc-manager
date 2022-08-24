package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceCostInput;
import com.azzolim.renan.dvc.manager.domain.model.DeviceType;
import com.azzolim.renan.dvc.manager.domain.model.Service;
import com.azzolim.renan.dvc.manager.domain.model.ServiceCost;
import com.azzolim.renan.dvc.manager.domain.service.impl.DeviceTypeServiceImpl;
import com.azzolim.renan.dvc.manager.domain.service.impl.ServiceCostServiceImpl;
import com.azzolim.renan.dvc.manager.domain.service.impl.ServiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ServiceCostControllerTest extends ControllerTest {

    private static final String RESOURCE = "/v1/service-costs";

    @MockBean
    private ServiceCostServiceImpl serviceCostService;

    @MockBean
    private ServiceServiceImpl serviceServiceImpl;

    @MockBean
    private DeviceTypeServiceImpl deviceTypeService;

    private Service service = Service.builder().id(1L).build();

    private DeviceType devType = DeviceType.builder().id(1L).build();

    private ServiceCost cost = ServiceCost.builder().id(1L).build();

    @Test
    void save() throws Exception {
        cost.setDeviceType(devType);
        cost.setService(service);
        when(this.serviceServiceImpl.findById(anyLong())).thenReturn(service);
        when(this.deviceTypeService.findById(anyLong())).thenReturn(devType);
        when(this.serviceCostService.save(any(ServiceCost.class))).thenReturn(cost);

        var input = new ServiceCostInput();
        input.setServiceId(1L);
        input.setDeviceTypeId(1L);
        input.setAmount(BigDecimal.TEN);

        mockMvc.perform(post(RESOURCE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cost.getId()));
    }

    @Test
    void findAll() throws Exception {
        when(this.serviceCostService.findAll()).thenReturn(List.of(cost, cost));

        mockMvc.perform(get(RESOURCE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(service.getId()));
    }

    @Test
    void findById() throws Exception {
        when(this.serviceCostService.findById(anyLong())).thenReturn(cost);

        mockMvc.perform(get(RESOURCE + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(service.getId()));
    }

    @Test
    void delete() throws Exception {
        when(this.serviceCostService.findById(anyLong())).thenReturn(cost);

        mockMvc.perform(MockMvcRequestBuilders.delete(RESOURCE + "/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

}