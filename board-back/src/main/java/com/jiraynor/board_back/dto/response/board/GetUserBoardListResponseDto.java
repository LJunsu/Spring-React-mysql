package com.jiraynor.board_back.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jiraynor.board_back.common.ResponseCode;
import com.jiraynor.board_back.common.ResponseMessage;
import com.jiraynor.board_back.dto.object.BoardListItem;
import com.jiraynor.board_back.dto.response.ResponseDto;
import com.jiraynor.board_back.entity.BoardListViewEntity;

import lombok.Getter;

@Getter
public class GetUserBoardListResponseDto extends ResponseDto {
    private List<BoardListItem> userBoardList;

    private GetUserBoardListResponseDto(List<BoardListViewEntity> boardListViewEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userBoardList = BoardListItem.getList(boardListViewEntities);
    }

    public static ResponseEntity<GetUserBoardListResponseDto> success(List<BoardListViewEntity> boardListViewEntities) {
        GetUserBoardListResponseDto result = new GetUserBoardListResponseDto(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USED, ResponseMessage.NOT_EXISTED_USED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    
}
