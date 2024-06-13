package com.sds.cmsapp.model.authority;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Authority;

@Mapper
public interface AuthorityDAO {

	public void insert(Authority authority);
	public Authority selectByAuthorityIdx(int authorityIdx);
	public Authority selectByAuthorityCode(int authorityCode);
	public List selectAll();
	
}
