package com.sds.cmsapp.model.role;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Role;

@Mapper
public interface RoleDAO {

	public void insert(Role role);
	public Role selectByRoleIdx(int role_idx);
	public Role selectByRoleCode(int role_code);
	public List selectAll();
	
}
