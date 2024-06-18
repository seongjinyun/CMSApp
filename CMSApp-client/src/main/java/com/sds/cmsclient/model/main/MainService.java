package com.sds.cmsclient.model.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsclient.domain.Project;

public interface MainService {
	public List<Project> projcetAllSelect();

}
