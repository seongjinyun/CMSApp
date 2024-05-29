package com.sds.cmsapp.model.dept;

import com.sds.cmsapp.domain.Dept;

public interface DeptService {

	public void insert(Dept dept);
	public Dept selectByDeptIdx(int dept_idx);
	
}
