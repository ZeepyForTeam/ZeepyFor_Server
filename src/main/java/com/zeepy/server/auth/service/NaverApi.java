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
@PropertySource(value = {"classpath:config/application-local.properties"})
public class NaverApi {
	@Value("${NAVER.CLIENT.ID}")
	private String client_id;
	@Value("${NAVER.SECRET}")
	private String client_secret;
	@Value("${NAVER.GETUSER.URL}")
	private String GetUserURL;
	@Value("${NAVER.AUTH.URL}")
	private String AuthURL;

	public GetUserInfoResDto getUserInfo(String accessToken) {
		GetUserInfoResDto userInfoResDto = new GetUserInfoResDto();
		//Get메소드에 accessToken을 헤더에 넣고
		//https://openapi.naver.com/v1/nid/me에 요청
		//반환받은 값을 보내주고 email로 로그인을 해준다.
		try {
			URL url = new URL(GetUserURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			if (responseCode != 200) {
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
			System.out.println("object : " + jsonObject);

			JSONObject response = (JSONObject)jsonObject.get("response");

			// String id = response.get("id").toString();
			String name = response.get("name").toString();
			String email = response.get("email").toString();

			userInfoResDto.setEmail(email);
			userInfoResDto.setName(name);
			//			userInfoResDto.setId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return userInfoResDto;
	}

	public void logout(String accessToken) {
		StringBuffer params = new StringBuffer();
		params.append("grant_type=" + "delete");
		params.append("client_id" + client_id);
		params.append("client_secret=" + client_secret);
		params.append("access_token=").append(accessToken);
		params.append("service_provider=" + "NAVER");

		String reqURL = AuthURL + "?" + params;
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			if (responseCode != 200) {
				throw new SNSUnAuthorization();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
