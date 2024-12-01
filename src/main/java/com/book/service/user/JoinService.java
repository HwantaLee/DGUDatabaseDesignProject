package com.book.service.user;

import com.book.apiPayload.code.status.ErrorStatus;
import com.book.apiPayload.code.status.StatusResponse;
import com.book.apiPayload.code.status.SuccessStatus;
import com.book.app.dto.JoinDTO;
import com.book.domain.user.User;
import com.book.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.book.domain.user.User.Role.USER;


@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public StatusResponse joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String name = joinDTO.getName();

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";    //이메일 형식검사
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";  //비밀번호 형식 검사

        boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {   //이미 존재하는 이메일로 회원가입시도
            return StatusResponse.ofError(ErrorStatus.REGISTER_ALREADY_USER_EXIST);
        }

        if (username == null || username.trim().isEmpty()) {        //아이디(이메일)이 입력되지 않은 경우
            return StatusResponse.ofError(ErrorStatus.REGISTER_EMAIL_EMPTY);
        }

        if (!username.matches(emailRegex)) {    //아이디(이메일)이 이메일의 형식이 아닌경우
            return StatusResponse.ofError(ErrorStatus.REGISTER_EMAIL_INVALID);
        }

        if (username.length() > 50) {   //아이디(이메일)이 너무 긴경우
            return StatusResponse.ofError(ErrorStatus.REGISTER_EMAIL_TOO_LONG);
        }

        if (name == null || name.trim().isEmpty()) {    //닉네임이 빈경우
            return StatusResponse.ofError(ErrorStatus.REGISTER_name_EMPTY);
        }

        if (name != null && !name.matches("^[a-zA-Z0-9가-힣]*$")) {   //닉네임에 특수문자가 들어간경우
            return StatusResponse.ofError(ErrorStatus.REGISTER_name_PUNCT);
        }

        if (name != null && name.length() > 50) {   //닉네임이 너무 긴경우
            return StatusResponse.ofError(ErrorStatus.REGISTER_name_TOO_LONG);
        }

        if (password == null || password.trim().isEmpty()) {
            return StatusResponse.ofError(ErrorStatus.REGISTER_PASSWORD_EMPTY);
        }

        if (!password.matches(passwordRegex)) {
            return StatusResponse.ofError(ErrorStatus.REGISTER_PASSWORD_INVALID);
        }

        if (password.length() > 70) {
            return StatusResponse.ofError(ErrorStatus.REGISTER_PASSWORD_TOO_LONG);
        }

        String encodedPW = bCryptPasswordEncoder.encode(password);
        joinDTO.setPassword(encodedPW);
        User data = User.toUser(joinDTO);
        userRepository.save(data);

        return StatusResponse.ofSuccess(SuccessStatus.REGISTER_JOIN_OK);
    }
}


