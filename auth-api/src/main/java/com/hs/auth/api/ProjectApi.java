package com.hs.auth.api;

import com.hs.auth.api.dto.ApiResponse;
import com.hs.auth.api.dto.ProjectRegisterRequest;
import com.hs.auth.api.dto.ProjectResponse;
import com.hs.auth.api.dto.ProjectUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project Management", description = "프로젝트 관리 API")
public interface ProjectApi {

    @PostMapping
    @Operation(summary = "프로젝트 등록", description = "새로운 프로젝트를 등록하고 Client ID/Secret을 발급합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "프로젝트 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 존재하는 프로젝트 이름")
    })
    ApiResponse<ProjectResponse> registerProject(@RequestBody ProjectRegisterRequest request);

    @GetMapping
    @Operation(summary = "프로젝트 목록 조회", description = "등록된 모든 프로젝트 목록을 조회합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ApiResponse<List<ProjectResponse>> getAllProjects();

    @GetMapping("/{projectId}")
    @Operation(summary = "프로젝트 상세 조회", description = "특정 프로젝트의 상세 정보를 조회합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음")
    })
    ApiResponse<ProjectResponse> getProject(@Parameter(description = "프로젝트 ID") @PathVariable String projectId);

    @PutMapping("/{projectId}")
    @Operation(summary = "프로젝트 수정", description = "프로젝트 설명을 수정합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음")
    })
    ApiResponse<ProjectResponse> updateProject(
            @Parameter(description = "프로젝트 ID") @PathVariable String projectId,
            @RequestBody ProjectUpdateRequest request
    );

    @PostMapping("/{projectId}/activate")
    @Operation(summary = "프로젝트 활성화", description = "프로젝트를 활성화 상태로 변경합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "활성화 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이미 활성화된 프로젝트")
    })
    ApiResponse<ProjectResponse> activateProject(@Parameter(description = "프로젝트 ID") @PathVariable String projectId);

    @PostMapping("/{projectId}/deactivate")
    @Operation(summary = "프로젝트 비활성화", description = "프로젝트를 비활성화 상태로 변경합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "비활성화 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이미 비활성화된 프로젝트")
    })
    ApiResponse<ProjectResponse> deactivateProject(@Parameter(description = "프로젝트 ID") @PathVariable String projectId);
}