package com.book.apiPayload.code.status;

import com.sun.net.httpserver.Authenticator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

@Getter
@Setter
@RequiredArgsConstructor

public class StatusResponse {
    private final ErrorStatus errorStatus;
    private final SuccessStatus successStatus;
    public static StatusResponse ofSuccess(SuccessStatus successStatus) {
        return new StatusResponse(null, successStatus);
    }

    public static StatusResponse ofError(ErrorStatus errorStatus) {
        return new StatusResponse(errorStatus, null);
    }

    public boolean isSuccess() {
        return successStatus != null;
    }
    public int getCode() {
        return isSuccess() ? successStatus.getCode() : errorStatus.getCode();
    }
}
