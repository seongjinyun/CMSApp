package com.sds.cmssetting.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmssetting.model.relationship.DeptProjectService;

@RestController
public class RestProjectController {	
	
	@Autowired
	private DeptProjectService deptProjectService;
	
	
}
