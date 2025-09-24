package com.itechart.main_service.service;

import com.itechart.admin_service_api.dto.TaskInternDto;

import java.util.UUID;

public interface MentorService {
    TaskInternDto commentPullRequest(Long taskInternId, String comment);
}
