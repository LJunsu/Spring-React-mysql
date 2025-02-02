package com.jiraynor.board_back.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jiraynor.board_back.dto.request.auth.SignInRequestDto;
import com.jiraynor.board_back.dto.request.auth.SignUpRequestDto;
import com.jiraynor.board_back.dto.response.ResponseDto;
import com.jiraynor.board_back.dto.response.auth.SignInResponseDto;
import com.jiraynor.board_back.dto.response.auth.SignUpResponseDto;
import com.jiraynor.board_back.entity.UserEntity;
import com.jiraynor.board_back.provider.JwtProvider;
import com.jiraynor.board_back.repository.UserRepository;
import com.jiraynor.board_back.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final로 지정한 필드의 생성자를 자동 생성 및 의존성 주입
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    // 비밀번호 암호화를 위한 BCrypt 인스턴스 생성
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if(existedEmail) return SignUpResponseDto.duplicateEmail();
            
            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if(existedNickname) return SignUpResponseDto.duplicateNickName();

            String telNumber = dto.getTelNumber();
            boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
            if(existedTelNumber) return SignUpResponseDto.duplicateTelNumber();

            // 위 중복 검사를 문제없이 수행한 후 비밀번호를 암호화 후 암호화한 비밀번호를 DTO에 재설정
            String password = dto.getPassword();
            String encodeedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodeedPassword);

            UserEntity userEntity = new UserEntity(dto);
            // userRepository - JpaRepository를 상속받아 CRUD 기능을 제공
            // .save() - 엔티티를 DB에 저장하거나 업데이트하는 메소드
            // userEntity가 새로운 엔티티라면, 새 레코드를 삽입
            // 이미 존재하는 엔티티라면, 해당 레코드를 업데이트
            userRepository.save(userEntity);
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> singIn(SignInRequestDto dto) {
        String token = null;

        try{
            String email = dto.getEmail();
            UserEntity userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return SignInResponseDto.signInFail();

            String password = dto.getPassword();
            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if(isMatched == false) return SignInResponseDto.signInFail();

            token = jwtProvider.create(email);
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);
    }
    
}
