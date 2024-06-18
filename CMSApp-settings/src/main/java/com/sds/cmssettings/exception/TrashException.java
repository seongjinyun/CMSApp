package com.sds.cmssettings.exception;

public class TrashException extends RuntimeException {
	
	public TrashException(String msg) {
		super(msg);
	}
	public TrashException(String msg, Throwable e) {
		super(msg, e);
	}
	public TrashException(Throwable e) {
		super(e);
	}
	
	

}
