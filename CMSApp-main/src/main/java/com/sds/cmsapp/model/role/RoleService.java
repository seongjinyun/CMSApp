package com.sds.cmsapp.model.role;

import com.sds.cmsapp.domain.Role;

public interface RoleService {

	public void insert(Role role);
	public Role selectByRoleIdx(int role_idx);
	
}