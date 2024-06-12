package com.sds.cmsapp.model.trash;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Emp;
import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.model.document.DocumentDAO;
import com.sds.cmsapp.model.document.DocumentVersionDAO;
import com.sds.cmsapp.model.emp.EmpDAO;
import com.sds.cmsapp.model.versionlog.VersionLogDAO;

@Service
public class TrashServiceImpl implements TrashService{
	
	@Autowired
	TrashDAO trashDAO;
	
	@Autowired
	DocumentDAO documentDAO;
	
	@Autowired
	DocumentVersionDAO documentVersionDAO;
	
	@Autowired
	VersionLogDAO versionLogDAO;
	
	@Autowired
	EmpDAO empDAO;

	@Override
	public int insert(final Integer documentIdx, final Integer empIdx) {
		Document document = documentDAO.select(documentIdx);
		Emp emp = empDAO.selectByEmpIdx(empIdx);
		Trash trash = new Trash();
		trash.setDocument(document);
		trash.setEmp(emp);
		return trashDAO.insert(trash);
	}

	@Override
	public int restore(final Integer trashIdx) {
		return trashDAO.delete(trashIdx);
	}

	@Override
	@Transactional
	public int delete(final Integer trashIdx) {
		Trash trash = trashDAO.select(trashIdx);
		documentDAO.delete(trash.getDocument().getDocumentIdx());
		int result = trashDAO.delete(trashIdx);
		
		return result;
	}

	@Override
	public int deleteAll() {
		return trashDAO.deleteAll();
	}

	@Override
	public List<Trash> selectAllWithRange(final Map<String, Integer> map) {
		List<Trash> trashList = trashDAO.selectAllWithRange(map);
		for (int i = 0; i < trashList.size(); i++) {
			Trash trash = trashList.get(i);
			DocumentVersion documentVersion = documentVersionDAO.selectByDocumentIdx(trash.getDocument().getDocumentIdx());
			VersionLog versionLog = documentVersion.getVersionLog();
			trash.setVersionLog(versionLog);
		}
		return trashList;
	}

	@Override
	public boolean isTrash(final Integer documentIdx) {
		boolean flag = false;
		if(trashDAO.selectByDocumentIdx(documentIdx) != null) {
			flag = true;
		}
		return flag;
	}

	@Override
	public int selectCount() {
		return trashDAO.selectCount();
	}
	

}
