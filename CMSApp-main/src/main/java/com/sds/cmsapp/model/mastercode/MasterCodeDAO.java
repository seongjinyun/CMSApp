package com.sds.cmsapp.model.mastercode;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.MasterCode;

@Mapper
public interface MasterCodeDAO {
	
	public MasterCode select(int status_code); //returnType="MasterCode"
	
	public List<MasterCode> selectAll();

}
