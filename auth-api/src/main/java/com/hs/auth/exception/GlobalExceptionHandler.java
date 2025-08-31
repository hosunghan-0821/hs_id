package com.hs.auth.exception;

import com.hs.auth.api.dto.ApiResponse;
import com.hs.auth.common.exception.InvalidStateException;
import com.hs.auth.common.exception.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OAuth2Exception.class)
    public <T> ApiResponse<T> handleOAuth2Exception(OAuth2Exception e) {
        return ApiResponse.error(e.getMessage(), e.getErrorCode());
    }

    @ExceptionHandler(InvalidStateException.class)
    public <T> ApiResponse<T> handleInvalidStateException(InvalidStateException e) {
        return ApiResponse.error("잘못된 State 파라미터입니다.", "invalid_state", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public <T> ApiResponse<T> handleIllegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.error("잘못된 요청입니다.", "invalid_request", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public <T> ApiResponse<T> handleGenericException(Exception e) {
        return ApiResponse.error("서버 내부 오류가 발생했습니다.", "internal_server_error", e.getMessage());
    }
}