package com.jiraynor.board_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jiraynor.board_back.entity.UserEntity;


@Repository // 해당 인터페이스가 Spring의 레포지토리로 사용 - bean 등록, 의존성 주입
// JpaRepository는 CRUD 메소드와 페이징, 정렬 등을 제공하는 인터페이스
// <UserEntity - 관리할 엔티티 클래스, String - 엔티티의 기본 키 타입 - email>
public interface UserRepository extends JpaRepository<UserEntity, String> {
    // existsBy@ - 해당 레포지토리의 엔티티의 테이블에 해당 @이 존재하는지?
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByTelNumber(String telNumber);

    UserEntity findByEmail(String email);
}
