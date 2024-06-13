package com.sds.cmsapp.model.relationship;

import java.util.List;

import com.sds.cmsapp.domain.DeptProject;

public interface DeptProjectService {

	public void insert(DeptProject deptProject);
	public List selectByDeptIdx(int deptIdx);
	public List selectOtherByDeptIdx(int deptIdx);
	public List selectEmptyDept();
	public List selectEmptyProject();
	public void delete(DeptProject deptProject);
	
}
