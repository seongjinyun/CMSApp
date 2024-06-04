package com.sds.cmsapp.model.emp;

import java.util.List;

import com.sds.cmsapp.domain.Emp;

public interface EmpService {

	public void insert(Emp emp);
	
	// EmpMap 반환
	public List selectAllEmpName();
	public List selectAll();
	
	public Emp selectByEmpIdx(int emp_idx);
	public void update(Emp emp);
	public void updateDept(Emp emp);
	
}
