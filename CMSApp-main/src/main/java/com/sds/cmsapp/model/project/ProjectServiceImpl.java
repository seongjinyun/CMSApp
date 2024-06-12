package com.sds.cmsapp.model.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Project;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDAO projectDAO;

	@Override
	public int insert(final Project project) {
		// TODO Auto-generated method stub
		projectDAO.insert(project);
		return project.getProjectIdx();
	}

	@Override
	public Project selectByProjectIdx(final int projectIdx) {
		// TODO Auto-generated method stub
		return projectDAO.selectByProjectIdx(projectIdx);
	}

	@Override
	public List selectAll() {
		return projectDAO.selectAll();
	}

	@Override
	public void delete(Project project) {
		// TODO Auto-generated method stub
		projectDAO.delete(project);
	}
}
