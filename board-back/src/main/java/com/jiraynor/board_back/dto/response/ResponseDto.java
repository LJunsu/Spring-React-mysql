package com.jiraynor.board_back.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // lombok - 클래스의 모든 필드에 대해 getter 메소드를 생성
@AllArgsConstructor // lombok - 클래스의 모든 필드를 파라미터로 받는 생성자를 생성
public class ResponseDto {
    private String code;
    private String message;

    // 정적 메소드(static) - 클래스의 인스턴스를 생성하지 않고 호출 가능
    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto responseBody = 
            // ResponseDto 객체를 생성하며, code와 message를 초기화
            new ResponseDto(
                // ResponseCode와 ResponseMessage는 interface로 각 상수에 접근 가능
                ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR
            );
        
        // ResponseEntity - HTTP 응답을 생성하는 클래스
        return ResponseEntity
            // HttpStatus.INTERNAL_SERVER_ERROR - 500 에러
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            // ResponseEntity의 응답 본문으로 설정
            .body(responseBody);
    }

    public static ResponseEntity<ResponseDto> validationFailed() {
        ResponseDto responseBody = 
            // ResponseDto 객체를 생성하며, code와 message를 초기화
            new ResponseDto(
                // ResponseCode와 ResponseMessage는 interface로 각 상수에 접근 가능
                ResponseCode.VALIDATION_FAILED, ResponseMessage.VALIDATION_FAILED
            );
    
        // ResponseEntity - HTTP 응답을 생성하는 클래스
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            // ResponseEntity의 응답 본문으로 설정
            .body(responseBody);
    }
}
