package com.sk.filemanagement.security;

import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer ";
  
  @Value("${predefined.token}")
  private String this_predefined_token;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = extractToken(request);
    if (StringUtils.hasText(token) && isValidToken(token)) {
      Authentication authentication = new UsernamePasswordAuthenticationToken(null, null,
      Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("토큰 검증 완료 (인증됨) !! ");
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
      // log.info("AUTHORIZATION_HEADER 토큰 추출 : "+ bearerToken.substring(TOKEN_PREFIX.length()));
      return bearerToken.substring(TOKEN_PREFIX.length());
    }
    return null;
  }

  private boolean isValidToken(String token) {
    // 미리 정의된 토큰과 비교하여 토큰의 유효성을 검증하는 로직을 작성합니다.
    String predefinedToken = this_predefined_token; // 미리 정의된 토큰을 설정합니다.

    // log.info("토큰검사결과 : " + token.equals(predefinedToken) + " 설정된 토큰 : " + predefinedToken);
    log.debug("토큰검사결과 : " + token.equals(predefinedToken));
    return token.equals(predefinedToken);
  }

}

