package com.sds.cmsapp.model.dept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Dept;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptDAO deptDAO;
	
	@Override
	public int insert(Dept dept) {
		// TODO Auto-generated method stub
		deptDAO.insert(dept);
		return dept.getDept_idx();
	}

	@Override
	public List selectAllDeptName() {
		// TODO Auto-generated method stub
		return deptDAO.selectAllDeptName();
	}
	
	@Override
	public Dept selectByDeptIdx(int dept_idx) {
		return deptDAO.selectByDeptIdx(dept_idx);
	}
}
