package com.sds.cmsapp.model.relationship;

import java.util.List;

import com.sds.cmsapp.domain.Authority;
import com.sds.cmsapp.domain.RoleAuthority;

public interface RoleAuthorityService {

	public void insertAuthorityIntoRole(RoleAuthority roleAuthority);
	public List<Authority> selectAuthoritiesByRoleCode(int role_code);
	public void deleteAuthoritiesByRoleCode(int role_code);
	
	// test
	public void deleteAll();
}
