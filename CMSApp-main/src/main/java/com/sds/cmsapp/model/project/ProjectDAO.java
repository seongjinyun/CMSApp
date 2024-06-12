package com.sds.cmsapp.model.project;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.domain.RequestDocumentDTO;

@Mapper
public interface ProjectDAO {
	
	public int insert(Project project);
	public Project selectByProjectIdx(int project_idx);
	
	public List<Project> selectAll();
	
	//public List<Project> selectAllByFilteredProjectIdx(RequestDocumentDTO requestDocumentDTO);
	
}
