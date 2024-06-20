package com.sds.cmsdocument.exception;

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
