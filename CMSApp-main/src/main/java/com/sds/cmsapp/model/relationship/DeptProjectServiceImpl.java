package com.sds.cmsapp.model.relationship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.DeptProject;

@Service
public class DeptProjectServiceImpl implements DeptProjectService {

	@Autowired
	private DeptProjectDAO deptProjectDAO;
	
	@Override
	public void insert(DeptProject deptProject) {
		// TODO Auto-generated method stub
		deptProjectDAO.insert(deptProject);
	}

	@Override
	public List selectByDeptIdx(int dept_idx) {
		// TODO Auto-generated method stub
		return deptProjectDAO.selectByDeptIdx(dept_idx);
	}

	@Override
	public List selectOtherByDeptIdx(int dept_idx) {
		// TODO Auto-generated method stub
		return deptProjectDAO.selectOtherByDeptIdx(dept_idx);
	}

	@Override
	public void delete(DeptProject deptProject) {
		// TODO Auto-generated method stub
		deptProjectDAO.delete(deptProject);
	}

}
