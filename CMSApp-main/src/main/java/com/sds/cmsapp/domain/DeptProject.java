package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class DeptProject {
	private int dept_project_idx;
	private Dept dept;
	private Project project;
}
