package com.hs.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 수정 요청")
public class ProjectUpdateRequest {

    @Schema(description = "프로젝트 설명", example = "업데이트된 프로젝트 설명")
    private String description;
}