package com.zeepy.server.auth.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeepy.server.auth.domain.Token;
import com.zeepy.server.auth.dto.GetUserInfoResDto;
import com.zeepy.server.auth.dto.LoginReqDto;
import com.zeepy.server.auth.dto.ReIssueReqDto;
import com.zeepy.server.auth.dto.SNSLoginReqDto;
import com.zeepy.server.auth.dto.TokenResDto;
import com.zeepy.server.auth.repository.TokenRepository;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundPasswordException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundTokenException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.NotFoundUserException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.RefreshTokenException;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.RefreshTokenNotExistException;
import com.zeepy.server.common.config.security.JwtAuthenticationProvider;
import com.zeepy.server.user.domain.User;
import com.zeepy.server.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final KakaoApi kakaoApi;
	private final NaverApi naverApi;

	@Transactional
	public TokenResDto login(LoginReqDto loginReqDto) {
		User user = getUserByEmail(loginReqDto.getEmail());

		if (!passwordEncoder.matches(
			loginReqDto.getPassword(),
			user.getPassword())) {
			throw new NotFoundPasswordException();
		}

		String accessToken = jwtAuthenticationProvider.createAccessToken(
			user.getEmail());
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();

		validToken(accessToken, refreshToken, user);

		return new TokenResDto(accessToken, refreshToken, user);
	}

	@Transactional
	public void logout(String userEmail) {
		Long userId = getUserByEmail(userEmail).getId();
		Token findToken = tokenRepository.findByUserId(userId)
			.orElseThrow(NotFoundTokenException::new);//???????????? ????????? ????????? ????????????.(401??)
		//?????? token??? ???????????? !null?????? ????????? ????????????
		if (findToken.getKakaoAccessToken() != null) {
			kakaoApi.logout(findToken.getKakaoAccessToken());
		}
		//????????? ????????????
		if (findToken.getNaverAccessToken() != null) {
			naverApi.logout(findToken.getNaverAccessToken());
		}
		//????????? token??? ????????? !null?????? ?????? ????????????
		tokenRepository.deleteByUserId(userId);
	}

	@Transactional
	public TokenResDto reissue(HttpServletRequest request,ReIssueReqDto reIssueReqDto) {
		String accessToken = reIssueReqDto.getAccessToken();
		String refreshToken = reIssueReqDto.getRefreshToken();

		Token findToken = tokenRepository.findByRefreshToken(refreshToken)
			.orElseThrow(RefreshTokenNotExistException::new);

		if (!jwtAuthenticationProvider.validateToken(request,refreshToken)) {
			throw new RefreshTokenException();
		}

		String userEmail = jwtAuthenticationProvider.getUserEmail(accessToken);
		String newAccessToken = jwtAuthenticationProvider.createAccessToken(userEmail);
		String newRefreshToken = jwtAuthenticationProvider.createRefreshToken();

		User findUser = userRepository.findByEmail(userEmail)
			.orElseThrow(NotFoundUserException::new);

		findToken.setServiceToken(newAccessToken, newRefreshToken);

		return new TokenResDto(newAccessToken, newRefreshToken, findUser);
	}

	@Transactional
	public TokenResDto kakaoLogin(GetUserInfoResDto userInfoResDto, SNSLoginReqDto snsLoginReqDto) {
		String email = userInfoResDto.getEmail();

		//?????????????????? ????????????
		User user = userRepository.findByEmail(email)
			.orElseGet(() -> {
				User newUser = userInfoResDto.toEntity();
				User saveUser = userRepository.save(newUser);    //?????????????????? name = zeepy#000 ???????????????
				saveUser.setNickNameById();
				return saveUser;
			});

		String accessToken = jwtAuthenticationProvider.createAccessToken(user.getEmail());
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();

		// Token tokens = new Token(accessToken, refreshToken, user);
		Token tokens = validToken(accessToken, refreshToken, user);
		tokens.setKakaoToken(
			snsLoginReqDto.getAccessToken()
		);
		tokenRepository.save(tokens);

		return new TokenResDto(accessToken, refreshToken, user);
	}

	@Transactional
	public TokenResDto naverLogin(GetUserInfoResDto userInfoResDto, SNSLoginReqDto snsLoginReqDto) {
		String email = userInfoResDto.getEmail();

		//?????????????????? ????????????
		User user = userRepository.findByEmail(email)
			.orElseGet(() -> {
				User newUser = userInfoResDto.toEntity();
				User saveUser = userRepository.save(newUser);    //?????????????????? name = zeepy#000 ???????????????
				saveUser.setNickNameById();
				return saveUser;
			});

		String accessToken = jwtAuthenticationProvider.createAccessToken(user.getEmail());
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();

		Token tokens = validToken(accessToken, refreshToken, user);
		tokens.setNaverToken(
			snsLoginReqDto.getAccessToken()
		);
		tokenRepository.save(tokens);

		return new TokenResDto(accessToken, refreshToken, user);
	}

	private User getUserByEmail(String authUserEmail) {
		return userRepository.findByEmail(authUserEmail)
			.orElseThrow(NotFoundUserException::new);
	}

	private Token validToken(String accessToken, String refreshToken, User user){
		Token findToken = tokenRepository.findByUserId(user.getId())
			.orElseGet(()->{
				Token newTokenOnlyUser = Token.builder()
					.user(user)
					.build();
				return tokenRepository.save(newTokenOnlyUser);
			});
		findToken.setServiceToken(accessToken, refreshToken);

		return findToken;
	}

}
