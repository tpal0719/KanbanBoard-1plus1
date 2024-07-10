package com.sparta.springtrello.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.sparta.springtrello.auth.JwtAuthenticationFilter;
import com.sparta.springtrello.auth.JwtAuthorizationFilter;
import com.sparta.springtrello.auth.JwtProvider;
import com.sparta.springtrello.auth.UserDetailsServiceImpl;
import com.sparta.springtrello.domain.user.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserAdapter userAdapter;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    // 사용자 인증을 처리하는 컴포넌트
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 사용자 로그인 시 JWT를 생성하고 반환하는 필터
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userAdapter, objectMapper,passwordEncoder);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    // 모든 요청에 대해 JWT의 유효성을 검사하고, 유효한 토큰일 경우 사용자를 인증 상태로 설정하는 필터
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider, userDetailsService, userAdapter, objectMapper);
    }

    // 위에 정의된 필터를 사용하여 필터 체인을 구성
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf(AbstractHttpConfigurer::disable);

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/", "/error", "/users/signup", "/users/login", "/users/kakao/authorize", "/users/kakao/callback").permitAll() // 메인 페이지, 에러 페이지, 회원가입, 로그인, 카카오 요청 허가
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // 필터 체인 구성
        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
