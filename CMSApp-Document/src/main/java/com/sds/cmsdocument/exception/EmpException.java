package com.sds.cmsdocument.exception;

public class EmpException extends RuntimeException {
	
	public EmpException(String msg) {
		super(msg);
	}
	
	public EmpException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public EmpException(Throwable e) {
		super(e);
	}
	
}
