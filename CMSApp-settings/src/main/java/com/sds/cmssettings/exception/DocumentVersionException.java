package com.sds.cmssettings.exception;

public class DocumentVersionException extends RuntimeException {
	
	public DocumentVersionException(String msg) {
		super(msg);
	}
	public DocumentVersionException(String msg, Throwable e) {
		super(msg, e);
	}
	public DocumentVersionException(Throwable e) {
		super(e);
	}
	
	

}
