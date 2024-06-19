package com.sds.cmsapp.settings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.DeptProject;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.folder.FolderService;
import com.sds.cmsapp.model.project.ProjectService;
import com.sds.cmsapp.model.relationship.DeptProjectService;

@Controller
public class ProjectController {	
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private DeptProjectService deptProjectService;
	
	// 프로젝트 생성
	@PostMapping("/settings/project/insert")
	public String insertDept(Project project, @RequestParam("deptIdx") List<Integer> deptIds,
							 RedirectAttributes redirectAttributes ) {	
		try {
			// 프로젝트 추가
			int projectIdx = projectService.insert(project);
			
			// 빈 폴더 초기화
			Folder folder = new Folder();
			folder.setProject(project);
			folder.setFolderName(project.getProjectName());
			folderService.createFolder(folder);
			
			// 부서들을 추가한 프로젝트의 담당으로 설정
			Project projectWithIdx = projectService.selectByProjectIdx(projectIdx); // 방금 입력한 프로젝트 인덱스를 지닌 프로젝트 객체
			
			for(int deptId : deptIds) {
				Dept dept = new Dept();
				dept.setDeptIdx(deptId); // dept 객체
				
				// dept_project에 두개 다 넣기
				DeptProject deptProject = new DeptProject();
				deptProject.setProject(projectWithIdx);
				deptProject.setDept(dept);
				deptProjectService.insert(deptProject);
			}
		    redirectAttributes.addFlashAttribute("message", "프로젝트가 성공적으로 추가되었습니다.");
		} catch (Exception e) {
		    e.printStackTrace();
		    redirectAttributes.addFlashAttribute("error", "프로젝트 추가에 실패했습니다.");
		}
	    
		return "redirect:/settings/dept_project";
	}
	
	// 프로젝트 삭제
	@PostMapping("/settings/project/delete")
	public String deleteProject(@RequestParam("projectIdx") String projectIdx, RedirectAttributes redirectAttributes) {
		
		Project project = projectService.selectByProjectIdx(Integer.parseInt(projectIdx));
 
		try {
			// 프로젝트 내부 폴더 삭제
			int empIdx = 1; // 지금 접속한 사원 인덱스. 테스트를 위해 1로 설정
			Folder folder = folderService.selectProjectRootFolder(project.getProjectIdx());
			folderService.deleteFolder(folder.getFolderIdx(), empIdx);

			// 프로젝트 삭제
			projectService.delete(project);
						
		    redirectAttributes.addFlashAttribute("message", "프로젝트가 성공적으로 삭제되었습니다.");
		} catch (Exception e) {
		    e.printStackTrace();
		    redirectAttributes.addFlashAttribute("error", "프로젝트 삭제에 실패했습니다.");
		} 
		
		return "redirect:/settings/dept_project";
	}
}
