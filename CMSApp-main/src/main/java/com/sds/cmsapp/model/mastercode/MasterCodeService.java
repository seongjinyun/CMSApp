package com.sds.cmsapp.model.mastercode;

import java.util.List;

import com.sds.cmsapp.domain.MasterCode;

public interface MasterCodeService {

	public List<MasterCode> selectAll();
	
	public MasterCode selectByStatusName(String statusName);
	
}
