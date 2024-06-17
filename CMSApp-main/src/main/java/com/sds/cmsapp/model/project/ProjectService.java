package com.sds.cmsapp.model.project;

import java.util.List;

import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.domain.RequestDocFilterDTO;

public interface ProjectService {
	
	public int insert(Project project);

	public Project selectByProjectIdx(int projectIdx);
	public Project selectByProjectName(String projectName);
	public List<Project> selectAll();
	
	public void delete(Project project);
}
