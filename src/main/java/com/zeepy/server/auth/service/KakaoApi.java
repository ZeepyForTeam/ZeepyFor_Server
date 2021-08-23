package com.zeepy.server.auth.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.zeepy.server.auth.dto.GetUserInfoResDto;
import com.zeepy.server.common.CustomExceptionHandler.CustomException.SNSUnAuthorization;

@Component
@PropertySource(value = {"classpath:security/application.properties"})
public class KakaoApi {
	@Value("${KAKAO.GETUSER.URL}")
	private String GetUserURL;
	@Value("${KAKAO.LOGOUT.URL}")
	private String LogoutURL;

	public GetUserInfoResDto getUserInfo(String accessToken) {
		System.out.println("accessToken !!!!!!! : " + accessToken);
		GetUserInfoResDto userInfoResDto = new GetUserInfoResDto();
		try {
			URL url = new URL(GetUserURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			//요청에 필요한 Header에 포함될 내용
			String authorizationHeader = "Bearer " + accessToken.trim();
			System.out.println("code : " + authorizationHeader);
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			System.out.println("method : " + conn.getRequestMethod());

			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			if (responseCode == 401 || responseCode == 400) {
				throw new SNSUnAuthorization();
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);

			JSONObject kakao_account = (JSONObject)jsonObject.get("kakao_account");

			String id = (String) jsonObject.get("id");
//			String email = kakao_account.get("email").toString();

//			userInfoResDto.setEmail(email);
			userInfoResDto.setId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return userInfoResDto;
	}

	public void logout(String accessToken) {
		System.out.println("accessToken : " + accessToken);
		try {
			URL url = new URL(LogoutURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			if (responseCode == 401) {
				throw new SNSUnAuthorization();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
