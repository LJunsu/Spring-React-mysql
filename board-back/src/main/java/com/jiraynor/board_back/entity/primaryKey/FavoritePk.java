package com.jiraynor.board_back.entity.primaryKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 복합 키를 관리하는 클래스
// Serializable를 구현하여 직렬화 가능
public class FavoritePk implements Serializable {
    @Column(name ="user_email") // DB 컬럼 명을 명시적으로 설정
    private String userEmail;
    @Column(name ="board_number")
    private int boardNumber;
}
