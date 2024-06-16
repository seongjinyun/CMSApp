package com.sds.cmsapp.exception;

public class ProjectException  extends RuntimeException {
	
	public ProjectException(String msg) {
		super(msg);
	}
	public ProjectException(String msg, Throwable e) {
		super(msg, e);
	}
	public ProjectException(Throwable e) {
		super(e);
	}
	
	

}
