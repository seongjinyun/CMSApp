package com.sds.cmsapp.model.dept;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Dept;

@Mapper
public interface DeptDAO {
	
	public int insert(Dept dept);
	public List selectAllDeptName();
	public List selectAll();
	public Dept selectByDeptIdx(int dept_idx);
	
}
