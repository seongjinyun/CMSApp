package com.sds.cmssettings.model.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmssettings.domain.Role;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public void insert(Role role) {
		// TODO Auto-generated method stub
		roleDAO.insert(role);
	}
	
	@Override
	public void insertRole(Role role) {
		// TODO Auto-generated method stub
		roleDAO.insertRole(role);
	}

	@Override
	public int getMaxRoleCode() {
		// TODO Auto-generated method stub
		return roleDAO.getMaxRoleCode();
	}

	@Override
	public Role selectByRoleIdx(int roleIdx) {
		// TODO Auto-generated method stub
		return roleDAO.selectByRoleIdx(roleIdx);
	}

	@Override
	public Role selectByRoleCode(int roleCode) {
		return roleDAO.selectByRoleCode(roleCode);
	}

	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return roleDAO.selectAll();
	}
}
