package com.webfm.tennis.web;

import com.webfm.tennis.HealthCheck;
import com.webfm.tennis.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    private HealthCheckService healthCheckService;

    @GetMapping("/healthcheck")
    public HealthCheck healthcheck(){
        //return new HealthCheck(ApplicationStatus.OK, "Welcome to Webfm Tennis !");
        return healthCheckService.healthcheck();
    }
}
