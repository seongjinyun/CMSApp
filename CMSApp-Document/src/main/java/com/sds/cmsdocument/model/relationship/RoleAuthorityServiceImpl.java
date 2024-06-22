package com.sds.cmsdocument.model.relationship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsdocument.domain.Authority;
import com.sds.cmsdocument.domain.RoleAuthority;

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
	public List<Authority> selectAuthoritiesByRoleCode(int roleCode) {
		// TODO Auto-generated method stub
		return roleAuthorityDAO.selectAuthoritiesByRoleCode(roleCode);
	}

	@Override
	public void deleteAuthoritiesByRoleCode(int roleCode) {
		// TODO Auto-generated method stub
		roleAuthorityDAO.deleteAuthoritiesByRoleCode(roleCode);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		roleAuthorityDAO.deleteAll();
	}	
}
