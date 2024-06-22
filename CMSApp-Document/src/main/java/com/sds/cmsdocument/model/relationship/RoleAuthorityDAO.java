package com.sds.cmsdocument.model.relationship;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsdocument.domain.Authority;
import com.sds.cmsdocument.domain.RoleAuthority;

@Mapper
public interface RoleAuthorityDAO {

	public void insertAuthorityIntoRole(RoleAuthority roleAuthority);
	public List<Authority> selectAuthoritiesByRoleCode(int roleCode);
	public void deleteAuthoritiesByRoleCode(int roleCode);
	
	// test
	public void deleteAll();
}
