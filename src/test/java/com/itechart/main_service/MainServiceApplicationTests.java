package com.itechart.main_service;

import com.itechart.admin_service_api.api.AdminServiceClient;
import com.itechart.main_service.config.AdminServiceClientMockConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(AdminServiceClientMockConfig.class)

class MainServiceApplicationTests {
	@Test
	void contextLoads() {
	}

}
