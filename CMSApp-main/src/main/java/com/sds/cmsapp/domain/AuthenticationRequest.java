package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class AuthenticationRequest {
	private String empId;
    private String empPw;
}
