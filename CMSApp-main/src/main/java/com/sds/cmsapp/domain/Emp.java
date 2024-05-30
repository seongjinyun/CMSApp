package com.sds.cmsapp.domain;

import java.util.List;

import lombok.Data;

@Data
public class Emp {
	private int emp_idx;
	private Dept dept;
	private String emp_name;
	private Role role;
	
	private List<StatusLog> statusLogList;
}
