package com.jiraynor.board_back.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

// IoC(제어의 역전)를 통한 DI(의존성 주입)
@Component // java been 등록
public class JwtProvider {
    // application.properties 파일에 secret-key 이름으로 작성한 값을 secretKey 변수에 적용
    @Value("${secret-key}")
    private String secretKey;

    private Key key;

    // 애플리케이션이 시작되면 secret-key 이름으로 작성된 값을 암호화한 후 key 변수에 저장
    // Bean이 초기화된 후 (모든 의존성이 주입된 후) 실행되는 메소드를 지정하는 어노테이션
    @PostConstruct
    // 애플리케이션이 시작될 때 실행
    public void init() {
        // HMAC-SHA 알고리즘에 사용할 암호화 키 생성
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // 파라미터로 받아온 email을 JWT로 변환
    public String create(String email) {
        Date expiredDate = 
            // 현재 UTC 시간을 기반으로 1시간을 더한 Date 객체 생성
            Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        String jwt = 
            // jwt를 성성하기 위한 빌더 생성
            Jwts.builder()
            // jwt에 서명 추가
            // 서명에 사용할 알고리즘, 서명에 사용할 비밀 키(java.security.Key 객체)
            .signWith(key, SignatureAlgorithm.HS256)
            // jwt의 payload에 클레임 추가 - email을 저장해 어떤 사용자와 관련이 있는지 명시
            .setSubject(email)
            // iat(Issued At) 클레임을 설정 - jwt가 생성된 시각을 포함 - 현재 시간을 지정
            .setIssuedAt(new Date())
            // exp(Expiration Time) 클레임을 설정 - jwt 만료 시간을 지정
            .setExpiration(expiredDate)
            // 설정된 정보를 기반으로 jwt를 직렬화하여 문자열 형식으로 반환
            .compact();
        
        return jwt;
    }

    // 파라미터로 받아온 jwt 토큰을 검증 후 payload의 subject -> email를 꺼냄
    public String validate(String jwt) {
        Claims claims = null;

        try {
            // jwt 검증
            claims = 
                // jwt를 파싱할 수 있는 parser 객체를 생성 - 이 객체를 사용해서 jwt를 해석
                // Jwts.parser()
                Jwts.parserBuilder()
                // jwt의 서명을 검증하는 데 사용할 비밀 키 설정
                // jwt를 서명할 때 사용한 것과 동일한 키여야 함
                // .setSigningKey(secretKey)
                .setSigningKey(key).build()
                // jwt 문자열을 파싱하여 jwt의 클레임을 포함하는 jws 객체를 반환
                // 이 메소드는 jwt가 유효한 jwt 토큰인지 검증하고, 유효하지 않으면 예외 발생
                // 반환되는 jws 객체는 jwt의 header, payload, signing을 포함하며, claims를 추출
                .parseClaimsJws(jwt)
                // jwt 페이로드 부분을 추출하여 claims을 반환
                .getBody();
        } catch(Exception exception) {
            exception.printStackTrace();
            return null;
        }

        // payload - subject를 반환
        return claims.getSubject();
    }
}