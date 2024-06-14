package com.sds.cmsapp.model.document;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sds.cmsapp.domain.DocumentVersion;

@Mapper
public interface DocumentVersionDAO {
	
	@Select("SELECT * FROM document_version WHERE document_idx = #{documentIdx}")
	@ResultMap("DocumentVersionMap")
	public DocumentVersion selectByDocumentIdx(int document_idx);
	
	@Select("SELECT COUNT(dv.document_idx), status_code\n"
			+ "FROM document_version dv\n"
			+ "LEFT JOIN trash t ON dv.document_idx = t.document_idx\n"
			+ "WHERE t.trash_idx IS NULL\n"
			+ "GROUP BY status_code\n"
			+ "HAVING status_code = #{statusCode}")
	public Integer countByStatus(int statusCode);
	
	public int insert(final DocumentVersion documentVersion);
	
	public int delete(final int documentVersionIdx);
}
