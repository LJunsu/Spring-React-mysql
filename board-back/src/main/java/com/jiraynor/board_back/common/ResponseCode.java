package com.jiraynor.board_back.common;

public interface ResponseCode {
    // 인터페이스는 public static final 형태로 사용하며, 생략해도 이 형태로 적용됨
    // public static final String SUCCESS = "SU";

    // HTTP Status 200
    String SUCCESS = "SU";

    // HTTP Status 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DF";
    String DUPLICATE_NICKNAME = "DN";
    String DUPLICATE_TEL_NUMBER = "DT";
    String NOT_EXISTED_USED = "NU";
    String NOT_EXISTED_BOARD = "NB";

    // HTTP Status 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    // HTTP Status 403
    String NO_PERMISSION = "NP";

    // HTTP Status 500
    String DATABASE_ERROR = "DBE";
}
