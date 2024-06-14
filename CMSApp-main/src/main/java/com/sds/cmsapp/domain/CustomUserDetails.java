package com.sds.cmsapp.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sds.cmsapp.model.emp.EmpDetailService;

import lombok.Data;

@Data
//회원의 상세정보를 가진 객체. 단 스프링이 지원하는 기술을 구현
public class CustomUserDetails implements UserDetails{
	
	//우리가 이미 정의해놓은 Member DTO 정보를 참고하여, 아래의 메서드들에서 정보들을 처리 
	private Emp emp;
	
	///////////////////////////////////////////////////////////////////////
	@Autowired
	private EmpDetailService empDetailService;
	///////////////////////////////////////////////////////////////////////
	
	public CustomUserDetails(Emp emp) {
		this.emp = emp;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authList = new ArrayList();
		authList.add(new GrantedAuthority() {
			public String getAuthority() {
				return emp.getRole().getRoleName(); 
			}
		});
		return authList;
	}

	@Override
	public String getPassword() {
		
		int empIdx = emp.getEmpIdx();
		EmpDetail empDetail = empDetailService.selectByEmpIdx(empIdx);
		
		return empDetail.getEmpPw();
	}
	
	public int getEmpIdx() {
		return emp.getEmpIdx();
	}

	@Override
	public String getUsername() {
		return emp.getEmpName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}