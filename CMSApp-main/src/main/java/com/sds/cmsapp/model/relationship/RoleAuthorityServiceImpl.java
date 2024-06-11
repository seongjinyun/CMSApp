package com.sds.cmsapp.model.relationship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Authority;
import com.sds.cmsapp.domain.RoleAuthority;

@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService {

	@Autowired
	RoleAuthorityDAO roleAuthorityDAO;

	@Override
	public void insertAuthorityIntoRole(RoleAuthority roleAuthority) {
		// TODO Auto-generated method stub
		roleAuthorityDAO.insertAuthorityIntoRole(roleAuthority);
	}

	@Override
	public List<Authority> selectAuthoritiesByRoleCode(int role_code) {
		// TODO Auto-generated method stub
		return roleAuthorityDAO.selectAuthoritiesByRoleCode(role_code);
	}

	@Override
	public void deleteAuthoritiesByRoleCode(int role_code) {
		// TODO Auto-generated method stub
		roleAuthorityDAO.deleteAuthoritiesByRoleCode(role_code);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		roleAuthorityDAO.deleteAll();
	}	
}
