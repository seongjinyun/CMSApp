package com.sds.cmsapp.model.status;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.MasterCode;

@Mapper
public interface MasterCodeDAO {
	
	public MasterCode select(int status_code);

}
