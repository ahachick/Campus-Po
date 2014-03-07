package com.campuspo.http;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import android.util.Log;

import com.campuspo.BuildConfig;

public class PublishPosterExecuter extends HttpExecuter {

	private static final String TAG = PublishPosterExecuter.class
			.getSimpleName();

	private static final Method sMethodType = Method.GET;

	private static final String PATH = "/releasePoster";

	private HashMap<String, String> mParams;

	public PublishPosterExecuter(HashMap<String, String> params) {
		this.mParams = params;
	}

	@Override
	protected Request buildRequest() {

		/*URI uri = URI.create(HttpExecuter.HttpMetaData.DOMAIN + PATH);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		StringBuilder sb = new StringBuilder("?");

		for (Entry entry : mParams.entrySet()) {
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
		byte[] requestBody = requestString.getBytes();

		return new Request(mMethodType, uri, null, requestBody);*/
		
		StringBuilder sb = new StringBuilder();
		sb.append(HttpExecuter.HttpMetaData.DOMAIN).append(PATH);
		if(mParams != null)
			sb.append("?");
			for(Entry<String, String> entry: mParams.entrySet()) {
				try {
					sb.append(entry.getKey()).append("=")
					.append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
		
		String uriString = sb.substring(0, sb.length() - 1);
		
		if(BuildConfig.DEBUG)
			Log.d(TAG, uriString);
		URI uri = URI.create(uriString);
		
		Request request = new Request(sMethodType, uri, null, null);
		
		return request;
	}

	@Override
	protected Result buildResult(Response response) {
		
		int code = response.getStatusCode;

		return new Result(code, null, null);
	}

	//save the result to contentProvider or save to draft
	@Override
	protected void process(Result result) {

	}

}
