package com.sds.cmsapp.settings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.emp.EmpService;

@Controller
public class DeptController {	
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private EmpService empService;
	
	@PostMapping("/settings/dept/insert")
	public String insertDept(Dept dept, @RequestParam("empIdx") List<Integer> empIds) {	

		// 부서 추가
		int deptIdx = deptService.insert(dept);
		
	    Dept deptWithIdx = deptService.selectByDeptIdx(deptIdx); // 추가된 부서 객체 조회
	    for(int empIdx : empIds) {
	        Emp emp = new Emp();
	        emp.setEmpIdx(empIdx);
	        emp.setDept(deptWithIdx);
	        // 사원들의 부서를 추가한 부서로 설정
	        empService.updateDept(emp);
	    }
	    
		return "redirect:/settings/dept_project";
	}
}
