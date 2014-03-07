package com.campuspo.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

public class HttpUtils {
	
	public static byte[] parseParamsToByteArray(HashMap<String, String> params) {

		StringBuilder sb = new StringBuilder("?");

		for (Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
		}
		String requestString;
		try {
			//encode the content by UTF-8
			requestString = URLEncoder.encode(
					sb.toString().substring(0, sb.length() - 1), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			requestString = "";
			e.printStackTrace();
		}
		
		return requestString.getBytes();
	}
	

}
