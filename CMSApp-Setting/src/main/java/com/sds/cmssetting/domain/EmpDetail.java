package com.sds.cmssetting.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EmpDetail {
	private int empDetailIdx;
	private Emp emp;
	private String empId;
	private String empPw;
	private String empProfileUrl;
	private MultipartFile file;
}
