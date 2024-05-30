package com.sds.cmsapp.settings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sds.cmsapp.domain.DeptProject;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.project.ProjectService;
import com.sds.cmsapp.model.relationship.DeptProjectService;

@Controller
public class DeptProjectController {

    @Autowired
    private DeptProjectService deptProjectService;
    
    @Autowired
    private DeptService deptService;
    
    @Autowired
    private ProjectService projectService;
    
    // 해당 부서에 프로젝트 추가
    @PostMapping("/settings/deptProject/insert")
    public String addProjectsToDept(@RequestParam("dept_idx") int dept_idx, @RequestParam("projectIds") List<Integer> projectIds, RedirectAttributes redirectAttributes) {
        try {
            for(int projectIdx : projectIds) {
                DeptProject deptProject = new DeptProject();
                deptProject.setDept(deptService.selectByDeptIdx(dept_idx));
                deptProject.setProject(projectService.selectByProjectIdx(projectIdx));
                deptProjectService.insert(deptProject);            	
            }
            redirectAttributes.addFlashAttribute("message", "프로젝트가 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "프로젝트 추가에 실패했습니다.");
        }
        return "redirect:/settings/dept_project";
    }
    
    // 해당 부서에서 프로젝트 제거
    @PostMapping("/settings/deptProject/delete")
    public String deleteProjectsFromDept(@RequestParam("dept_idx") int dept_idx, @RequestParam("projectIds") List<Integer> projectIds, RedirectAttributes redirectAttributes) {
        try {
            for(int projectIdx : projectIds) {
                DeptProject deptProject = new DeptProject();
                deptProject.setDept(deptService.selectByDeptIdx(dept_idx));
                deptProject.setProject(projectService.selectByProjectIdx(projectIdx));
                deptProjectService.delete(deptProject);            	
            }
            redirectAttributes.addFlashAttribute("message", "프로젝트가 성공적으로 제거되었습니다.");
            System.out.println("프로젝트가 성공적으로 제거되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "프로젝트 제거에 실패했습니다.");
            System.out.println("프로젝트 제거에 실패했습니다.");
        }
        return "redirect:/settings/dept_project";
    }
}
