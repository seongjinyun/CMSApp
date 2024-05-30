package com.sds.cmsapp.model.dept;

import java.util.List;

import com.sds.cmsapp.domain.Dept;

public interface DeptService {

	public int insert(Dept dept);
	public List selectAllDeptName();
	public Dept selectByDeptIdx(int dept_idx);
	
}
