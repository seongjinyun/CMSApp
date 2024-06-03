package com.sds.cmsapp.model.emp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Emp;

@Mapper
public interface EmpDAO {
	
	public void insert(Emp emp);
	public List selectAllEmpName();
	public List selectAll();

	// EmpMap 반환
	public Emp selectByEmpIdx(int emp_idx);
	public void update(Emp emp);
	public void updateDept(Emp emp);
	
}
