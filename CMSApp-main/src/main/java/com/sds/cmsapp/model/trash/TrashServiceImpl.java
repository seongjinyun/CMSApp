package com.sds.cmsapp.model.trash;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.model.document.DocumentDAO;

@Service
public class TrashServiceImpl implements TrashService{
	
	@Autowired
	TrashDAO trashDAO;
	
	@Autowired
	DocumentDAO documentDAO;

	@Override
	public int insert(Trash trash) {
		return trashDAO.insert(trash);
	}

	@Override
	public int restore(int trash_idx) {
		return trashDAO.delete(trash_idx);
	}

	@Override
	public int delete(int trash_idx) {
		Trash trash = trashDAO.select(trash_idx);
		int document_idx = trash.getDocument().getDocument_idx();
		return 0;
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List selectAllWithRange(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTrash(int document_idx) {
		
		return false;
	}

	@Override
	public int selectCount() {
		return trashDAO.selectCount();
	}
	

}
