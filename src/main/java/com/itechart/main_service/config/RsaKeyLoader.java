package com.itechart.main_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;

import static com.itechart.main_service.config.GitHubJwtUtil.loadPrivateKey;

@Service
@RequiredArgsConstructor
public class RsaKeyLoader {
    private final GitHubConfig config;

    public RSAPrivateKey loadKey() {
        return loadPrivateKey(config.getPemPath());
    }
}