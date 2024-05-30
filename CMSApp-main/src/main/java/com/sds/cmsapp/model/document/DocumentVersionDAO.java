package com.sds.cmsapp.model.document;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.DocumentVersion;

@Mapper
public interface DocumentVersionDAO {

	public DocumentVersion selectByDocumentIdx(int document_idx); //resultMap="DocumentVersionMap"
}
