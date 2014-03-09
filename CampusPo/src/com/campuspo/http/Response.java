package com.campuspo.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
	
	private int mStatusCode;
	
	private Map<String, List<String>> mHeaders;
	
	private byte[] mBody;
	
	public Response(int statusCode, Map<String, List<String>> map,
						byte[] body) {
		mStatusCode = statusCode;
		mHeaders = map;
		mBody = body;
	}
	
	public int getStatusCode;
	
	public Map<String, List<String>> getHeaders() {
		return mHeaders;
	}
	
	public byte[] getBody() {
		return mBody;
	}
}
