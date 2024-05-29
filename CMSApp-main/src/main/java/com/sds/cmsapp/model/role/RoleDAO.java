package com.sds.cmsapp.model.role;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Role;

@Mapper
public interface RoleDAO {

	public void insert(Role role);
	public Role selectByRoleIdx(int role_idx);
	
}
