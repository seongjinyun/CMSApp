package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class RoleAuthority {
	private int role_authority_idx;
	private Role role;
	private Authority authority;
}
