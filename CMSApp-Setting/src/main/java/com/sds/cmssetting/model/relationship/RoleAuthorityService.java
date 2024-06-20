package com.sds.cmssetting.model.relationship;

import java.util.List;

import com.sds.cmssetting.domain.Authority;
import com.sds.cmssetting.domain.RoleAuthority;

public interface RoleAuthorityService {

	public void insertAuthorityIntoRole(RoleAuthority roleAuthority);
	public List<Authority> selectAuthoritiesByRoleCode(int roleCode);
	public void deleteAuthoritiesByRoleCode(int roleCode);
	
	// test
	public void deleteAll();
}
