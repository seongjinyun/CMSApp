package com.sds.cmsapp.model.document;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.MasterCode;
import com.sds.cmsapp.domain.ResultDocCountDTO;

@Mapper
public interface DocumentVersionDAO {
	
	@Select("SELECT * FROM document_version WHERE document_idx = #{documentIdx}")
	@ResultMap("DocumentVersionMap")
	public DocumentVersion selectByDocumentIdx(int documentIdx);
			
	public ResultDocCountDTO selectCountByStatus(int statusCode);
	
	public int insert(final DocumentVersion documentVersion);
	
	public int delete(final int documentVersionIdx);
	
	public int updateStatusByDocumentIdx(final DocumentVersion documentVersion);
}
