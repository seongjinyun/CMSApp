package com.sds.cmsapp.model.relationship;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.DeptProject;

@Mapper
public interface DeptProjectDAO {
	
	public void insert(DeptProject deptProject);
	public List selectByDeptIdx(Integer dept_idx);
	public List selectOtherByDeptIdx(Integer dept_idx);
	public void delete(DeptProject deptProject);
	
}
