package com.itechart.main_service.service.impl;

import com.itechart.admin_service_api.api.AdminServiceClient;
import com.itechart.admin_service_api.dto.TaskDto;
import com.itechart.admin_service_api.dto.TaskInternDto;
import com.itechart.main_service.service.GitHubJwtUtilService;
import com.itechart.main_service.service.GitHubService;
import com.itechart.main_service.service.MentorService;
import com.itechart.main_service.service.ParseLinkService;
import com.itechart.main_service.utils.CurrentUserService;
import com.itechart.profileserviceapi.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final AdminServiceClient adminServiceClient;
    private final GitHubJwtUtilService gitHubJwtUtilService;
    private final GitHubService gitHubService;
    private final ParseLinkService parseLinkService;

    @Override
    public TaskInternDto commentPullRequest(Long taskInternId, String comment) {
        // получить таск интерн, +
        // достать ссылку гитхаб, +
        // получить текущего ментора, +
        // проверить ли заассайнен он на текущую таску
        // оставить коммент
        TaskInternDto taskInternDto = adminServiceClient.getTaskInternById(taskInternId).getBody();
        String githubLink = taskInternDto.getGithubLink();
        UserDto currentMentor = CurrentUserService.getCurrentUser();
        //TODO check if current mentor is assigned to task
        String jwt = gitHubJwtUtilService.generateJwt();
        Map<String, String> pr = parseLinkService.parseLink(githubLink);
        String token = gitHubService.getInstallationToken(jwt, pr.get("owner"));
        gitHubService.postComment(token, pr.get("owner"), comment, pr.get("repo"), Integer.parseInt(pr.get("pr")));
        return taskInternDto;
    }
}
