package com.itechart.main_service.service.impl;

import com.itechart.admin_service_api.dto.TaskInternDto;
import com.itechart.main_service.service.MentorService;
import org.springframework.stereotype.Service;

@Service
public class MentorServiceImpl implements MentorService {
    @Override
    public TaskInternDto commentPullRequest(Long taskInternId, String comment) {
        return null;
    }
}
