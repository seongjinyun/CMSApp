package com.sds.cmsapp.model.publishing;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.PublishedVersionName;

@Mapper
public interface PublishedVersionNameDAO {
	public int insert(final PublishedVersionName publishedVersionName);
	
	public int delete(final int publishedVersionNameIdx);
	
	public PublishedVersionName select(final int publishedVersionNameIdx);
	
	public List<PublishedVersionName> selectAll();
}
