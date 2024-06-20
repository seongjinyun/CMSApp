package com.sds.cmsdocument.model.document;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.domain.MasterCode;
import com.sds.cmsdocument.domain.ResultDocCountDTO;

@Mapper
public interface DocumentVersionDAO {
			
	public ResultDocCountDTO selectCountByStatus(int statusCode);
	
	public int insert(final DocumentVersion documentVersion);
	
	public int updateStatusByDocumentIdx(final DocumentVersion documentVeresion);
	
	public int delete(final int documentVersionIdx);
	
	public int deleteByDocumentIdx(final int documentIdx);
	
	// DocumentVersionMap 반환
	public DocumentVersion selectByDocumentIdx(int documentIdx);

}
