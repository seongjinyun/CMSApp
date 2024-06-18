package com.sds.cmsapp.model.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.common.FileManager;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.EmpDetail;

@Service
public class EmpDetailServiceImpl implements EmpDetailService {

	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private EmpDetailDAO empDetailDAO;

	@Override
	public void insert(EmpDetail empDetail) {
		// TODO Auto-generated method stub
		// upload
		fileManager.save(empDetail);
		empDetailDAO.insert(empDetail);
	}

	@Override
	public EmpDetail selectByEmpIdx(int empIdx) {
		// TODO Auto-generated method stub
		return empDetailDAO.selectByEmpIdx(empIdx);
	}

	@Override
	public void update(EmpDetail empDetail) {
		// TODO Auto-generated method stub
		fileManager.save(empDetail);
		empDetailDAO.update(empDetail);
	}

	@Override
	public Emp selectByLoginData(EmpDetail empDetail) {
		// TODO Auto-generated method stub
		return empDetailDAO.selectByLoginData(empDetail);
	}

	@Override
	public EmpDetail selectByUid(String empId) {
		// TODO Auto-generated method stub
		return empDetailDAO.selectByUid(empId);
	}

	@Override
	public int selectEmpIdxByUid(String empId) {
		// TODO Auto-generated method stub
		return empDetailDAO.selectEmpIdxByUid(empId);
	}


}
