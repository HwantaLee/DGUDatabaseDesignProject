package com.book.app.controller;

import com.book.apiPayload.ApiResponse;
import com.book.apiPayload.code.status.StatusResponse;
import com.book.app.dto.JoinDTO;
import com.book.service.user.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/user")
public class JoinController {

    private final JoinService joinService;
    @PostMapping("/join")
    public ApiResponse<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        StatusResponse statusResponse = joinService.joinProcess(joinDTO);
        if(statusResponse.isSuccess()) {
            return ApiResponse.of(statusResponse.getSuccessStatus(), "");
        } else {
            return ApiResponse.errorof(statusResponse.getErrorStatus(), "");
        }
    }

}