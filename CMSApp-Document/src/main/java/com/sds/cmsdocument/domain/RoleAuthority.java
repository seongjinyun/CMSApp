package com.sds.cmsdocument.domain;

import lombok.Data;

@Data
public class RoleAuthority {
	private int roleAuthorityIdx;
	private Role role;
	private Authority authority;
}
