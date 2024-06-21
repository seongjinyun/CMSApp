package com.sds.cmsapp.settings.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sds.cmsapp.common.FileManager;
import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.EmpDetail;
import com.sds.cmsapp.domain.Role;
import com.sds.cmsapp.exception.UploadException;
import com.sds.cmsapp.jwt.JwtValidService;
import com.sds.cmsapp.model.dept.DeptService;
import com.sds.cmsapp.model.emp.EmpDetailService;
import com.sds.cmsapp.model.emp.EmpService;
import com.sds.cmsapp.model.role.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestMypageController {
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private EmpDetailService empDetailService;
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private RoleService roleService;
	
    @Autowired
    private JwtValidService jwtValidService;
    
	@Autowired
	private FileManager fileManager;
		
	@GetMapping("/rest/mypage/getInfo")
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
	
	@PostMapping("/rest/mypage/update")
	public String update(Emp emp, EmpDetail empDetail, @RequestParam("deptIdx") int deptIdx, @RequestParam("roleCode") int roleCode) {
		try {
			 MultipartFile file = empDetail.getFile();
	            if (file != null && !file.isEmpty()) {            // 파일이 존재한다면 
	                String fileUrl = fileManager.save(empDetail); // 서버에 저장
	                empDetail.setEmpProfileUrl(fileUrl);
	            } 
	        
           Dept dept = new Dept();
           dept.setDeptIdx(deptIdx);
           emp.setDept(dept);
           
           Role role = new Role();
           role.setRoleCode(roleCode);
           emp.setRole(role);    
	       
           System.out.println("emp.getEmpName(): " + emp.getEmpName());
           System.out.println("empDetail.getEmpId(): "+ empDetail.getEmpId());
           System.out.println("empDetail.getEmpPw(): " + empDetail.getEmpPw());
           
           // 이름, id, pw등이 변경되지 않았다면 원래 값으로 설정
           if(emp.getEmpName() == null) {
        	   emp.setEmpName(empService.selectByEmpIdx(emp.getEmpIdx()).getEmpName());
		   } if(empDetail.getEmpId() == null) {
        	   empDetail.setEmpId(empDetailService.selectByEmpIdx(emp.getEmpIdx()).getEmpId());
		   } if(empDetail.getEmpPw() == null) {
			   empDetail.setEmpPw(empDetailService.selectByEmpIdx(emp.getEmpIdx()).getEmpPw());
		   }
		   
           // EmpDetail 객체를 DB에 저장하는 로직	        
	        empService.update(emp);
	        empDetail.setEmp(emp);
	        empDetailService.update(empDetail);

           System.out.println("Employee updated successfully");
           return "redirect:/settings/mypage";
       } catch (UploadException e) {
       	e.printStackTrace();
           throw new UploadException("사원 수정 실패", e);
       }
		
	}
	
}
