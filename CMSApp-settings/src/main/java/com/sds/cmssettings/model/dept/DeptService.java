package com.sds.cmssettings.model.dept;

import java.util.List;

import com.sds.cmssettings.domain.Dept;

public interface DeptService {

	public int insert(final Dept dept);
	public List<Dept> selectAllDeptName();
	public List<Dept> selectAll();
	public Dept selectByDeptIdx(final int deptIdx);
	public void delete(final Dept dept);
	
}
