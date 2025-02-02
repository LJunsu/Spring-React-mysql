package com.jiraynor.board_back.repository.resultSet;

public interface GetBoardResultSet {
    Integer getBoardNumber();
    String getTitle();
    String getContent();
    String getWriteDateTime();
    String getWriterEmail();
    String getWriterNickname();
    String getWriterProfileImage();
}
