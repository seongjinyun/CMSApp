package com.sds.cmsapp.exception;

public class DocumentException extends RuntimeException {
	
	public DocumentException(String msg) {
		super(msg);
	}
	public DocumentException(String msg, Throwable e) {
		super(msg, e);
	}
	public DocumentException(Throwable e) {
		super(e);
	}
	
	

}
