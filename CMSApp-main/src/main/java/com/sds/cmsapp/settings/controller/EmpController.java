package com.sds.cmsapp.settings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sds.cmsapp.common.FileManager;
import com.sds.cmsapp.domain.Dept;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.EmpDetail;
import com.sds.cmsapp.domain.Role;
import com.sds.cmsapp.exception.UploadException;
import com.sds.cmsapp.model.emp.EmpDetailService;
import com.sds.cmsapp.model.emp.EmpService;

@Controller
public class EmpController {

	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private EmpService empService;
	
	@Autowired
	private EmpDetailService empDetailService;
	
	@PostMapping("/emp/regist")
	public String regist(Emp emp, EmpDetail empDetail, @RequestParam("dept_idx") int deptIdx, @RequestParam("role_code") int roleCode) {
		try {
			 MultipartFile file = empDetail.getFile();
	            if (file != null && !file.isEmpty()) {            // 파일이 존재한다면 
	                String fileUrl = fileManager.save(empDetail); // 서버에 저장
	                empDetail.setEmp_profile_url(fileUrl);
	            } 
	        
            Dept dept = new Dept();
            dept.setDept_idx(deptIdx);
            emp.setDept(dept);
            
            Role role = new Role();
            role.setRole_code(roleCode);
            emp.setRole(role);    
	            
            // EmpDetail 객체를 DB에 저장하는 로직	        
	        empService.insert(emp);
	        empDetail.setEmp(emp);
	        empDetailService.insert(empDetail);

            System.out.println("Employee added successfully");
	        return "redirect:/settings/user";
        } catch (UploadException e) {
        	e.printStackTrace();
            throw new UploadException("사원 등록 실패", e);
        }
	}
	
	@PostMapping("/emp/update")
    public String update(@RequestParam("emp_idx") List<Integer> empIdxList,
                         @RequestParam("role_code") List<Integer> roleCodeList,
                         @RequestParam("dept_idx") List<Integer> deptIdxList) {
        for (int i = 0; i < empIdxList.size(); i++) {
            Emp emp = empService.selectByEmpIdx(empIdxList.get(i));
            if (emp != null) {
                Dept dept = new Dept();
                dept.setDept_idx(deptIdxList.get(i));
                emp.setDept(dept);

                Role role = new Role();
                role.setRole_code(roleCodeList.get(i));
                emp.setRole(role);

                empService.update(emp);
            }
        }
        return "redirect:/settings/user";
    }
	
	@PostMapping("/settings/mypage/update")
	public String update(Emp emp, EmpDetail empDetail, @RequestParam("dept_idx") int deptIdx, @RequestParam("role_code") int roleCode) {
		try {
			 MultipartFile file = empDetail.getFile();
	            if (file != null && !file.isEmpty()) {            // 파일이 존재한다면 
	                String fileUrl = fileManager.save(empDetail); // 서버에 저장
	                empDetail.setEmp_profile_url(fileUrl);
	            } 
	        
           Dept dept = new Dept();
           dept.setDept_idx(deptIdx);
           emp.setDept(dept);
           
           Role role = new Role();
           role.setRole_code(roleCode);
           emp.setRole(role);    
	       
           System.out.println("emp.getEmp_name(): " + emp.getEmp_name());
           System.out.println("empDetail.getEmp_id(): "+ empDetail.getEmp_id());
           System.out.println("empDetail.getEmp_pw(): " + empDetail.getEmp_pw());
           
           // 이름, id, pw등이 변경되지 않았다면 원래 값으로 설정
           if(emp.getEmp_name() == null) {
        	   emp.setEmp_name(empService.selectByEmpIdx(emp.getEmp_idx()).getEmp_name());
		   } if(empDetail.getEmp_id() == null) {
        	   empDetail.setEmp_id(empDetailService.selectByEmpIdx(emp.getEmp_idx()).getEmp_id());
		   } if(empDetail.getEmp_pw() == null) {
			   empDetail.setEmp_pw(empDetailService.selectByEmpIdx(emp.getEmp_idx()).getEmp_pw());
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
