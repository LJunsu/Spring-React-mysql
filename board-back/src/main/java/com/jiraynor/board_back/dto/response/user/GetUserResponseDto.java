package com.jiraynor.board_back.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;
import com.jiraynor.board_back.dto.response.ResponseDto;
import com.jiraynor.board_back.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetUserResponseDto extends ResponseDto {
    private String email;
    private String nickname;
    private String profileImage;

    private GetUserResponseDto(UserEntity userEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.email = userEntity.getEmail();
        this.nickname = userEntity.getNickname();
        this.profileImage = userEntity.getProfileImage();
    } 

    public static ResponseEntity<GetUserResponseDto> success(UserEntity userEntity) {
        GetUserResponseDto result = new GetUserResponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USED, ResponseMessage.NOT_EXISTED_USED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        
    }
}
