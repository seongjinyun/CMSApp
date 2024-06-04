package com.sds.cmsapp.settings.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.EmpDetail;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.emp.EmpDetailService;
import com.sds.cmsapp.model.emp.EmpService;
import com.sds.cmsapp.model.role.RoleService;

@Controller
public class SettingsController {	
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private EmpDetailService empDetailService;
	
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
	public String getMypageInfo(Model model) {
		
		// 부서 이름과 index 가져오기
		List deptList = deptService.selectAll();
		model.addAttribute("deptList", deptList);
		
		// 역할 이름과 index 가져오기
		List roleList = roleService.selectAll();
		model.addAttribute("roleList", roleList);
		
		// -----------------------------
		// 테스트를 위해 사원 '아이린'전달
		Emp emp = empService.selectByEmpIdx(51);
		EmpDetail empDetail = empDetailService.selectByEmpIdx(emp.getEmp_idx());
		model.addAttribute("emp", emp);
		model.addAttribute("empDetail", empDetail);
		System.out.println(empDetail.getEmp_profile_url());
		String profileImgUrl = "/profileImg/" + empDetail.getEmp_profile_url();
	    model.addAttribute("profile_img_url", profileImgUrl);
		// -----------------------------
		
		return "settings/mypage";
	}
	
	@GetMapping("/settings/user")
	public String getUserInfo(Model model) {
		// 사원 이름과 index 가져오기
		List empList = empService.selectAll();
		model.addAttribute("empList", empList);
		
		// 부서 이름과 index 가져오기
		List deptList = deptService.selectAll();
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
