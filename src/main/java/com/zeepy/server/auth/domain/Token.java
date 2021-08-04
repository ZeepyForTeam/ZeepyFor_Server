package com.zeepy.server.auth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.zeepy.server.common.domain.BaseTimeEntity;
import com.zeepy.server.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Token extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_sequence_gen")
	@SequenceGenerator(name = "token_sequence_gen", sequenceName = "token_sequence")
	private Long id;

	@NotNull
	private String accessToken;

	@NotNull
	private String refreshToken;

	private String kakaoAccessToken;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Token(String accessToken, String refreshToken, User user) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.user = user;
	}

	public void setKakaoToken(String kakaoAccessToken) {
		this.kakaoAccessToken = kakaoAccessToken;
	}
}