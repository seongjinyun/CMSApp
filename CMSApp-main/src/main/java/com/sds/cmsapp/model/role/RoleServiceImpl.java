package com.sds.cmsapp.model.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Role;

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
	public Role selectByRoleIdx(int role_idx) {
		// TODO Auto-generated method stub
		return roleDAO.selectByRoleIdx(role_idx);
	}

	@Override
	public Role selectByRoleCode(int role_code) {
		return roleDAO.selectByRoleCode(role_code);
	}
}
