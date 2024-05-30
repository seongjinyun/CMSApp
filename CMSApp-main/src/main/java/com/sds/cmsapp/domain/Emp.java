package com.sds.cmsapp.domain;

import java.util.List;

import lombok.Data;

@Data
public class Emp {
	private int emp_idx;
	private String emp_name;
	
	// 부모 테이블
	private Dept dept;
	private Role role;
}
