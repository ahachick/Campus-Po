package com.campuspo.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.campuspo.BuildConfig;



public abstract class HttpExecuter {
	
	private static final String TAG = "HttpExecuter";
	
	public static final String KEY_CODE = "code";
	public static final String KEY_DATA = "data";
	public static final String KEY_ERROR = "error";
	public static final String KEY_MESSAGE = "message";
		
	public static final int CODE_SUCCESS = 1;
	public static final int CODE_FAIL = 0;
	public static final int CODE_TIMEOUT = -1;
	public static final int CODE_NETWORK_FAILED = -2;
	
	//模版方法
	public Result excute() {
		
		if(BuildConfig.DEBUG)
			Log.d(TAG, "buildRequest()");
		Request request = buildRequest();
		if(BuildConfig.DEBUG)
			Log.d(TAG, "doRequest()");
		Response response = doRequest(request);
		if(BuildConfig.DEBUG)
			Log.d(TAG, "buildResult()");
		Result result = buildResult(response);
		
		process(result);
		
		return result;
	}
	
	private Response doRequest(Request request) {
		HttpURLConnection conn = null;
		Response response = null;
		int status = -1;
		try {

			URL url = request.getUri().toURL();
			conn = (HttpURLConnection) url.openConnection();
			//add request header if set
			if (request.getHeaders() != null) {
				for (String header : request.getHeaders().keySet()) {
					for (String value : request.getHeaders().get(header)) {
						conn.addRequestProperty(header, value);
					}
				}
			}
			//set timeout 10 seconds
			conn.setReadTimeout(10 * 1000);
			//set use caches false
			conn.setUseCaches(false);
			//judge the type of request , GET: do not output; POST: use output;
			switch (request.getRequestType()) {
			case GET:
				conn.setDoOutput(false);
				break;
			case POST:
				byte[] payload = request.getRequestBody();
				conn.setDoOutput(true);
				conn.setFixedLengthStreamingMode(payload.length);
				conn.getOutputStream().write(payload);
				status = conn.getResponseCode();
			default:
				break;
			}
			
			status = conn.getResponseCode();
			
			if (conn.getContentLength() > 0) {
				
				if(BuildConfig.DEBUG)
					Log.d(TAG, "do request--status code = " + status + ";contentLength = " + conn.getContentLength());
					
				BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
				byte[] body = readStream(in);
				if(BuildConfig.DEBUG)
					Log.d(TAG, new String(body, "UTF-8"));
				response = new Response(conn.getResponseCode(), conn.getHeaderFields(), body);
			} else {
				response = new Response(status, conn.getHeaderFields(), new byte[] {});
			}

		} catch (IOException e) {
			
			response  = new Response(-1, new HashMap<String, List<String>>(), new byte[] {});
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		
		return response;
	}

	private static byte[] readStream(InputStream in) throws IOException {
		byte[] buf = new byte[1024];
		int count = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		while ((count = in.read(buf)) != -1)
			out.write(buf, 0, count);
		return out.toByteArray();

	}
	
	protected void process(Result result) {
		
	}

	
	protected abstract Request buildRequest();
	
	protected abstract Result buildResult(Response response);
	
	
	public static enum Method {
		GET, POST
	}
	
	public static class HttpMetaData {
		
		public static final String DOMAIN = "http://125.216.251.17:8080/CampusPo_2";
		//public static final String DOMAIN = "http://localhost:8080/CampusPo_2";
		//public static final String DOMAIN = "http://www.campuspo.com";
	}
}
