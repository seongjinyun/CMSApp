package com.sds.cmsapp.model.authority;

import java.util.List;

import com.sds.cmsapp.domain.Authority;

public interface AuthorityService {

	public void insert(Authority authority);
	public Authority selectByAuthorityIdx(int authority_idx);
	public Authority selectByAuthorityCode(int authority_code);
	public List selectAll();
	
}
