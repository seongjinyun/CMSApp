package com.sds.cmsapp.model.trash;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.DocumentVersion;
import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.domain.VersionLog;
import com.sds.cmsapp.model.document.DocumentDAO;
import com.sds.cmsapp.model.document.DocumentVersionDAO;
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

	@Override
	public int insert(Trash trash) {
		return trashDAO.insert(trash);
	}

	@Override
	public int restore(Integer trash_idx) {
		return trashDAO.delete(trash_idx);
	}

	@Override
	public int delete(Integer trash_idx) {
		Trash trash = trashDAO.select(trash_idx);
		int document_idx = trash.getDocument().getDocument_idx();
		trashDAO.delete(trash_idx);
		
		return 0;
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List selectAllWithRange(Map map) {
		List<Trash> trashList = trashDAO.selectAllWithRange(map);
		for (int i = 0; i < trashList.size(); i++) {
			Trash trash = trashList.get(i);
			DocumentVersion documentVersion = documentVersionDAO.selectByDocumentIdx(i);
			VersionLog versionLog = documentVersion.getVersionLog();
			trash.setVersionLog(versionLog);
		}
		return trashList;
	}

	@Override
	public boolean isTrash(Integer document_idx) {
		
		return false;
	}

	@Override
	public int selectCount() {
		return trashDAO.selectCount();
	}
	

}
