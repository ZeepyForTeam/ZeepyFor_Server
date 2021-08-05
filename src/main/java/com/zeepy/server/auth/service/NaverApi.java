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
public class NaverApi {

	public GetUserInfoResDto getUserInfo(String accessToken) {
		GetUserInfoResDto userInfoResDto = new GetUserInfoResDto();
		String reqUrl = "https://openapi.naver.com/v1/nid/me";
		//Get메소드에 accessToken을 헤더에 넣고
		//https://openapi.naver.com/v1/nid/me에 요청
		//반환받은 값을 보내주고 email로 로그인을 해준다.
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);

			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw new RuntimeException("네이버 사용자 정보 불러오기 실패");
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);

			JSONObject response = (JSONObject)jsonObject.get("response");

			//String email = response.get("email").toString();
			String nickname = response.get("nickname").toString();

			userInfoResDto.setEmail(nickname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfoResDto;
	}
}
