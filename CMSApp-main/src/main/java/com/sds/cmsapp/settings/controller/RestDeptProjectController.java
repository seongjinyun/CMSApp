package com.sds.cmsapp.settings.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.DeptProject;
import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.project.ProjectService;
import com.sds.cmsapp.model.relationship.DeptProjectService;

@RestController
public class RestDeptProjectController {

    @Autowired
    private DeptProjectService deptProjectService;
    
    @Autowired
    private DeptService deptService;
    
    @Autowired
    private ProjectService projectService;
    
    // 모든 부서에 속한 프로젝트 가져오기
    @GetMapping("/settings/project/selectByDeptIdx")
    public Map<String, Object> getProjectsByDept(@RequestParam("dept_idx") int dept_idx) {
        List<Project> projects = deptProjectService.selectByDeptIdx(dept_idx);
        Map<String, Object> response = new HashMap<>();
        response.put("projects", projects);
        return response;
    }
    
    // 해당 부서에 속한 프로젝트 가져오기
    @GetMapping("/settings/deptProject/getProjectsByDeptIdx/{dept_idx}")
    public ResponseEntity<List<Project>> getProjectsByDeptIdx(@PathVariable("dept_idx") int dept_idx) {
        try {
            List<Project> projects = deptProjectService.selectByDeptIdx(dept_idx);
            return ResponseEntity.ok().body(projects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }   
    
    // 해당 부서에 속하지 않은 프로젝트 가져오기
    @GetMapping("/settings/deptProject/getOtherProjectsByDeptIdx/{dept_idx}")
    public ResponseEntity<List<Project>> getOtherProjectsByDeptIdx(@PathVariable("dept_idx") int dept_idx) {
        try {
            List<Project> projects = deptProjectService.selectOtherByDeptIdx(dept_idx);
            return ResponseEntity.ok().body(projects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }  
    
}