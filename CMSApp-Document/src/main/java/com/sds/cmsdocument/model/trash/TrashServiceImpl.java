package com.sds.cmsdocument.model.trash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsdocument.domain.Document;
import com.sds.cmsdocument.domain.DocumentVersion;
import com.sds.cmsdocument.domain.Emp;
import com.sds.cmsdocument.domain.Folder;
import com.sds.cmsdocument.domain.Trash;
import com.sds.cmsdocument.domain.VersionLog;
import com.sds.cmsdocument.exception.TrashException;
import com.sds.cmsdocument.model.bookmark.BookmarkDAO;
import com.sds.cmsdocument.model.document.DocumentDAO;
import com.sds.cmsdocument.model.document.DocumentService;
import com.sds.cmsdocument.model.document.DocumentVersionDAO;
import com.sds.cmsdocument.model.document.OneditDAO;
import com.sds.cmsdocument.model.emp.EmpDAO;
import com.sds.cmsdocument.model.folder.FolderDAO;
import com.sds.cmsdocument.model.publishing.PublishedVersionDAO;
import com.sds.cmsdocument.model.versionlog.VersionLogDAO;

@Service
public class TrashServiceImpl implements TrashService{
	
	@Autowired
	private TrashDAO trashDAO;
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private DocumentVersionDAO documentVersionDAO;
		
	@Autowired
	private EmpDAO empDAO;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private FolderDAO folderDAO;
	
	@Autowired 
	private OneditDAO oneditDAO;
	
	@Autowired
	private BookmarkDAO bookmarkDAO;
	
	@Autowired
	private VersionLogDAO versionLogDAO;
	
	@Autowired
	private PublishedVersionDAO publishedVersionDAO;
	

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
		Trash trash = trashDAO.select(trashIdx);
		Document document = trash.getDocument();
		if(document.getFolder() == null) { // 복원됐는데 돌아갈 곳이 없다면
			if(folderDAO.selectRestoreFolder() == null) {
				Folder folder = new Folder();
				folder.setFolderName("restored");
				folderDAO.insert(folder);
			}
			document.setFolder(folderDAO.selectRestoreFolder());
			documentDAO.update(document);
		}
		return trashDAO.delete(trashIdx);
	}

	@Override
	@Transactional
	public int delete(final Integer trashIdx) throws TrashException {
		Trash trash = trashDAO.select(trashIdx);
		int documentIdx = trash.getDocument().getDocumentIdx();
		if(!oneditDAO.selectByDocumentIdx(documentIdx).isEmpty()) {
			throw new TrashException("수정 작업중인 문서입니다");
		};
		oneditDAO.deleteByDocumentIdx(documentIdx);
		trashDAO.delete(trashIdx);
		bookmarkDAO.deleteByDocumentIdx(documentIdx);
		publishedVersionDAO.deleteByDocumentIdx(documentIdx);
		documentVersionDAO.deleteByDocumentIdx(documentIdx);
		versionLogDAO.deleteByDocumentIdx(documentIdx);
		
		//publishedVersionD
		int result = trashDAO.delete(trashIdx);
		documentService.delete(trash.getDocument().getDocumentIdx()); // 삭제의 책임을 documentService에게
		return result;
	}

	@Override
	@Transactional
	public int deleteAll() {
		int count = 0;
		List<Trash> trashList = trashDAO.selectAll();
		for(Trash trash : trashList) {
			delete(trash.getTrashIdx());
			count++;
		}
		return count;
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
	
	/**
	 * 
	 * @param objectIdxList 문서와 폴더의 id가 섞인 스트링 List ex) f1, f2, d1...
	 * @param firstLetter 폴더는 f, 문서는 d
	 * @return 두번째 param이 f 일 경우 폴더의 Integer idxList, 문서일 경우 문서의 Integer idxList
	 */
	@Override
	public List<Integer> seperateObjectList(List<String> objectIdxList, char firstLetter) throws TrashException {
		if(firstLetter != 'f' && firstLetter != 'd') {
			throw new TrashException("두번째 인수가 'f' 또는 'd' 가 아닙니다.");
		}
		List<Integer> resultIdxList = new ArrayList<>();
		for(String object : objectIdxList) {
			if(object.charAt(0) == firstLetter) {
				String str = object.substring(1);
				Integer documentIdx = Integer.parseInt(str);
				resultIdxList.add(documentIdx);
			}
		}
		
		return resultIdxList;
	}
	
	

}
