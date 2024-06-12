package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class RoleAuthority {
	private int roleAuthorityIdx;
	private Role role;
	private Authority authority;
}
