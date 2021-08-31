package com.zeepy.server.auth.utils;

import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.zeepy.server.common.CustomExceptionHandler.CustomException.SNSUnAuthorization;

import sun.security.ec.ECPrivateKeyImpl;

@Component
@PropertySource(value = {"classpath:config/application-local.properties"})
public class AppleUtils {

	@Value("${APPLE.PUBLICKEY.URL}")
	private String APPLE_PUBLIC_KEYS_URL;

	@Value("${APPLE.AUTH.TOKEN.URL}")
	private String AUTH_TOKEN_URL;

	@Value("${APPLE.ISS}")
	private String ISS;

	@Value("${APPLE.AUD}")
	private String AUD;

	@Value("${APPLE.TEAM.ID}")
	private String TEAM_ID;

	@Value("${APPLE.KEY.ID}")
	private String KEY_ID;

	@Value("${APPLE.KEY.PATH}")
	private String KEY_PATH;

	@Value(("${APPLE.NONCE}"))
	private String NONCE;

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

			if (!NONCE.equals(payload.getClaim("nonce"))) {
				System.out.println("nonce 다름!");
			}
			if (!ISS.equals(payload.getIssuer())) {
				System.out.println("issue 다름!");
			}
			if (!AUD.equals(payload.getAudience().get(0))) {
				System.out.println("aud 다름!");
			}
			System.out.println("AUD : " + AUD + " " + "AUD2 : " + payload.getAudience().get(0));
			//NONCE, ISS, AUD
			if (!NONCE.equals(payload.getClaim("nonce"))
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
			throw new SNSUnAuthorization();
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
			throw new SNSUnAuthorization();
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
				PemObject pemObject = pemReader.readPemObject();
				content = pemObject.getContent();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new SNSUnAuthorization();
		}
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
			throw new SNSUnAuthorization();
		}
		throw new AppleUnAuthrizationException();
	}

	public TokenResponse validateAuthorizationGrantCode(String clientSecret, String code) {
		Map<String, String> tokenRequest = new HashMap<>();

		tokenRequest.put("client_id", AUD);
		tokenRequest.put("client_secret", clientSecret);
		tokenRequest.put("code", code);
		tokenRequest.put("grant_type", "authorization_code");

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
			throw new SNSUnAuthorization();
		}
		throw new AppleUnAuthrizationException();
	}
}
