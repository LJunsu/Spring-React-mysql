package com.jiraynor.board_back.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;
import com.jiraynor.board_back.dto.response.ResponseDto;

import lombok.Getter;

@Getter // lombok - 클래스의 모든 필드에 대해 getter 메소드를 생성
public class SignUpResponseDto extends ResponseDto {
    
    // 생성자
    private SignUpResponseDto() {
        // SUCCESS로 초기화
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    // 정적 메소드(static) - 클래스의 인스턴스를 생성하지 않고 호출 가능
    public static ResponseEntity<SignUpResponseDto> success() {
        SignUpResponseDto result = 
            // SignUpResponseDto 생성자로 생성 - SUCCESS
            new SignUpResponseDto();
        
        // HttpStatus.OK(200) 상태 코드와 SignUpResponseDto 객체 반환
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 정적 메소드(static) - 클래스의 인스턴스를 생성하지 않고 호출 가능
    public static ResponseEntity<ResponseDto> duplicateEmail() {
        ResponseDto result = 
            // ResponseDto의 AllArgsConstructor를 통해 구현된 생성자 - DUPLICATE_EMAIL
            new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
        
        // HttpStatus.BAD_REQUEST(400) 상태 코드와 ResponseDto 객체 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    // 정적 메소드(static) - 클래스의 인스턴스를 생성하지 않고 호출 가능
    public static ResponseEntity<ResponseDto> duplicateNickName() {
        ResponseDto result = 
            // ResponseDto의 AllArgsConstructor를 통해 구현된 생성자 - DUPLICATE_NICKNAME
            new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME);
        
        // HttpStatus.BAD_REQUEST(400) 상태 코드와 ResponseDto 객체 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    // 정적 메소드(static) - 클래스의 인스턴스를 생성하지 않고 호출 가능
    public static ResponseEntity<ResponseDto> duplicateTelNumber() {
        ResponseDto result = 
            // ResponseDto의 AllArgsConstructor를 통해 구현된 생성자 - DUPLICATE_TEL_NUMBER
            new ResponseDto(ResponseCode.DUPLICATE_TEL_NUMBER, ResponseMessage.DUPLICATE_TEL_NUMBER);
        
        // HttpStatus.BAD_REQUEST(400) 상태 코드와 ResponseDto 객체 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
