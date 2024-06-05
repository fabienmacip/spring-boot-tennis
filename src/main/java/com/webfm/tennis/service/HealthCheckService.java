package com.webfm.tennis.service;

import com.webfm.tennis.ApplicationStatus;
import com.webfm.tennis.HealthCheck;
import com.webfm.tennis.repository.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    @Autowired
    private HealthCheckRepository healthCheckRepository;

    public HealthCheck healthcheck(){
        Long activeSessions = healthCheckRepository.countApplicationConnections();

        if(activeSessions > 0) {
            return new HealthCheck(ApplicationStatus.OK, "Welcome to Webfm Tennis !");
        } else {
            return new HealthCheck(ApplicationStatus.KO, "Webfm Tennis is not fully functional, please check your configuration.");
        }

        //return new HealthCheck(ApplicationStatus.OK, "Welcome to Webfm Tennis !");
    }

}
