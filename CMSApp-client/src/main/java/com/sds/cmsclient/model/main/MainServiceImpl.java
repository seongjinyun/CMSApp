package com.sds.cmsclient.model.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsclient.domain.Project;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	private MainDAO mainDAO;

	public List<Project> projcetAllSelect() {
		return mainDAO.projcetAllSelect();
	}
}
