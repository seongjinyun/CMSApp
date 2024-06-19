package com.sds.cmssettings.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmssettings.model.relationship.DeptProjectService;

@RestController
public class RestProjectController {	
	
	@Autowired
	private DeptProjectService deptProjectService;
	
	
}
