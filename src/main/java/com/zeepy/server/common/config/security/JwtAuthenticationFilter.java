package com.zeepy.server.common.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		String token = jwtAuthenticationProvider.resolveToken((HttpServletRequest)request);
		System.out.println("token !!!!!!!!!!!!!!!!! " + token);
		if (token != null && jwtAuthenticationProvider.validateToken((HttpServletRequest)request,token)) {
			System.out.println("토큰 유효성검사 통과!!!!!!!!!!!!!!!!");
			String userEmail = jwtAuthenticationProvider.getUserEmail(token);
			System.out.println("userEmail !!!!!!!!!!!! : " + userEmail);
			Authentication auth = jwtAuthenticationProvider.getAuthentication(userEmail);
			System.out.println("인증된 authentication !!!!!!!!!!!!!!!!!!! : " + auth);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}
}
