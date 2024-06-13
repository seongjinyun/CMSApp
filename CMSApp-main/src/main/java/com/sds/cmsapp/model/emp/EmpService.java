package com.sds.cmsapp.model.emp;

import java.util.List;
import java.util.Map;

import com.sds.cmsapp.domain.Emp;

public interface EmpService {

	public void insert(Emp emp);
	
	// EmpMap 반환
	public List selectAllEmpName();
	public List selectAll();
	public int getTotalCount();
	public List selectEmpPage(Map map);
	
	public Emp selectByEmpIdx(int empIdx);
	public void update(Emp emp);
	public void updateDept(Emp emp);
	
}
