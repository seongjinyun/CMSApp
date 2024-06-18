package com.sds.cmssettings.model.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmssettings.domain.Authority;

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
	public Authority selectByAuthorityIdx(int authorityIdx) {
		// TODO Auto-generated method stub
		return authorityDAO.selectByAuthorityIdx(authorityIdx);
	}

	@Override
	public Authority selectByAuthorityCode(int authorityCode) {
		return authorityDAO.selectByAuthorityCode(authorityCode);
	}

	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return authorityDAO.selectAll();
	}
}
