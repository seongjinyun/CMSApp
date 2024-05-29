package com.sds.cmsapp.model.dept;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Dept;

@Mapper
public interface DeptDAO {
	
	public void insert(Dept dept);
	public Dept selectByDeptIdx(int dept_idx);
	
}
