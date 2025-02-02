package com.jiraynor.board_back.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jiraynor.board_back.dto.request.board.PatchBoardRequestDto;
import com.jiraynor.board_back.dto.request.user.PatchNicknameRequestDto;
import com.jiraynor.board_back.dto.request.user.PatchProfileImageRequestDto;
import com.jiraynor.board_back.dto.response.ResponseDto;
import com.jiraynor.board_back.dto.response.board.GetUserBoardListResponseDto;
import com.jiraynor.board_back.dto.response.user.GetSignInUserResponseDto;
import com.jiraynor.board_back.dto.response.user.GetUserResponseDto;
import com.jiraynor.board_back.dto.response.user.PatchNicknameResponseDto;
import com.jiraynor.board_back.dto.response.user.PatchProfileImageResponseDto;
import com.jiraynor.board_back.entity.BoardListViewEntity;
import com.jiraynor.board_back.entity.UserEntity;
import com.jiraynor.board_back.repository.UserRepository;
import com.jiraynor.board_back.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        UserEntity userEntity = null;

        try {
            userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return GetSignInUserResponseDto.notExistUser();
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {
        UserEntity userEntity = null;

        try {
            userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return GetUserResponseDto.noExistUser();
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {        
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            if(userEntity == null) PatchNicknameResponseDto.noExistUser();
            
            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if(existedNickname) return PatchNicknameResponseDto.duplicateNickname();

            userEntity.setNickname(nickname);
            userRepository.save(userEntity);
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchNicknameResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email) {
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return PatchProfileImageResponseDto.noExistUser();

            String profileImage = dto.getProfileImage();
            userEntity.setProfileImage(profileImage);
            userRepository.save(userEntity);
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchProfileImageResponseDto.success();
    }
}
