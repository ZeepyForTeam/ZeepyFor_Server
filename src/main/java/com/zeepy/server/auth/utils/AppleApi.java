package com.zeepy.server.auth.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.zeepy.server.common.CustomExceptionHandler.CustomException.SNSUnAuthorization;

public class AppleApi {

	public static String doGet(String reqUrl) {
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

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

			br.close();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SNSUnAuthorization();
		}
	}

	public static String doPost(String reqUrl, Map<String, String> tokenRequest) {
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, String> param : tokenRequest.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			String line;
			String result = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("responseResult : " + result);

			if (responseCode != 200) {
				throw new SNSUnAuthorization();
			}

			br.close();

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			throw new SNSUnAuthorization();
		}
	}
}