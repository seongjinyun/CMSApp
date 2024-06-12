package com.sds.cmsapp.model.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.domain.RequestDocumentDTO;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDAO projectDAO;

	@Override
	public int insert(Project project) {
		// TODO Auto-generated method stub
		projectDAO.insert(project);
		return project.getProjectIdx();
	}

	@Override
	public Project selectByProjectIdx(int project_idx) {
		// TODO Auto-generated method stub
		return projectDAO.selectByProjectIdx(project_idx);
	}

	@Override
	public List<Project> selectAll() {
		return projectDAO.selectAll();
	}

//	@Override
//	public List<Project> selectAllByFilteredProjectIdx(RequestDocumentDTO requestDocumentDTO) {
//		return projectDAO.selectAllByFilteredProjectIdx(requestDocumentDTO);
//	}
}
