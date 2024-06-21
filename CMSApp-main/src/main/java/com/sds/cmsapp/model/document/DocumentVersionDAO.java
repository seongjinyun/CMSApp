package com.sds.cmsapp.model.document;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.ResultDocCountDTO;

@Mapper
public interface DocumentVersionDAO {
			
	public ResultDocCountDTO selectCountByStatus(int statusCode);
	
	public int insert(final DocumentVersion documentVersion);
	
	public int updateStatusByDocumentIdx(final DocumentVersion documentVeresion);
	
	public int updateStatusByDocumentIdxForPublishing(final DocumentVersion documentVeresion);
	
	public int delete(final int documentVersionIdx);
	
	public int deleteByDocumentIdx(final int documentIdx);
	
	// DocumentVersionMap 반환
	public DocumentVersion selectByDocumentIdx(int documentIdx);

}
