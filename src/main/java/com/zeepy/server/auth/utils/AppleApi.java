package com.zeepy.server.auth.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AppleApi {

	public static String doGet(String reqUrl) {
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

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
			throw new RuntimeException(e);
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
			// System.out.println("client_id : "+tokenRequest.get("client_id"));
			// System.out.println("client_secret : "+tokenRequest.get("client_secret"));
			// System.out.println("grant_type : "+tokenRequest.get("grant_type"));
			// System.out.println("code : "+tokenRequest.get("code"));
			//
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// //conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			// conn.setRequestProperty("client_id",tokenRequest.get("client_id"));
			// conn.setRequestProperty("client_secret",tokenRequest.get("client_secret"));
			// conn.setRequestProperty("grant_type",tokenRequest.get("grant_type"));
			// conn.setRequestProperty("code",tokenRequest.get("code"));

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
				System.out.println("뺴애애애애애액");
			}

			br.close();

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}