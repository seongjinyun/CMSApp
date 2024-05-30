package com.sds.cmsapp.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.model.dept.DeptService;

@Controller
public class DeptController {	
	
	@Autowired
	private DeptService deptService;
	
	@PostMapping("/settings/dept/insert")
	public String insertDept(Dept dept) {	

		// 부서 추가
		deptService.insert(dept);
		
		// 사원들의 부서를 추가한 부서로 설정
		//empService.update(emp);
		
		return "redirect:/settings/access";
	}
}
