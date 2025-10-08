package com.hs.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 등록 요청")
public class ProjectRegisterRequest {

    @Schema(description = "프로젝트 이름", example = "My OAuth2 Service")
    private String name;

    @Schema(description = "프로젝트 설명", example = "사용자 인증을 위한 OAuth2 서비스")
    private String description;

    @Schema(description = "리다이렉트 URI", example = "https://myservice.com/callback")
    private String redirectUri;
}