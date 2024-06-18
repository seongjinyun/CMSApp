package com.sds.cmssettings.model.owner;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmssettings.domain.Owner;

@Mapper
public interface OwnerDAO {

	public void update(Owner owner);
	
}
