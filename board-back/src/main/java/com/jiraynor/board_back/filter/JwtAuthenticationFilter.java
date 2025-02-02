package com.jiraynor.board_back.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jiraynor.board_back.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// IoC(제어의 역전)를 통한 DI(의존성 주입)
@Component // java bean 등록
// lombok - final, @NonNull이 붙은 필드에 자동으로 생성자를 생성
@RequiredArgsConstructor
// OncePerRequestFilter는 필터 클래스로 매 요청마다 한 번만 실행되는 필터를 구현할 때 사용
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider JwtProvider; // 생성자를 통해 DI

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // request의 Bearer 토큰 추출
            String token = parseBearerToken(request);

            if(token == null) {
                // 필터 체인 - 다음 필터로 요청을 넘김
                filterChain.doFilter(request, response);
                return;
            }
    
            // jwt 토큰 검증 후 정보 추출
            String email = JwtProvider.validate(token);
            if(email == null) {
                // 필터 체인 - 다음 필터로 요청을 넘김
                filterChain.doFilter(request, response);
                return;
            }
    
            // 인증 객체(Authentication) 생성
            AbstractAuthenticationToken authenticationToken =
                // UsernamePasswordAuthenticationToken는 
                // Spring Security에서 기본적인 인증 토큰 클래스
                // 사용자의 아이디와 비밀번호를 포함하여 인증 정보 생성
                new UsernamePasswordAuthenticationToken(
                    // 아이디, 비밀번호, 권한 정보
                    email, null, AuthorityUtils.NO_AUTHORITIES
                );
            
            // 인증 객체의 세부 정보를 설정
            // 생성된 WebAuthenticationDetails 객체를 authenticationToken 객체에 설정
            authenticationToken.setDetails(
                // WebAuthenticationDetailsSource는 
                // Spring Security에서 인증을 위한 세부 정보를 생성하는 클래스
                // 사용자의 ip, 세션 id, 현재 요청에 대한 세부 정보를 포함하는 객체
                new WebAuthenticationDetailsSource()
                // request를 통해 WebAuthenticationDetails 객체를 생성
                .buildDetails(request)
            );
    
            // 현재 인증된 사용자와 관련된 보안 정보를 담는 객체
            // 인증된 사용자는 이제 SecurityContextHolder를 통해 접근할 수 있음
            SecurityContext securityContext = 
                // 빈 SecurityContext 객체를 생성하는 메소드
                SecurityContextHolder.createEmptyContext();
            // 생성된 SecurityContext 객체에 현재 인증된 사용자를 인증 토큰으로 설정
            securityContext.setAuthentication(authenticationToken);
    
            // 생성한 securityContext를 현재 스레드의 보안 컨텍스트로 설정
            // SecurityContextHolder는 현재 스레드에 대한 보안 컨텍스트를 저장하고 관리하는 클래스
            // SecurityContext는 HTTP 주기 동안 스레드 로컬에 저장되어 인증된 사용자 정보를 참조
            SecurityContextHolder.setContext(securityContext);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // request에서 header의 Authorization의 값
        String authorization = request.getHeader("Authorization");

        // hasText를 통해 authorization가 null, 길이 0, 화이트스페이스라면 false, 아니면 true
        boolean hasAuthorization = StringUtils.hasText(authorization);
        // 값이 없다면 null 반환
        if(!hasAuthorization) return null;

        // authorization의 값이 Bearer로 시작하면 true, 아니면 false
        boolean isBearer = authorization.startsWith("Bearer ");
        // authorization의 값이 Bearer로 시작하지 않아서 null 반환
        if(!isBearer) return null;

        // 위 검증을 통과했다면 authorization의 7번 인덱스 부터(Bearer 을 제외한)의 값을 문자로 저장
        // 토큰은 Authorization: Bearer <토큰> 이런 형태로 제공되기 때문
        String token = authorization.substring(7);
        return token;
    }
}
