package com.sds.cmsdocument.model.emp;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.domain.EmpDetail;

@Mapper
public interface EmpDetailDAO {

	public void insert(EmpDetail empDetail);
	public EmpDetail selectByEmpIdx(int empIdx);
	public void update(EmpDetail empDetail);
	
	public Emp selectByLoginData(EmpDetail empDetail);
	public EmpDetail selectByUid(String empId);
	public int selectEmpIdxByUid(String empId);
}
