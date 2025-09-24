package com.itechart.main_service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.itechart.main_service.config.RestTemplateConfig;
import com.itechart.main_service.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GitHubServiceImpl implements GitHubService {
    private final RestTemplateConfig restTemplate;
    @Override
    public String getInstallationToken(String jwt, String owner) {
        HttpHeaders httpHeaders  = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<JsonNode> response = restTemplate.restTemplate().exchange(
                "https://api.github.com/app/installations",
                HttpMethod.GET,
                entity,
                JsonNode.class
        );

        JsonNode installations = response.getBody();
        long installationId = StreamSupport.stream(installations.spliterator(), false)
                .filter(node -> node.path("account").path("login").asText().equals(owner))
                .findFirst()
                .orElseThrow()
                .path("id")
                .asLong();
        String url = "https://api.github.com/app/installations/"
                + installationId
                + "/access_tokens";
        ResponseEntity<JsonNode> resp = restTemplate.restTemplate().exchange(
                url, HttpMethod.POST, entity, JsonNode.class
        );
        return resp.getBody().path("token").asText();
    }

    @Override
    public void postComment(String token, String owner, String comment, String repo, Integer pr) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = Map.of("body", comment);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        restTemplate.restTemplate().postForEntity(
                "https://api.github.com/repos/%s/%s/issues/%d/comments".formatted(owner, repo, pr),
                entity,
                Void.class
        );
    }
}
