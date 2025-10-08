package com.hs.auth.api.dto;

import com.hs.auth.project.domain.Project;
import com.hs.auth.project.domain.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "프로젝트 응답")
public class ProjectResponse {

    @Schema(description = "프로젝트 ID")
    private String projectId;

    @Schema(description = "프로젝트 이름")
    private String name;

    @Schema(description = "프로젝트 설명")
    private String description;

    @Schema(description = "클라이언트 ID")
    private String clientId;

    @Schema(description = "클라이언트 Secret (등록/재발급 시에만 표시)")
    private String clientSecret;

    @Schema(description = "리다이렉트 URI")
    private String redirectUri;

    @Schema(description = "프로젝트 상태 (ACTIVE/INACTIVE)")
    private ProjectStatus status;

    @Schema(description = "생성일시")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;

    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId().getValue(),
                project.getName(),
                project.getDescription(),
                project.getClientId(),
                project.getClientSecret(),
                project.getRedirectUri(),
                project.getStatus(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    public static ProjectResponse fromWithoutSecret(Project project) {
        return new ProjectResponse(
                project.getId().getValue(),
                project.getName(),
                project.getDescription(),
                project.getClientId(),
                "********", // Secret 마스킹
                project.getRedirectUri(),
                project.getStatus(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    public static ProjectResponse fromWithPlainSecret(Project project, String plainSecret) {
        return new ProjectResponse(
                project.getId().getValue(),
                project.getName(),
                project.getDescription(),
                project.getClientId(),
                plainSecret, // 평문 secret (등록 시에만)
                project.getRedirectUri(),
                project.getStatus(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}