package com.sds.cmsapp.model.emp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Emp;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	private EmpDAO empDAO;

	@Override
	public void insert(Emp emp) {
		// TODO Auto-generated method stub
		empDAO.insert(emp);
	}

	@Override
	public List selectAllEmpName() {
		// TODO Auto-generated method stub
		return empDAO.selectAllEmpName();
	}
	
	// EmpMap 반환
	@Override
	public List selectAll() {
		// TODO Auto-generated method stub
		return empDAO.selectAll();
	}
	
	// pager에 이용할 전체 사원 수 가져오기
	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return empDAO.getTotalCount();
	}

	@Override
	public List selectEmpPage(Map map) {
		// TODO Auto-generated method stub
		return empDAO.selectEmpPage(map);
	}
	
	@Override
	public Emp selectByEmpIdx(int empIdx) {
		return empDAO.selectByEmpIdx(empIdx);
	}
	
	@Transactional
	public void update(Emp emp) {
		// TODO Auto-generated method stub
		
		// Dept dept = 
		// emp.setDept(dept);
		// Role role = 
		// emp.setRole(role);
		
		// int result = empDAO.update(emp);
		
		// if(result < 1)
		//	throw new EmpException("사원 정보 등록 실패");
		
		// EmpDetail empDetail = emp.getEmpDetail();
		// empDetail.setEmp(emp);
		// empDetail.set
		
		// result = empDetailDAO.update(empDetail);
		// if(result < 1)
		//	throw new EmpException("사원 상세 정보 등록 실패");
		
		empDAO.update(emp);
	}

	@Override
	public void updateDept(Emp emp) {
		// TODO Auto-generated method stub
		empDAO.updateDept(emp);
	}
}
