package com.jiraynor.board_back.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jiraynor.board_back.filter.JwtAuthenticationFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// 설정 정보를 제공하며, Spring의 컴포넌트 스캔에 의해 자동 감지되어 bean 등록
@Configuration
// Spring Security 설정을 제공
@EnableWebSecurity
// lombok - final, @NonNull이 붙은 필드에 자동으로 생성자를 생성
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    // Spring 컨테이너에 bean 등록
    // Spring Security의 HttpSecurinty 설정을 통해 애플리케이션의 보안 구성을 정의
    // JWT 인증, CORS 설정, CSRF 비활성화, 세션 관리 등의 보안 설정
    @Bean
    protected SecurityFilterChain cofigure(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity 설정
        httpSecurity
            // cors 설정
            .cors(
                cors -> 
                    // cors 정책을 설정한 corsConfigurationSource 함수
                    cors.configurationSource(corsConfigrationSource())
                    // cors.disable() // 위 설정 적용이 안되서 CorsConfig.java로 적용
            )
            // csrf 보호 비활성화
            // API 서버에서 클라이언트와 서버 간 상태를 유지하지 않을 경우(Stateless) 비활성화함
            .csrf(CsrfConfigurer::disable)
            // http 기본 인증 비활성화
            // 기본 인증 방식인 클라이언트가 http 요청 시 사용자명과 비밀번호를 제공하는 방식을 비활성화
            .httpBasic(HttpBasicConfigurer::disable)
            // 세션 관리 정책 설정
            .sessionManagement(
                sessionManagement -> 
                    // 상태가 없는 세션 정책을 설정하여 세션을 사용하지 않겠다는 설정
                    // jwt와 같은 토큰 기반 인증을 사용할 때 사용
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // 요청 url에 대한 인증 정책 설정
            .authorizeHttpRequests(
                request -> 
                    request
                    // 해당 url 패턴에 대한 요청은 인증 없이 접근 가능
                    .requestMatchers(
                        "/", "/api/v1/auth/**", "/api/v1/search/**", "/file/**"
                    ).permitAll() // 해당 URL에 인증 없이 접근 - 다른 조건의 메소드가 많음
                    // get 요청에 대해서 해당 url도 인증 없이 접근 가능
                    .requestMatchers(
                        HttpMethod.GET, "/api/v1/board/**", "/api/v1/user/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    // 그 외 모든 요청은 인증된 사용자만 접근
                    .anyRequest().authenticated()
            )
            // 인증 실패 시 커스텀 오류 처리
            .exceptionHandling(
                exceptionHandling -> 
                    exceptionHandling
                    // 인증 실패(authenticationEntryPoint) 시 FailedAuthenticationEntryPoint 호출
                    .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
            )
            // jwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 필터 앞에 추가
            // 모든 요청에 대해 jwt 토큰을 검사하고 인증을 수행하기 위해 jwt 인증 필터를 추가
            .addFilterBefore(
                jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
            );

        // Spring Security의 HttpSecurity 객체를 구성한 후
        // 해당 설정을 기반으로 SecurityFilterChain을 생성하여 반환
        // HttpSecurity의 보안 설정을 구성한 후 .build()를 호출하면 SecurityFilterChain 객체가 생성
        return httpSecurity.build();
    }

    // Spring 컨테이너에 bean 등록
    @Bean
    // CORS(Cross-Origin Resource Sharing) 설정
    // CORS는 웹 브라우저에서 보안 상 다른 출처에서 리소스를 요청할 때 제한을 두는 메커니즘
    protected CorsConfigurationSource corsConfigrationSource() {
        // CorsConfiguration 객체는 CORS 설정을 위한 구성 객체
        // 출처, HTTP 메소드, HTTP 헤더 등 어떤 요청을 허용할지 설정
        CorsConfiguration configuration = new CorsConfiguration();
        // addAllowedOrigin - 출처에 대한 요청 설정 - *는 모든 출처에 요청을 허용
        // configuration.addAllowedOrigin("*");
        configuration.addAllowedOriginPattern("*");
        // addAllowedMethod - HTTP 메소드에 대한 요청 설정 - *는 모든 HTTP 메소드에 요청을 허용
        configuration.addAllowedMethod("*");
        // addExposedHeader - HTTP 응답 헤더를 클라이언트에서 접근할 수 있도록 허용
        configuration.addExposedHeader("*");
        configuration.setAllowCredentials(true);

        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Accept");


        // UrlBasedCorsConfigurationSource - URL 기반으로 CORS 설정을 등록
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 해당 경로에 대해 CORS 설정을 등록
        source.registerCorsConfiguration("/**", configuration);

        // CorsConfigurationSource 객체 반환
        return source;
    }
}

// Spring Security에서 인증 실패 시 클라이언트에게 HTTP 응답을 보냄
// AuthenticationEntryPoint - 인증되지 않은 요청이 있을 때 응답을 처리하는 인터페이스
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    // commence - 인증되지 않은 요청에 대해 HTTP 응답을 작성
    public void commence(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException authException
    ) throws IOException, ServletException {
        // 응답 타입을 json으로 설정
        response.setContentType("application/json");
        // 응답 상태 코드를 403 Forbidden으로 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 응답 바디에 json 형식의 메시지를 작성
        // 클라이언트에게 인증 실패에 대한 설명을 포함한 JSON 객체
        response.getWriter()
            .write("{\"code\" : \"AF\", \"message\" : \"Authorization Failed\"}");
    }   
}