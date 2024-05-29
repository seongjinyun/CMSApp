package com.sds.cmsapp.model.relationship;

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

}
