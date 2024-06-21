package com.sds.cmssetting.exception;

public class JwtException extends RuntimeException{
	
	public JwtException(String msg) {
		super(msg);
	}
	public JwtException(String msg, Throwable e) {
		super(msg, e);
	}
	public JwtException(Throwable e) {
		super(e);
	}
	
}