package com.itechart.main_service.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "github")
@RequiredArgsConstructor
@Getter
@Setter
public class GitHubConfig {
    private Long appId;
    private String clientId;
    private String clientSecret;
    private String webhookSecret;
    private String pemPath;
}
