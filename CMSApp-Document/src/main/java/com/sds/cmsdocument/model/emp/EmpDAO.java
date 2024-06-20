package com.sds.cmsdocument.model.emp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsdocument.domain.Emp;

@Mapper
public interface EmpDAO {
	
	public void insert(Emp emp);
	public List selectAllEmpName();
	public List selectAll();
	public int getTotalCount();
	public List selectEmpPage(Map map);

	// EmpMap 반환
	public Emp selectByEmpIdx(int empIdx);
	public void update(Emp emp);
	public void updateDept(Emp emp);
}
