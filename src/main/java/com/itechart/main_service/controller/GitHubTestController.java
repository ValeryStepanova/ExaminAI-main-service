package com.itechart.main_service.controller;

import com.itechart.main_service.config.GitHubConfig;
import com.itechart.main_service.config.GitHubJwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class GitHubTestController {

    private final GitHubJwtUtil jwtUtil;

    @GetMapping("/generateToken")
    public ResponseEntity<String> generateToken(){
        return ResponseEntity.ok(jwtUtil.generateJwt());
    }

}
