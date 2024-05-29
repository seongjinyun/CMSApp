package com.sds.cmsapp.model.emp;

import com.sds.cmsapp.domain.EmpDetail;

public interface EmpDetailService {

	public void insert(EmpDetail empDetail);
	public EmpDetail selectByEmpIdx(int emp_idx);
	public void update(EmpDetail empDetail);
	
}
