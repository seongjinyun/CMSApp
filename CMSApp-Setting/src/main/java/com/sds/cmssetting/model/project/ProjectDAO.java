package com.sds.cmssetting.model.project;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sds.cmssetting.domain.Project;

@Mapper
public interface ProjectDAO {
	
	public int insert(Project project);

	public Project selectByProjectIdx(int project_idx);
	
	@Select("SELECT * FROM project WHERE project_name = #{projectName}")
	public Project selectByProjectName(String projectName);
	
	@Select("SELECT * FROM project WHERE folder_idx = #{folderIdx}")
	public Project selectByFolderIdx(int folderIdx);
	
	public List<Project> selectAll();
	
	public void delete(Project project);
	
}
