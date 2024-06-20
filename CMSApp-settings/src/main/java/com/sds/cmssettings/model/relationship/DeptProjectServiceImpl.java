package com.sds.cmssettings.model.relationship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmssettings.domain.DeptProject;

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
	public List selectByDeptIdx(int deptIdx) {
		// TODO Auto-generated method stub
		return deptProjectDAO.selectByDeptIdx(deptIdx);
	}

	@Override
	public List selectOtherByDeptIdx(int deptIdx) {
		// TODO Auto-generated method stub
		return deptProjectDAO.selectOtherByDeptIdx(deptIdx);
	}
	
	@Override
	public List selectEmptyDept() {
		// TODO Auto-generated method stub
		return deptProjectDAO.selectEmptyDept();
	}
	
	@Override
	public List selectEmptyProject() {
		// TODO Auto-generated method stub
		return deptProjectDAO.selectEmptyProject();
	}

	@Override
	public void delete(DeptProject deptProject) {
		// TODO Auto-generated method stub
		deptProjectDAO.delete(deptProject);
	}
}
