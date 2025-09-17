package com.itechart.main_service.service;

import com.itechart.admin_service_api.dto.TaskInternDto;
import com.itechart.admin_service_api.dto.request.UpdateLinkRequest;
import com.itechart.main_service.exception.TaskInternNotFoundException;
import com.itechart.main_service.exception.handler.UnauthorizedException;

public interface InternService {
    TaskInternDto updateLink(Long taskId, UpdateLinkRequest updateLinkRequest) throws TaskInternNotFoundException, UnauthorizedException;
}
