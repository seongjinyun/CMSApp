package com.sds.cmsdocument.model.emp;

import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.domain.EmpDetail;

public interface EmpDetailService {

	public void insert(EmpDetail empDetail);
	public EmpDetail selectByEmpIdx(int empIdx);
	public void update(EmpDetail empDetail);
	
	public Emp selectByLoginData(EmpDetail empDetail);
	public EmpDetail selectByUid(String empd);
	public int selectEmpIdxByUid(String empId);
}
