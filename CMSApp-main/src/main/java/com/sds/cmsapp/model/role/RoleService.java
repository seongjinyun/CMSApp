package com.sds.cmsapp.model.role;

import java.util.List;

import com.sds.cmsapp.domain.Role;

public interface RoleService {

	public void insert(Role role);
	public Role selectByRoleIdx(int role_idx);
	public Role selectByRoleCode(int role_code);
	public List selectAll();
	
}
