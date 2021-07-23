package com.zeepy.server.auth.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.zeepy.server.auth.dto.GetUserInfoResDto;

@Service
public class KakaoApi {

	public GetUserInfoResDto getUserInfo(String access_Token) {
		GetUserInfoResDto userInfoResDto = new GetUserInfoResDto();
		String reqUrl = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");

			//요청에 필요한 Header에 포함될 내용
			connection.setRequestProperty("Authorization", "Bearer" + access_Token);

			// int responseCode = connection.getResponseCode();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line;
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("responseBody : " + result);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);

			JSONObject properties = (JSONObject)jsonObject.get("properties");
			JSONObject kakao_account = (JSONObject)jsonObject.get("kakao_account");

			String nickname = properties.get("nickname").toString();
			String email = kakao_account.get("email").toString();

			userInfoResDto.setNickname(nickname);
			userInfoResDto.setEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfoResDto;
	}
}
