package com.itechart.main_service.controller;

import com.itechart.admin_service_api.dto.TaskInternDto;
import com.itechart.admin_service_api.dto.request.UpdateLinkRequest;
import com.itechart.main_service.exception.TaskInternNotFoundException;
import com.itechart.main_service.exception.handler.UnauthorizedException;
import com.itechart.main_service.service.InternService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/intern-flow")
@RequiredArgsConstructor
public class InternController {
    private final InternService internService;

    @PutMapping("/{taskId}/githubLink")
    public ResponseEntity<TaskInternDto> updateGitHubLink(@PathVariable Long taskId, @RequestBody @Valid UpdateLinkRequest updateLinkRequest) throws UnauthorizedException, TaskInternNotFoundException {
        return ResponseEntity.ok(internService.updateLink(taskId, updateLinkRequest));
    }
}
