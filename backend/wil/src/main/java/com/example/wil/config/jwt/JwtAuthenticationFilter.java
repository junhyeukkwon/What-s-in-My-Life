package com.example.wil.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.wil.DTO.LoginRequestDto;
import com.example.wil.config.auth.PrincipalDetails;
import com.example.wil.config.auth.PrincipalDetailsService;
import com.example.wil.config.oauth.PrincipalOauth2UserService;
import com.example.wil.util.HeaderUtils;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    @Autowired
    private TokenProvider tokenProvider;

//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain)  throws ServletException, IOException {
//
//        String tokenStr = HeaderUtils.getAccessToken(request);
//
//        if (tokenProvider.validateToken(tokenStr)) {
//            Authentication authentication = tokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }


//    private final AuthenticationManager authenticationManager;
//
//    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
//    // 인증 요청시에 실행되는 함수 => /login
//
//    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//
//        System.out.println("JwtAuthenticationFilter : 진입 (로그인 시도중)");
//
//        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
//        ObjectMapper om = new ObjectMapper();
//        LoginRequestDto loginRequestDto = null;
//        try {
//            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("JwtAuthenticationFilter : "+loginRequestDto);
//
//        // 유저네임패스워드 토큰 생성
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(
//                        loginRequestDto.getUsername(),
//                        loginRequestDto.getPassword());
//
//        System.out.println("JwtAuthenticationFilter : 토큰생성완료");
//
//        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
//        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
//        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
//        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
//        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.
//
//        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
//        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
//        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
//        Authentication authentication =
//                authenticationManager.authenticate(authenticationToken);
//
//        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println("Authentication : "+principalDetailis.getUser().getUsername());
//        return authentication;
//    }
//
//    // JWT Token 생성해서 response에 담아주기
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//
//        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
//
//        String jwtToken = JWT.create()
//                .withSubject(principalDetailis.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
//                .withClaim("id", principalDetailis.getUser().getId())
//                .withClaim("username", principalDetailis.getUser().getUsername())
//                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
//
//        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
//        System.out.println(jwtToken);
//    }

}