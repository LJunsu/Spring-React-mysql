package com.jiraynor.board_back.service;

import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.dto.request.auth.SignInRequestDto;
import com.jiraynor.board_back.dto.request.auth.SignUpRequestDto;
import com.jiraynor.board_back.dto.response.auth.SignInResponseDto;
import com.jiraynor.board_back.dto.response.auth.SignUpResponseDto;

public interface AuthService {
    // ResponseEntity - HTTP 상태 코드, 응답 본문(body), 응답 헤더를 포함하는 클래스
    // <? super SignUpResponseDto> - 
    // SignUpResponseDto 뿐만 아니라 그 상위 타입인 ResponseDto도 반환할 수 있게 함
    // 즉, SignUpResponseDto 자신 뿐만 아니라 무보 클래스를 허용
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    
    ResponseEntity<? super SignInResponseDto> singIn(SignInRequestDto dto);
}
