package com.sds.cmsapp.model.relationship;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.DeptProject;

@Mapper
public interface DeptProjectDAO {
	
	public void insert(DeptProject deptProject);
	
}
