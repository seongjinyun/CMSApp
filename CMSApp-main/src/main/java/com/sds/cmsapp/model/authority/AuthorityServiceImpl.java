package com.sds.cmsapp.model.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Authority;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityDAO authorityDAO;
	
	@Override
	public void insert(Authority authority) {
		// TODO Auto-generated method stub
		authorityDAO.insert(authority);
	}

	@Override
	public Authority selectByAuthorityIdx(int authority_idx) {
		// TODO Auto-generated method stub
		return authorityDAO.selectByAuthorityIdx(authority_idx);
	}

	@Override
	public Authority selectByAuthorityCode(int authority_code) {
		return authorityDAO.selectByAuthorityCode(authority_code);
	}

	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return authorityDAO.selectAll();
	}
}
