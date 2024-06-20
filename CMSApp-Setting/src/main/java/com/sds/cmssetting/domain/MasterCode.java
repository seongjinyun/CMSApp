package com.sds.cmssetting.domain;

import lombok.Data;

@Data
public class MasterCode {
	private int masterCodeIdx;
	private String statusName;
	private int statusCode;
	
	public MasterCode() {
		
	}
	
	public MasterCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public MasterCode(String statusName) {
		this.statusName = statusName;
	}
	
}
