package com.sds.cmsclient.model.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsclient.domain.Project;

@Mapper
public interface MainDAO {
	public List<Project> projcetAllSelect();
}
