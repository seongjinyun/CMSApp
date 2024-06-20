package com.sds.cmsdocument.domain;

import lombok.Data;

@Data
public class Emp {
	private int empIdx;
	private String empName;
	
	// 부모 테이블
	private Dept dept;
	private Role role;
	
	public Emp() {
	}
	
	public Emp(int empIdx) {
		this.empIdx = empIdx;
	}

}
