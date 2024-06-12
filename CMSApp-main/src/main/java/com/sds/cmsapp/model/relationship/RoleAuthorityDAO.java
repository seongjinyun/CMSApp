package com.sds.cmsapp.model.relationship;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Authority;
import com.sds.cmsapp.domain.RoleAuthority;

@Mapper
public interface RoleAuthorityDAO {

	public void insertAuthorityIntoRole(RoleAuthority roleAuthority);
	public List<Authority> selectAuthoritiesByRoleCode(int roleCode);
	public void deleteAuthoritiesByRoleCode(int roleCode);
	
	// test
	public void deleteAll();
}
