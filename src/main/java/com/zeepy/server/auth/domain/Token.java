package com.zeepy.server.auth.domain;

import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Token extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "token_sequence_gen")
	private Long id;

	private String accessToken;

	private String refreshToken;

	private String kakaoAccessToken;

	private String naverAccessToken;

	private String appleRefreshToken;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Token(String accessToken, String refreshToken, User user) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.user = user;
	}

	@Builder
	public Token(User user) {
		this.user = user;
	}

	public void setServiceToken(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public void setKakaoToken(String kakaoAccessToken) {
		this.kakaoAccessToken = kakaoAccessToken;
	}

	public void setNaverToken(String naverAccessToken) {
		this.naverAccessToken = naverAccessToken;
	}

	public void setAppleRefreshToken(String appleRefreshToken) {
		this.appleRefreshToken = appleRefreshToken;
	}
}
