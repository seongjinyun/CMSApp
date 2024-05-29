package com.sds.cmsapp.model.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Project;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDAO projectDAO;

	@Override
	public int insert(Project project) {
		// TODO Auto-generated method stub
		projectDAO.insert(project);
		return project.getProject_idx();
	}

	@Override
	public Project selectByProjectIdx(int project_idx) {
		// TODO Auto-generated method stub
		return projectDAO.selectByProjectIdx(project_idx);
	}
}
