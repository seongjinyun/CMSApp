package com.sds.cmsdocument.exception;

public class UploadException extends RuntimeException{
	
	public UploadException(String msg) {
		super(msg);
	}
	public UploadException(String msg, Throwable e) {
		super(msg, e);
	}
	public UploadException(Throwable e) {
		super(e);
	}
	
}