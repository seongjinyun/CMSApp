package com.sds.cmsapp.model.emp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Emp;

@Mapper
public interface EmpDAO {
	
	public void insert(Emp emp);
	public List selectAllEmpName();
	public Emp selectByEmpIdx(int emp_idx);
	public void update(Emp emp);
	
}
