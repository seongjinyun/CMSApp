package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Emp {
	private int emp_idx;
	private Dept dept;
	private String emp_name;
	private Role role;
}
