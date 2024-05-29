package com.sds.cmsapp.model.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.EmpDetail;

@Service
public class EmpDetailServiceImpl implements EmpDetailService {

	@Autowired
	private EmpDetailDAO empDetailDAO;

	@Override
	public void insert(EmpDetail empDetail) {
		// TODO Auto-generated method stub
		empDetailDAO.insert(empDetail);
	}

	@Override
	public EmpDetail selectByEmpIdx(int emp_idx) {
		// TODO Auto-generated method stub
		return empDetailDAO.selectByEmpIdx(emp_idx);
	}

	@Override
	public void update(EmpDetail empDetail) {
		// TODO Auto-generated method stub
		empDetailDAO.update(empDetail);
	}
}
