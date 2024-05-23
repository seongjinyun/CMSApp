package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class EmpDetail {
	private int emp_detail_idx;
	private Emp emp;
	private String emp_id;
	private String emp_pw;
	private String emp_profile_url;
}
