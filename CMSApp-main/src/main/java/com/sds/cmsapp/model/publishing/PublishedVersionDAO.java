package com.sds.cmsapp.model.publishing;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.PublishedVersion;

@Mapper
public interface PublishedVersionDAO {
	
	public int insert(final PublishedVersion publishedVersion);
	
	public int update(final PublishedVersion publishedVersion);
	
	public int delete(final int publishedVersionIdx);
	
	public int deleteByDocumentIdx(final int documentIdx);
	
	public PublishedVersion select(final int publishedVersionIdx);
	
	public PublishedVersion selectByDocumentIdx(final int documentIdx);
	
	public PublishedVersion selectLastestPublishedVersionByDocumentIdx(final int documentIdx);

}
