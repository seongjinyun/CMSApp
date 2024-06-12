package com.sds.cmsapp.model.document;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.DocumentVersion;

@Mapper
public interface DocumentDetailDAO {
	public DocumentVersion documentDetailSelect(int documentIdx);

}
