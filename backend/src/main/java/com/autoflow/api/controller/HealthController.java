package com.autoflow.api.controller;

import com.autoflow.api.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ApiResponse<Map<String, String>> getHealth() {
        log.info("Health check requested");
        return ApiResponse.success("System is healthy", Map.of(
                "status", "UP",
                "version", "0.0.1-SNAPSHOT"
        ));
    }
}
