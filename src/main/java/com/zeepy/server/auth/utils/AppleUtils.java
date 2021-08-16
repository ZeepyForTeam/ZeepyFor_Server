package com.zeepy.server.auth.utils;

import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.zeepy.server.auth.model.Key;
import com.zeepy.server.auth.model.Keys;
import com.zeepy.server.auth.model.Payload;
import com.zeepy.server.auth.model.TokenResponse;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.AppleUnAuthrizationException;

import sun.security.ec.ECPrivateKeyImpl;

@Component
public class AppleUtils {

	// @Value("${APPLE.PUBLICKEY.URL}")
	private String APPLE_PUBLIC_KEYS_URL = "https://appleid.apple.com/auth/keys";

	// @Value("${APPLE.AUTH.TOKEN.URL}")
	private String AUTH_TOKEN_URL = "https://appleid.apple.com/auth/token";

	// @Value("${APPLE.ISS}")
	private String ISS = "https://appleid.apple.com";
	// 발급기관

	// @Value("${APPLE.AUD}")
	private String AUD = "";
	// 대상

	// @Value("${APPLE.TEAM.ID}")
	private String TEAM_ID = "";

	// @Value("${APPLE.KEY.ID}")
	private String KEY_ID = "";

	// @Value("${APPLE.KEY.PATH}")
	private String KEY_PATH = "static/AuthKey_CMLD397J88.p8";

	// @Value("${APPLE.WEBSITE.URL}")
	private String APPLE_WEBSITE_URL = "";

	public boolean verifyIdentityToken(String idToken) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(idToken);    //token -> JWT
			JWTClaimsSet payload = signedJWT.getJWTClaimsSet();    //JWT의 payload

			//EXP
			Date currentTime = new Date(System.currentTimeMillis());
			if (!currentTime.before(payload.getExpirationTime())) {    //현재시간이 exp보다 크면 exp시간이 오버된것이므로 유효성검사 실패
				return false;
			}
			System.out.println("token값 : " + signedJWT);

			System.out.println("clain값 : " + payload.getClaims());
			System.out.println("nonce 값 : " + payload.getClaim("nonce"));

			//NONCE, ISS, AUD
			if (!"f2acb684cc464e79ee6c37626bc6c6803b76ac4a33831d8f89a65e84426465e8".equals(payload.getClaim("nonce"))
				|| !ISS.equals(payload.getIssuer()) || !AUD.equals(
				payload.getAudience().get(0))) {
				System.out.println("NOCE, ISS, AUD 실패");
				return false;
			}
			System.out.println("NOCE, ISS, AUD 통과");

			//RSA
			if (verifyPublicKey(signedJWT)) {
				System.out.println("RSA 통과");
				return true;
			}

		} catch (RuntimeException | ParseException e) {
			e.printStackTrace();
		}
		System.out.println("token validation 실패");
		return false;
	}

	public boolean verifyPublicKey(SignedJWT signedJWT) {//JWT화 한 token
		String publicKeys = AppleApi.doGet(APPLE_PUBLIC_KEYS_URL); //apple서버에서 받아온 공용키
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Keys keys = objectMapper.readValue(publicKeys, Keys.class);
			for (Key key : keys.getKeys()) {
				RSAKey rsaKey = (RSAKey)JWK.parse(objectMapper.writeValueAsString(key));
				RSAPublicKey publicKey = rsaKey.toRSAPublicKey();
				JWSVerifier verifier = new RSASSAVerifier(publicKey); //publicKey

				if (signedJWT.verify(verifier)) {    //token을 publicKey로 서명 확인.
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * cilent_secret jwt 생성성	 */
	public String createClientSecret() {
		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256)
			.keyID(KEY_ID)
			.build();
		Date now = new Date();

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
			.issuer(TEAM_ID)
			.issueTime(now)
			.expirationTime(new Date(now.getTime() + 1000L * 60 * 60 * 24))
			.audience(ISS)
			.subject(AUD)
			.build();

		SignedJWT jwt = new SignedJWT(header, claimsSet);

		try {
			ECPrivateKey ecPrivateKey = new ECPrivateKeyImpl(readPrivateKey());
			JWSSigner jwsSigner = new ECDSASigner(ecPrivateKey);

			jwt.sign(jwsSigner);

		} catch (InvalidKeyException | JOSEException e) {
			e.printStackTrace();
		}

		return jwt.serialize();
	}

	/**
	 * p8파일에서 private key 획득
	 */
	private byte[] readPrivateKey() {
		Resource resource = new ClassPathResource(KEY_PATH);
		byte[] content = null;

		try (FileReader keyReader = new FileReader(resource.getURI().getPath());
			 PemReader pemReader = new PemReader(keyReader)) {
			{
				System.out.println("keyReader : " + keyReader);
				System.out.println("pemReader : " + pemReader);
				PemObject pemObject = pemReader.readPemObject();
				System.out.println("pemObject : " + Arrays.toString(pemObject.getContent()));
				System.out.println("pemContent : " + pemObject.getContent());
				System.out.println("size : " + pemObject.getContent().length);
				content = pemObject.getContent();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("content : " + content);
		return content;
	}

	public Payload decodeFromIdToken(String idToken) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(idToken);
			JWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();
			ObjectMapper objectMapper = new ObjectMapper();
			Payload payload = objectMapper.readValue(getPayload.toJSONObject().toJSONString(), Payload.class);

			if (payload != null) {
				return payload;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new AppleUnAuthrizationException();
	}

	public TokenResponse validateAuthorizationGrantCode(String clientSecret, String code) {
		Map<String, String> tokenRequest = new HashMap<>();

		tokenRequest.put("client_id", AUD);
		tokenRequest.put("client_secret", clientSecret);
		tokenRequest.put("code", code);
		tokenRequest.put("grant_type", "authorization_code");
		//tokenRequest.put("redirect_uri", APPLE_WEBSITE_URL);

		return getTokenResponse(tokenRequest);
	}

	public TokenResponse validateAnExistingRefreshToken(String clientSecret, String appleRefreshToken) {
		Map<String, String> tokenRequest = new HashMap<>();

		tokenRequest.put("client_id", AUD);
		tokenRequest.put("client_secret", clientSecret);
		tokenRequest.put("grant_type", "refresh_token");
		tokenRequest.put("refresh_token", appleRefreshToken);

		System.out.println("utilrs의 통신전!!!");
		System.out.println("clientSecret : " + clientSecret);
		System.out.println("appleRefreshToken : " + appleRefreshToken);
		System.out.println("200통신 기원!!!!!");

		return getTokenResponse(tokenRequest);
	}

	/**
	 * POST https://appleid.apple.com/auth/token
	 * @return tokenResponse
	 */
	private TokenResponse getTokenResponse(Map<String, String> tokenRequest) {
		try {
			String response = AppleApi.doPost(AUTH_TOKEN_URL, tokenRequest);
			ObjectMapper objectMapper = new ObjectMapper();
			TokenResponse tokenResponse = objectMapper.readValue(response, TokenResponse.class);

			if (tokenResponse != null) {
				return tokenResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new AppleUnAuthrizationException();
	}
}
