package com.itechart.main_service.controller;

import com.itechart.admin_service_api.dto.TaskInternDto;
import com.itechart.main_service.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("api/v1/mentor-flow")
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;

    @PostMapping("/comment/{taskInternId}/{internId}")
    public ResponseEntity<TaskInternDto> commentPullRequest(@PathVariable Long taskInternId, @PathVariable UUID internId, String comment){
        return ResponseEntity.ok(mentorService.commentPullRequest(taskInternId, internId, comment));
    }
}
