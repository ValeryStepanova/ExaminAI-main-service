package com.itechart.main_service.config;

import com.itechart.admin_service_api.api.AdminServiceClient;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AdminServiceClientMockConfig {
    @Bean
    public AdminServiceClient adminServiceClient() {
        return Mockito.mock(AdminServiceClient.class);
    }
}
