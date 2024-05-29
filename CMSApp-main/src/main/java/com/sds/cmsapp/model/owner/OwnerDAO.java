package com.sds.cmsapp.model.owner;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Owner;

@Mapper
public interface OwnerDAO {

	public void update(Owner owner);
	
}
