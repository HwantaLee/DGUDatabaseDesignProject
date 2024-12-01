package com.book.apiPayload.exception.handler;

import com.book.apiPayload.code.BaseErrorCode;
import com.book.apiPayload.exception.GeneralException;

public class AppHandler extends GeneralException  {
    public AppHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
