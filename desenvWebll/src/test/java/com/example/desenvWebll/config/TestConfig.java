package com.example.desenvWebll.config;

import com.example.desenvWebll.service.PlantioService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public PlantioService plantioService() {
        return Mockito.mock(PlantioService.class);
    }
}