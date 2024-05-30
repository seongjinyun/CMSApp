package com.sds.cmsapp.settings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.emp.EmpService;
import com.sds.cmsapp.model.role.RoleService;

@Controller
public class SettingsController {	
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/settings/general")
	public String getGeneral() {
		return "settings/general";
	}
	
	@GetMapping("/settings/log")
	public String getLog() {
		return "settings/log";
	}
	
	@GetMapping("/settings/dept_project")
	public String getAccess(Model model) {
		// 사원 이름과 index 가져오기
		List<Emp> empList = empService.selectAllEmpName();
		model.addAttribute("empList", empList);
		
		// 부서 이름과 index 가져오기
		List deptList = deptService.selectAllDeptName();
		model.addAttribute("deptList", deptList);
				
		return "settings/dept_project";
	}
	
	@GetMapping("/settings/mypage")
	public String getMypageInfo() {
		return "settings/mypage";
	}
	
	@GetMapping("/settings/user")
	public String getUserInfo(Model model) {
		// 사원 이름과 index 가져오기
		List<Emp> empList = empService.selectAllEmpName();
		model.addAttribute("empList", empList);
		
		// 부서 이름과 index 가져오기
		List deptList = deptService.selectAllDeptName();
		model.addAttribute("deptList", deptList);
		
		// 역할 이름과 index 가져오기
		List roleList = roleService.selectAll();
		model.addAttribute("roleList", roleList);
		
		return "settings/user";
	}
	
	@GetMapping("/settings/role")
	public String getRole() {
		return "settings/role";
	}
}
