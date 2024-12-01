package com.book.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.book.apiPayload.code.BaseCode;
import com.book.apiPayload.code.ReasonDTO;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, 200, "성공입니다."),

    // 회원가입

    REGISTER_JOIN_OK(HttpStatus.OK, 200, "회원가입 성공!");

    // 코스에디터 관련
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
