package com.sds.cmssetting.settings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sds.cmssetting.domain.DeptProject;
import com.sds.cmssetting.model.dept.DeptService;
import com.sds.cmssetting.model.project.ProjectService;
import com.sds.cmssetting.model.relationship.DeptProjectService;

@Controller
public class DeptProjectController {

    @Autowired
    private DeptProjectService deptProjectService;
    
    @Autowired
    private DeptService deptService;
    
    @Autowired
    private ProjectService projectService;
    
    // 해당 부서에 프로젝트 추가
    @PostMapping("/setting/deptProject/insert")
    public String addProjectsToDept(@RequestParam("deptIdx") int deptIdx, @RequestParam("projectIds") List<Integer> projectIds, RedirectAttributes redirectAttributes) {
        try {
            for(int projectIdx : projectIds) {
                DeptProject deptProject = new DeptProject();
                deptProject.setDept(deptService.selectByDeptIdx(deptIdx));
                deptProject.setProject(projectService.selectByProjectIdx(projectIdx));
                deptProjectService.insert(deptProject);            	
            }
            redirectAttributes.addFlashAttribute("message", "프로젝트가 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "프로젝트 추가에 실패했습니다.");
        }
        return "redirect:/setting/dept_project";
    }
    
    // 해당 부서에서 프로젝트 제거
    @PostMapping("/setting/deptProject/delete")
    public String deleteProjectsFromDept(@RequestParam("deptIdx") int deptIdx, @RequestParam("projectIds") List<Integer> projectIds, RedirectAttributes redirectAttributes) {
        try {
            for(int projectIdx : projectIds) {
                DeptProject deptProject = new DeptProject();
                deptProject.setDept(deptService.selectByDeptIdx(deptIdx));
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
        return "redirect:/setting/dept_project";
    }
}
