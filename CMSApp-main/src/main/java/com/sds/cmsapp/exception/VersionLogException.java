package com.sds.cmsapp.exception;

public class VersionLogException extends RuntimeException {
	
	public VersionLogException(String msg) {
		super(msg);
	}
	public VersionLogException(String msg, Throwable e) {
		super(msg, e);
	}
	public VersionLogException(Throwable e) {
		super(e);
	}
	
	

}
