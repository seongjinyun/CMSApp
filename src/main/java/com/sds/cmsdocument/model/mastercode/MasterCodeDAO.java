package com.sds.cmsdocument.model.mastercode;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsdocument.domain.MasterCode;

@Mapper
public interface MasterCodeDAO {
	
	@Select("SELECT * FROM mastercode WHERE status_code = #{statusCode}")
	public MasterCode select(int statusCode); 
	
	@Select("SELECT * FROM mastercode WHERE status_name = #{statusName}")
	public MasterCode selectByStatusName(String statusName);
	
	@Select("SELECT * FROM mastercode")
	public List<MasterCode> selectAll();

}
