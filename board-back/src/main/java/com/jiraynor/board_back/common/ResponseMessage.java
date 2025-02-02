package com.jiraynor.board_back.common;

public interface ResponseMessage {
    // 인터페이스는 public static final 형태로 사용하며, 생략해도 이 형태로 적용됨
    // public static final String SUCCESS = "Success";

    // HTTP Status 200
    String SUCCESS = "Success.";

    // HTTP Status 400
    String VALIDATION_FAILED = "Validation failed.";
    String DUPLICATE_EMAIL = "Duplicate email.";
    String DUPLICATE_NICKNAME = "Duplicate nickname.";
    String DUPLICATE_TEL_NUMBER = "Duplicate telephone number.";
    String NOT_EXISTED_USED = "This user does not exist.";
    String NOT_EXISTED_BOARD = "This board does not exist.";
    
    // HTTP Status 401
    String SIGN_IN_FAIL = "Login information mismatch.";
    String AUTHORIZATION_FAIL = "Authorization Failed.";
    
    // HTTP Status 403
    String NO_PERMISSION = "Do not have permission.";
    
    // HTTP Status 500
    String DATABASE_ERROR = "Database error.";
}
