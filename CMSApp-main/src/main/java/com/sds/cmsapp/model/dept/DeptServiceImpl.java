package com.sds.cmsapp.model.dept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Dept;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptDAO deptDAO;
	
	@Override
	public void insert(Dept dept) {
		deptDAO.insert(dept);
	}

	@Override
	public Dept selectByDeptIdx(int dept_idx) {
		return deptDAO.selectByDeptIdx(dept_idx);
	}
}
