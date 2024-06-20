package com.sds.cmsdocument.model.mastercode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsdocument.domain.MasterCode;

@Service
public class MasterCodeServiceImpl implements MasterCodeService {

	@Autowired
	MasterCodeDAO masterCodeDAO;

	@Override
	public List<MasterCode> selectAll() {
		return masterCodeDAO.selectAll();
	}
	
	public MasterCode selectByStatusName(String statusName) {
		return masterCodeDAO.selectByStatusName(statusName);
	};
		
}
