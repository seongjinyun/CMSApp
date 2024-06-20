package com.sds.cmssetting.model.dept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmssetting.domain.Dept;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptDAO deptDAO;
	
	@Override
	public int insert(final Dept dept) {
		// TODO Auto-generated method stub
		deptDAO.insert(dept);
		return dept.getDeptIdx();
	}

	@Override
	public List<Dept> selectAllDeptName() {
		// TODO Auto-generated method stub
		return deptDAO.selectAllDeptName();
	}
	
	@Override
	public List<Dept> selectAll() {
		// TODO Auto-generated method stub
		return deptDAO.selectAll();
	}
	
	@Override
	public Dept selectByDeptIdx(final int deptIdx) {
		return deptDAO.selectByDeptIdx(deptIdx);
	}

	@Override
	public void delete(Dept dept) {
		// TODO Auto-generated method stub
		deptDAO.delete(dept);
	}
}
