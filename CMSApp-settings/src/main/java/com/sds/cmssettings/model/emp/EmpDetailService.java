package com.sds.cmssettings.model.emp;

import com.sds.cmssettings.domain.Emp;
import com.sds.cmssettings.domain.EmpDetail;

public interface EmpDetailService {

	public void insert(EmpDetail empDetail);
	public EmpDetail selectByEmpIdx(int empIdx);
	public void update(EmpDetail empDetail);
	
	public Emp selectByLoginData(EmpDetail empDetail);
	public EmpDetail selectByUid(String empd);
	public int selectEmpIdxByUid(String empId);
}
