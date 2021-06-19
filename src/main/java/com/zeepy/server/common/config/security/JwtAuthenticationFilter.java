package com.zeepy.server.common.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	// @Override
	// public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
	// 	IOException,
	// 	ServletException {
	// 	String token = jwtAuthenticationProvider.resolveToken((HttpServletRequest)request);
	// 	System.out.println("token !!!!!!!!!!!!!!!!! "+token);
	// 	if (token != null && jwtAuthenticationProvider.validateToken(token)) {
	// 		String userEmail = jwtAuthenticationProvider.getUserEmail(token);
	// 		Authentication auth = jwtAuthenticationProvider.getAuthentication(userEmail);
	// 		SecurityContextHolder.getContext().setAuthentication(auth);
	// 	}
	// 	chain.doFilter(request, response);
	// }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = jwtAuthenticationProvider.resolveToken((HttpServletRequest)request);
		System.out.println("token !!!!!!!!!!!!!!!!! " + token);
		if (token != null && jwtAuthenticationProvider.validateToken(token)) {
			String userEmail = jwtAuthenticationProvider.getUserEmail(token);
			Authentication auth = jwtAuthenticationProvider.getAuthentication(userEmail);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);
	}
}
