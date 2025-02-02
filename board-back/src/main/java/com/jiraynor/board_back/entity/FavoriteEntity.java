package com.jiraynor.board_back.entity;

import com.jiraynor.board_back.entity.primaryKey.FavoritePk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favorite")
@Table(name = "favorite")
// 복합 기본 키를 사용하는 방식으로 두 개 이상의 필드를 조합해 하나의 기본 키를 만드는 방법
// DB 테이블에서 두 개 이상의 컬럼을 기본 키로 사용하는 경우에 적용
// 하나의 필드로 기본 키를 만들 수 없거나 두 필드를 결합한 관계를 표현하는 경우 적용
@IdClass(FavoritePk.class) 
public class FavoriteEntity {
    @Id
    private String userEmail;
    @Id
    private int boardNumber;
}
