package com.azzolim.renan.dvc.manager.api.v1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/manager", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerController {

    @GetMapping("/monthly-costs")
    public void calculateMonthlyCost() {

    }
}
