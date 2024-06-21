package com.sds.cmssetting.model.owner;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmssetting.domain.Owner;

@Mapper
public interface OwnerDAO {

	public void update(Owner owner);
	
}
