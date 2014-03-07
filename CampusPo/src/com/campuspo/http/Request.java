package com.campuspo.http;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import com.campuspo.http.HttpExecuter.Method;

public class Request {
	
	private static final String TAG = "Request";
	
	private static final int DEFAULT_REQUEST_TIMEOUT = 10 * 1000;
	
	private Method mMethodType;
	
	private URI mUri;
	
	private HashMap<String, List<String>> mHeaders;
	
	private byte[] mRequestBody;
	
	public Request(Method methodType, URI uri,
					HashMap<String, List<String>> headers, byte[] requestBody) {
		
		mUri = uri;
		mMethodType = methodType;
		
		mHeaders = headers;
		
		mRequestBody = requestBody;
	}
	
	public Method getRequestType() {
		return mMethodType;
	}
	
	public URI getUri() {
		return mUri;
	}
	
	public HashMap<String, List<String>> getHeaders () {
		return mHeaders;
	}
	
	public byte[] getRequestBody() {
		return mRequestBody;
	}
	
	private static class RequestParams {

		
	}
}
