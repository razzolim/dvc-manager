package com.azzolim.renan.dvc.manager.api.v1.controller;

import com.azzolim.renan.dvc.manager.api.v1.controller.in.ServiceInput;
import com.azzolim.renan.dvc.manager.domain.model.Service;
import com.azzolim.renan.dvc.manager.domain.model.ServiceType;
import com.azzolim.renan.dvc.manager.domain.service.impl.ServiceServiceImpl;
import com.azzolim.renan.dvc.manager.domain.service.impl.ServiceTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ServiceControllerTest extends ControllerTest {

    private static final String RESOURCE = "/v1/services";

    @MockBean
    private ServiceServiceImpl serviceServiceImpl;

    @MockBean
    private ServiceTypeServiceImpl serviceTypeService;

    private ServiceType serviceType = ServiceType.builder().id(1L).build();

    private Service service = Service.builder().id(1L).build();

    @Test
    void save() throws Exception {
        var input = new ServiceInput();
        input.setName("Test");
        input.setServiceTypeId(1L);

        service.setServiceType(serviceType);
        when(this.serviceTypeService.findById(Mockito.anyLong())).thenReturn(serviceType);
        when(this.serviceServiceImpl.save(any(Service.class))).thenReturn(service);

        mockMvc.perform(post(RESOURCE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

    }

    @Test
    void delete() throws Exception {
        when(this.serviceServiceImpl.findById(anyLong())).thenReturn(service);

        mockMvc.perform(MockMvcRequestBuilders.delete(RESOURCE + "/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void findById() throws Exception {
        when(this.serviceServiceImpl.findById(anyLong())).thenReturn(service);

        mockMvc.perform(get(RESOURCE + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(service.getId()));
    }

    @Test
    void findAll() throws Exception {
        when(this.serviceServiceImpl.findAll()).thenReturn(List.of(service, service));

        mockMvc.perform(get(RESOURCE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}