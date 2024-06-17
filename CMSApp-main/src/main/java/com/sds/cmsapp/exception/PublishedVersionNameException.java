package com.sds.cmsapp.exception;

public class PublishedVersionNameException extends RuntimeException {
	
	public PublishedVersionNameException(String msg) {
		super(msg);
	}
	public PublishedVersionNameException(String msg, Throwable e) {
		super(msg, e);
	}
	public PublishedVersionNameException(Throwable e) {
		super(e);
	}
	
	

}
