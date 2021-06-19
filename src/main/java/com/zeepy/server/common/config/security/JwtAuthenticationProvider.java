package com.zeepy.server.common.config.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.zeepy.server.auth.service.CustomDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {
	private final CustomDetailsService customDetailsService;
	private final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60; //1시간
	private final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24; //24시간
	@Value("${jwt.secret}")
	private String secretKey;

	@PostConstruct
	protected void init() {
		this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public String getUserEmail(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public UsernamePasswordAuthenticationToken getAuthentication(String userEmail) {
		UserDetails userDetails = customDetailsService.loadUserByUsername(userEmail);
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
	}

	public String createAccessToken(String userEmail) {
		Claims claims = Jwts.claims().setSubject(userEmail);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public String createRefreshToken() {
		Date now = new Date();
		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

}
