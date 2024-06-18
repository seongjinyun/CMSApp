package com.sds.cmssettings.model.trash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmssettings.domain.Document;
import com.sds.cmssettings.domain.Emp;
import com.sds.cmssettings.domain.Trash;
import com.sds.cmssettings.model.document.DocumentDAO;
import com.sds.cmssettings.model.emp.EmpDAO;

@Service
public class TrashServiceImpl implements TrashService{
	
	@Autowired
	private TrashDAO trashDAO;
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private EmpDAO empDAO;
	
	@Override
	public int insert(final Integer documentIdx, final Integer empIdx) {
		Document document = documentDAO.select(documentIdx);
		Emp emp = empDAO.selectByEmpIdx(empIdx);
		Trash trash = new Trash();
		trash.setDocument(document);
		trash.setEmp(emp);
		return trashDAO.insert(trash);
	}
}
