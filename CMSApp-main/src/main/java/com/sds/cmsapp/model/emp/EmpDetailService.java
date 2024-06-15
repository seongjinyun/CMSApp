package com.sds.cmsapp.model.emp;

import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.EmpDetail;

public interface EmpDetailService {

	public void insert(EmpDetail empDetail);
	public EmpDetail selectByEmpIdx(int empIdx);
	public void update(EmpDetail empDetail);
	
	public Emp selectByLoginData(EmpDetail empDetail);
}
