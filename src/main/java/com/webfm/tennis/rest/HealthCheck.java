package com.webfm.tennis.rest;

public record HealthCheck(ApplicationStatus status, String message) {
}
