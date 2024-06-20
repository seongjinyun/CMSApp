package com.sds.cmsdocument.model.mastercode;

import java.util.List;

import com.sds.cmsdocument.domain.MasterCode;

public interface MasterCodeService {

	public List<MasterCode> selectAll();
	
	public MasterCode selectByStatusName(String statusName);
	
}
