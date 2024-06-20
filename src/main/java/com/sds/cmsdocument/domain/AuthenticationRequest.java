package com.sds.cmsdocument.domain;

import lombok.Data;

@Data
public class AuthenticationRequest {
	private String empId;
    private String empPw;
}
