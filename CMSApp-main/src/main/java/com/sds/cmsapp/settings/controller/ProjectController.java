package com.sds.cmsapp.settings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.DeptProject;
import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.project.ProjectService;
import com.sds.cmsapp.model.relationship.DeptProjectService;

@Controller
public class ProjectController {	
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private DeptProjectService deptProjectService;
	
	@PostMapping("/settings/project/insert")
	public String insertDept(Project project, @RequestParam("dept_idx") List<Integer> deptIds) {	

		// 프로젝트 추가
		int project_idx = projectService.insert(project);
		
		// 부서들을 추가한 프로젝트의 담당으로 설정
		Project projectWithIdx = projectService.selectByProjectIdx(project_idx); // 방금 입력한 프로젝트 인덱스를 지닌 프로젝트 객체
		
		for(int deptId : deptIds) {
			Dept dept = new Dept();
			dept.setDept_idx(deptId); // dept 객체
			
			// dept_project에 두개 다 넣기
			DeptProject deptProject = new DeptProject();
			deptProject.setProject(projectWithIdx);
			deptProject.setDept(dept);
			deptProjectService.insert(deptProject);
		}
	    
		return "redirect:/settings/dept_project";
	}
	
}
