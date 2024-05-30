package com.sds.cmsapp.model.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.MasterCode;

@Service
public class MasterCodeServiceImpl implements MasterCodeService {

	@Autowired
	MasterCodeDAO masterCodeDAO;

	@Override
	public MasterCode select(int status_code) {
		return masterCodeDAO.select(status_code);
	}
		
}
