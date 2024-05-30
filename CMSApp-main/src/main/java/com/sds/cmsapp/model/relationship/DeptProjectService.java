package com.sds.cmsapp.model.relationship;

import java.util.List;

import com.sds.cmsapp.domain.DeptProject;

public interface DeptProjectService {

	public void insert(DeptProject deptProject);
	public List selectByDeptIdx(Integer dept_idx);
	public List selectOtherByDeptIdx(Integer dept_idx);
	public void delete(DeptProject deptProject);
	
}
