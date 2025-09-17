package com.itechart.main_service.service.impl;

import com.itechart.admin_service_api.api.AdminServiceClient;
import com.itechart.admin_service_api.dto.TaskInternDto;
import com.itechart.admin_service_api.dto.request.UpdateLinkRequest;
import com.itechart.main_service.exception.TaskInternNotFoundException;
import com.itechart.main_service.exception.handler.UnauthorizedException;
import com.itechart.main_service.service.InternService;
import com.itechart.main_service.utils.Constants;
import com.itechart.main_service.utils.CurrentUserService;
import com.itechart.profileserviceapi.dto.UserDto;
import com.itechart.profileserviceapi.enums.Role;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.itechart.main_service.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class InternServiceImpl implements InternService {
    private final AdminServiceClient adminServiceClient;

    @Override
    public TaskInternDto updateLink(Long taskId, UpdateLinkRequest updateLinkRequest) throws TaskInternNotFoundException, UnauthorizedException {
        UserDto currentUser = CurrentUserService.getCurrentUser();
        if (currentUser == null) {
            throw new UnauthorizedException(NOT_AUTHENTICATED);
        }
        if (!currentUser.getRoles().contains(Role.ROLE_INTERN)) {

            throw new AccessDeniedException(INTERN_PERMISSION);
        }
        TaskInternDto taskInternDto = Optional.of(
                adminServiceClient.getTaskInternByTaskID(taskId, currentUser.getUuid()).getBody()
        ).orElseThrow(() -> new TaskInternNotFoundException(TASK_INTERN_NOT_FOUND));

        taskInternDto.setGithubLink(updateLinkRequest.gitHubLink());

        return adminServiceClient.updateGitLink(taskInternDto).getBody();
    }

}
