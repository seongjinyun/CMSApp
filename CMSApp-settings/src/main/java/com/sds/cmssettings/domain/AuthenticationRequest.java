package com.sds.cmssettings.domain;

import lombok.Data;

@Data
public class AuthenticationRequest {
	private String empId;
    private String empPw;
}
