package com.itechart.main_service.service;

public interface GitHubService {
    String getInstallationToken(String jwt, String owner);
    void postComment(String token, String owner, String comment, String repo, Integer pr);
}
