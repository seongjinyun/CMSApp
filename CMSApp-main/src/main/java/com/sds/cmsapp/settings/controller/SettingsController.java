package com.sds.cmsapp.settings.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsapp.common.Pager;
import com.sds.cmsapp.domain.Authority;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.EmpDetail;
import com.sds.cmsapp.domain.Role;
import com.sds.cmsapp.model.authority.AuthorityService;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.emp.EmpDetailService;
import com.sds.cmsapp.model.emp.EmpService;
import com.sds.cmsapp.model.relationship.DeptProjectService;
import com.sds.cmsapp.model.relationship.RoleAuthorityService;
import com.sds.cmsapp.model.role.RoleService;

@Controller
public class SettingsController {	
	
	@Autowired
	private Pager pager;
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private EmpDetailService empDetailService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private DeptProjectService deptProjectService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private RoleAuthorityService roleAuthorityService;
	
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
		
		// 빈 부서 가져오기
		List emptyDeptList = deptProjectService.selectEmptyDept();
		model.addAttribute("emptyDeptList", emptyDeptList);
		
		// 어느 부서도 관리하지 않는 프로젝트 가져오기
		List emptyProjectList = deptProjectService.selectEmptyProject();
		model.addAttribute("emptyProjectList", emptyProjectList);
		
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
		
		/*
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
		*/
		
		// -----------------------------
		// 테스트를 위해 사원 전달
		Emp emp = empService.selectByEmpIdx(58);
		EmpDetail empDetail = empDetailService.selectByEmpIdx(emp.getEmpIdx());
		model.addAttribute("emp", emp);
		model.addAttribute("empDetail", empDetail);
		System.out.println("DB에서 전달받은 프로필 이미지 url: "+empDetail.getEmpProfileUrl());
		String profileImgUrl = "/profileImg/" + empDetail.getEmpProfileUrl();
	    model.addAttribute("profile_img_url", profileImgUrl);
	    System.out.println("File exists: " + new File("src/main/resources/static/profileImg/" + empDetail.getEmpProfileUrl()).exists());
	    // System.out.println("html로 전달되는 경로: "+profileImgUrl);
		// -----------------------------
		
		return "settings/mypage";
	}
	
	@GetMapping("/settings/user")
	public String getUserInfo(@RequestParam(value="currentPage", defaultValue="1") int currentPage, Model model) {
		
		int totalRecord = empService.getTotalCount();
        pager.init(totalRecord, currentPage);
		
		// 사원 이름과 index 가져오기
		// List empList = empService.selectAll();
        
        // pager가 적용된 사원 목록 가져오기
        HashMap map = new HashMap();
		map.put("startIndex", pager.getStartIndex());
		map.put("rowCount", pager.getPageSize());
		List empList = empService.selectEmpPage(map);
        // List empList = empService.selectPage(pager.getStartIndex(), pager.getPageSize());
		model.addAttribute("empList", empList);
		model.addAttribute("pager", pager);
		
		// 부서 이름과 index 가져오기
		List deptList = deptService.selectAll();
		model.addAttribute("deptList", deptList);
		
		// 역할 이름과 index 가져오기
		List roleList = roleService.selectAll();
		model.addAttribute("roleList", roleList);
		
		return "settings/user";
	}

	// 0610
	@GetMapping("/settings/role")
	public String getRole(Model model) {
		
		// 역할 목록 가져오기
		List roleList = roleService.selectAll();
		model.addAttribute("roleList", roleList);
		
		// 권한 목록 가져오기
		List authList = authorityService.selectAll();
		model.addAttribute("authList", authList);
        
		// Role과 해당하는 권한 목록 가져오기
		Map<Role, List<Authority>> roleAuthMap = new HashMap<>();
	    for(int i=0; i<roleList.size(); i++) {
	    	Role role = (Role) roleList.get(i);
	    	List<Authority> RoleAuthList = roleAuthorityService.selectAuthoritiesByRoleCode(role.getRoleCode());
	    	System.out.println(("RoleAuthList: "+RoleAuthList));
	    	roleAuthMap.put(role, RoleAuthList);
	    }
	    model.addAttribute("roleAuthMap", roleAuthMap);
		
		return "settings/role";
	}
}
