package com.sds.cmssetting.exception;

public class PublishedVersionException extends RuntimeException {
	
	public PublishedVersionException(String msg) {
		super(msg);
	}
	public PublishedVersionException(String msg, Throwable e) {
		super(msg, e);
	}
	public PublishedVersionException(Throwable e) {
		super(e);
	}
	
	

}
