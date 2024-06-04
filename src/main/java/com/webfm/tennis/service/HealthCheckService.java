package com.webfm.tennis.service;

import com.webfm.tennis.ApplicationStatus;
import com.webfm.tennis.HealthCheck;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {
    public HealthCheck healthcheck(){
        return new HealthCheck(ApplicationStatus.OK, "Welcome to Webfm Tennis !");
    }

}
