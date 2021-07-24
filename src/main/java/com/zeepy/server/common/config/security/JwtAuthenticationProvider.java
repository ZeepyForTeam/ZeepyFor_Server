package com.zeepy.server.common.config.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.zeepy.server.auth.service.CustomDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {
	private final CustomDetailsService customDetailsService;
	private final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60;
	private final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60;
	private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class.getName());
	@Value("${jwt.secret}")
	private String secretKey;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty");
		}
		return false;
	}

	public String getUserEmail(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public UsernamePasswordAuthenticationToken getAuthentication(String userEmail) {
		UserDetails userDetails = customDetailsService.loadUserByUsername(userEmail);
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
	}

	public String createAccessToken(String userEmail) {
		Claims claims = Jwts.claims().setSubject(userEmail);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public String createRefreshToken() {
		Date now = new Date();
		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

}
