package com.sds.cmssettings.exception;

public class FolderException extends RuntimeException {
	
	public FolderException(String msg) {
		super(msg);
	}
	public FolderException(String msg, Throwable e) {
		super(msg, e);
	}
	public FolderException(Throwable e) {
		super(e);
	}
	
	

}
