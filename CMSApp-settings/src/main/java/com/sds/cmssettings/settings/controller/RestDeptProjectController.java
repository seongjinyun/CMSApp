package com.sds.cmssettings.settings.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmssettings.domain.Project;
import com.sds.cmssettings.model.dept.DeptService;
import com.sds.cmssettings.model.project.ProjectService;
import com.sds.cmssettings.model.relationship.DeptProjectService;

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
    public Map<String, Object> getProjectsByDept(@RequestParam("deptIdx") int deptIdx) {
        List<Project> projects = deptProjectService.selectByDeptIdx(deptIdx);
        Map<String, Object> response = new HashMap<>();
        response.put("projects", projects);
        return response;
    }
    
    // 해당 부서에 속한 프로젝트 가져오기
    @GetMapping("/settings/deptProject/getProjectsByDeptIdx/{deptIdx}")
    public ResponseEntity<List<Project>> getProjectsByDeptIdx(@PathVariable("deptIdx") int deptIdx) {
        try {
            List<Project> projects = deptProjectService.selectByDeptIdx(deptIdx);
            return ResponseEntity.ok().body(projects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }   
    
    // 해당 부서에 속하지 않은 프로젝트 가져오기
    @GetMapping("/settings/deptProject/getOtherProjectsByDeptIdx/{deptIdx}")
    public ResponseEntity<List<Project>> getOtherProjectsByDeptIdx(@PathVariable("deptIdx") int deptIdx) {
        try {
            List<Project> projects = deptProjectService.selectOtherByDeptIdx(deptIdx);
            return ResponseEntity.ok().body(projects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }  
    
}