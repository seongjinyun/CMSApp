package com.sds.cmssettings.model.document;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmssettings.domain.Onedit;

@Mapper
public interface OneditDAO {
	public Onedit select(final int onEditIdx);
	
	public void insert(final Onedit onEdit);
	
	public void delete(final int onEdit);
	
	public void deleteByDocumentIdx(final int documentIdx);
	
	public List<Onedit> selectAll();
	
	public List<Onedit> selectByEmpIdx(final int empIdx);
	
	public List<Onedit> selectByDocumentIdx(final int documentIdx);
}
