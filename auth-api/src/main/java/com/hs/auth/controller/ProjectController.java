package com.hs.auth.controller;

import com.hs.auth.api.ProjectApi;
import com.hs.auth.api.dto.ApiResponse;
import com.hs.auth.api.dto.ProjectRegisterRequest;
import com.hs.auth.api.dto.ProjectRegistrationResult;
import com.hs.auth.api.dto.ProjectResponse;
import com.hs.auth.api.dto.ProjectUpdateRequest;
import com.hs.auth.project.domain.Project;
import com.hs.auth.service.ProjectFacadeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController implements ProjectApi {

    private final ProjectFacadeService projectFacadeService;

    public ProjectController(ProjectFacadeService projectFacadeService) {
        this.projectFacadeService = projectFacadeService;
    }

    @Override
    public ApiResponse<ProjectResponse> registerProject(ProjectRegisterRequest request) {
        ProjectRegistrationResult result = projectFacadeService.registerProject(request.getName(), request.getDescription(), request.getRedirectUri());

        ProjectResponse response = ProjectResponse.fromWithPlainSecret(result.getProject(), result.getPlainClientSecret());
        return ApiResponse.success("프로젝트가 등록되었습니다.", response);
    }

    @Override
    public ApiResponse<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projects = projectFacadeService.getAllProjects().stream()
                .map(ProjectResponse::fromWithoutSecret)
                .collect(Collectors.toList());
        return ApiResponse.success("프로젝트 목록 조회가 완료되었습니다.", projects);
    }

    @Override
    public ApiResponse<ProjectResponse> getProject(String projectId) {
        Project project = projectFacadeService.getProject(projectId);
        return ApiResponse.success("프로젝트 조회가 완료되었습니다.", ProjectResponse.fromWithoutSecret(project));
    }

    @Override
    public ApiResponse<ProjectResponse> updateProject(String projectId, ProjectUpdateRequest request) {
        Project project = projectFacadeService.updateProject(projectId, request.getDescription());
        return ApiResponse.success("프로젝트가 수정되었습니다.", ProjectResponse.fromWithoutSecret(project));
    }

    @Override
    public ApiResponse<ProjectResponse> activateProject(String projectId) {
        Project project = projectFacadeService.activateProject(projectId);
        return ApiResponse.success("프로젝트가 활성화되었습니다.", ProjectResponse.fromWithoutSecret(project));
    }

    @Override
    public ApiResponse<ProjectResponse> deactivateProject(String projectId) {
        Project project = projectFacadeService.deactivateProject(projectId);
        return ApiResponse.success("프로젝트가 비활성화되었습니다.", ProjectResponse.fromWithoutSecret(project));
    }
}