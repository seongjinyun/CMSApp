package com.sds.cmsapp.settings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Project;
import com.sds.cmsapp.model.relationship.DeptProjectService;

@RestController
public class RestProjectController {	
	
	@Autowired
	private DeptProjectService deptProjectService;
	
	
}
