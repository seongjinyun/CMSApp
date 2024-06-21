package com.sds.cmssetting.settings.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.cmssetting.common.Pager;
import com.sds.cmssetting.domain.Authority;
import com.sds.cmssetting.domain.Emp;
import com.sds.cmssetting.domain.EmpDetail;
import com.sds.cmssetting.domain.Role;
import com.sds.cmssetting.jwt.JwtValidService;
import com.sds.cmssetting.model.authority.AuthorityService;
import com.sds.cmssetting.model.dept.DeptService;
import com.sds.cmssetting.model.emp.EmpDetailService;
import com.sds.cmssetting.model.emp.EmpService;
import com.sds.cmssetting.model.relationship.DeptProjectService;
import com.sds.cmssetting.model.relationship.RoleAuthorityService;
import com.sds.cmssetting.model.role.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
    @Autowired
    private JwtValidService jwtValidService;
    
	@GetMapping("/loginForm")
	public String getLoginForm() {
		System.out.println("로그인 폼 요청");
		return "/login/loginForm";
	}
	
	@GetMapping("/setting/general")
	public String getGeneral() {
		return "setting/general";
	}
	
	@GetMapping("/setting/log")
	public String getLog() {
		return "setting/log";
	}
	
	@GetMapping("/setting/dept_project")
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
		
		return "setting/dept_project";
	}
	
	@GetMapping("/setting/mypage/info")
	@ResponseBody
	public Map<String, Object> getMypageInfo(@RequestHeader(name="Authorization") String header) {
	    String token = header.replace("Bearer ", "");
	    log.debug("Received token: " + token);

	    // 부서 이름과 index 가져오기
	    List deptList = deptService.selectAll();
	    
	    // 역할 이름과 index 가져오기
	    List roleList = roleService.selectAll();
	    
	    // JWT 토큰에서 Emp 객체를 추출
	    Emp emp = jwtValidService.getEmpFromJwt(token);
	    EmpDetail empDetail = empDetailService.selectByEmpIdx(emp.getEmpIdx());

	    String profileImgUrl = "/profileImg/" + empDetail.getEmpProfileUrl();

	    Map<String, Object> response = new HashMap<>();
	    response.put("empIdx", emp.getEmpIdx());
	    response.put("empName", emp.getEmpName());
	    response.put("empId", empDetail.getEmpId());
	    response.put("empPw", empDetail.getEmpPw());
	    response.put("deptList", deptList);
	    response.put("roleList", roleList);
	    response.put("profileImgUrl", profileImgUrl);
	    response.put("emp", emp);

	    return response;
	}

	@GetMapping("/setting/mypage")
	public String getMypage() {
		return "setting/mypage";
	}

	@GetMapping("/setting/user")
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
		
		return "setting/user";
	}

	// 0610
	@GetMapping("/setting/role")
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
		
		return "setting/role";
	}
}
