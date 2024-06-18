package com.sds.cmssettings.model.document;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sds.cmssettings.domain.DocumentVersion;
import com.sds.cmssettings.domain.ResultDocCountDTO;

@Mapper
public interface DocumentVersionDAO {
	
	@Select("SELECT * FROM document_version WHERE document_idx = #{documentIdx}")
	@ResultMap("DocumentVersionMap")
	public DocumentVersion selectByDocumentIdx(int documentIdx);
			
	public ResultDocCountDTO selectCountByStatus(int statusCode);
	
	public int insert(final DocumentVersion documentVersion);
	
	public int updateStatusByDocumentIdx(final DocumentVersion documentVeresion);
	
	public int delete(final int documentVersionIdx);
	
	public int deleteByDocumentIdx(final int documentIdx);

}
