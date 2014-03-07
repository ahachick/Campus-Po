package com.campuspo.http;

import java.io.Serializable;

public class Result {
	
	private int mResultCode;
	private String mErrorMessage;
	
	private Serializable mEntity;
	
	public Result(int resultCode, String errorMsg, Serializable entity) {
		mResultCode = resultCode;
		mErrorMessage = errorMsg;
		mEntity = entity;
	}
	
	public int getResultCode() {
		return mResultCode;
	}
	
	public String getErrorMessage() {
		return mErrorMessage;
	}
	
	public Serializable getEntity() {
		return mEntity;
	}
}
