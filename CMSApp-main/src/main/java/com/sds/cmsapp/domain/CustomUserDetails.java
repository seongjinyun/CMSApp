package com.sds.cmsapp.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
//회원의 상세정보를 가진 객체. 단 스프링이 지원하는 기술을 구현
public class CustomUserDetails implements UserDetails{
	 
	private Emp emp;
	
	private EmpDetail empDetail;
	
	private String role;
	
	public CustomUserDetails(Emp emp, EmpDetail empDetail, String role) {
		this.emp = emp;
		this.empDetail = empDetail;
		this.role = role;
	}
	
    public Emp getUser() {
        return emp;
    }
    
	public int getEmpIdx() {
		return emp.getEmpIdx();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
	}

	@Override
	public String getPassword() {		
		return empDetail.getEmpPw();
	}

	@Override
	public String getUsername() {
		return empDetail.getEmpId();
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