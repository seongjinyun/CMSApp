package com.sds.cmsapp.exception;

public class StatusLogException extends RuntimeException {
	
	public StatusLogException(String msg) {
		super(msg);
	}
	public StatusLogException(String msg, Throwable e) {
		super(msg, e);
	}
	public StatusLogException(Throwable e) {
		super(e);
	}
	
	

}
