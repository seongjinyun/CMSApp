package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Emp {
	private int empIdx;
	private String emp_name;
	
	// 부모 테이블
	private Dept dept;
	private Role role;
}
