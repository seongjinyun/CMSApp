package com.sds.cmsapp.model.mastercode;

import java.util.List;

import com.sds.cmsapp.domain.MasterCode;

public interface MasterCodeService {

	public MasterCode select(int status_code); 	//returnType="MasterCode"
	
	public List<MasterCode> selectAll();
	
}
