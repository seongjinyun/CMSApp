package com.sds.cmsapp.model.folder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.cmsapp.domain.Document;
import com.sds.cmsapp.domain.Folder;
import com.sds.cmsapp.domain.Trash;
import com.sds.cmsapp.exception.FolderException;
import com.sds.cmsapp.model.document.DocumentDAO;
import com.sds.cmsapp.model.document.DocumentService;
import com.sds.cmsapp.model.emp.EmpDAO;
import com.sds.cmsapp.model.trash.TrashDAO;

@Service
public class FolderServiceImpl implements FolderService {
		
	@Autowired
	private FolderDAO folderDAO;
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private TrashDAO trashDAO;
	
	@Autowired
	private EmpDAO empDAO;
	
	@Autowired
	private DocumentService documentService;

	@Override
	public int moveDirectory(final int documentIdx, final int targetFolderIdx) {
		Document document = documentDAO.select(documentIdx);
		document.setFolder(folderDAO.select(targetFolderIdx));
		return documentDAO.update(document);
	}

	@Override
	public int  createFolder(final Folder folder) {
		return folderDAO.insert(folder);
	}

	@Override
	@Transactional
	public void deleteFolder(final int folderIdx, final int empIdx) throws FolderException {
		deleteFolder(folderIdx, empIdx, 0);
	}
	
	private void deleteFolder(final int folderIdx, final  int empIdx, final int depth) throws FolderException {
	
		if (depth > 1000) { // 재귀 깊이 제한
			throw new FolderException("재귀 깊이 초과: 폴더 삭제 과정에서 문제가 발생했습니다. 하위 폴더부터 삭제해주세요.");
		}
		List<Folder> childFolderList = folderDAO.selectSub(folderIdx);
		List<Document> documentList = documentDAO.selectByFolderIdx(folderIdx);
		if(documentList != null) {
			for(Document document : documentList) {
				Folder folder = document.getFolder();
				folder.setFolderIdx(null);
				document.setFolder(folder); // document의 folder를 restored로
				documentDAO.update(document);
				Trash trash = new Trash();
				trash.setDocument(document);
				trash.setEmp(empDAO.selectByEmpIdx(empIdx));
				trashDAO.insert(trash);
			}
		}
		if(childFolderList != null) {
			for(Folder folder : childFolderList) {
				deleteFolder(folder.getFolderIdx(), empIdx, depth + 1);

			}
		}
		folderDAO.delete(folderIdx);
	}

	@Override
	public int updateFolder(final Folder folder) {
		return folderDAO.update(folder);
	}
	
	@Override
	public Folder select(final int folderIdx) {
		return folderDAO.select(folderIdx); 
	}

	@Override
	public List<Folder> selectSub(final int folderIdx) {
		return folderDAO.selectSub(folderIdx);
	}

	@Override
	public List<Folder> selectAll() {
		return folderDAO.selectAll();
	}
	
	@Override
	public int selectDepth(final int folderIdx) throws FolderException {
		int depth = 1;
		
		Integer parentFolderIdx = folderIdx;
		Folder folder = null;
		while(true) {
			folder = folderDAO.select(parentFolderIdx);
			Integer index = folderDAO.selectParentIdx(folder.getFolderIdx());
			if (index == null) {
				break;
			}
			if (depth > 100) {
				throw new FolderException("반복 횟수가 많습니다");
			}
			parentFolderIdx = index;
			depth++;
			
		} 
		return depth;
	}
	
	// 현재 폴더부터 최상위 폴더까지 리스트에 담은 후 순서 반전
	@Override
	public List<String> selectPath(final int folderIdx) {
		Folder folder = null; 
		Integer parentFolderIdx = folderIdx;
		List<String> folderNameList = new ArrayList<String>();
		folderNameList.add(folderDAO.select(folderIdx).getFolderName());
		while(true) {
			folder = folderDAO.select(parentFolderIdx);
			Folder parentFolder = folder.getParentFolder();
			if (parentFolder == null) {
				break;
			}
			parentFolderIdx = parentFolder.getFolderIdx();
			folderNameList.add(folderDAO.select(parentFolderIdx).getFolderName());
		}
		Collections.reverse(folderNameList);
		return folderNameList;
	}
	
	public List<Folder> selectTopFolder(){
		return folderDAO.selectTopFolder();
	}
	
	public List<Folder> completeFolderList(final List<Folder> folderList){
		List<Folder> folderList1 = new ArrayList<Folder>();
		List<Folder> folderList2 = new ArrayList<Folder>();
		folderList1.addAll(folderList);
		while(true) {
			for (Folder folder : folderList1) {			
				List<Folder> subList = folderDAO.selectSub(folder.getFolderIdx());				
				folder.setChildFolderList(subList);
				folderList2.addAll(subList);
			}
			folderList1.clear();
			if (folderList2.size() == 0) {
				break;
			}
			folderList1.addAll(folderList2);
			folderList2.clear();
		}
		return folderList;
	}
	
	public Folder completeFolder(final int folderIdx) throws FolderException{
		int count = 0;
		Folder folder = folderDAO.select(folderIdx);
		List<Folder> folderList1 = new ArrayList<Folder>();
		List<Folder> folderList2 = new ArrayList<Folder>();
		folderList1.add(folder);
		while(true) {
			for (Folder folderDTO : folderList1) {			
				List<Folder> subList = folderDAO.selectSub(folderDTO.getFolderIdx());				
				folderDTO.setChildFolderList(subList);
				folderList2.addAll(subList);
			}
			folderList1.clear();
			if (folderList2.isEmpty()) {
				break;
			}
			folderList1.addAll(folderList2);
			folderList2.clear();
			count++;
			if (count > 1000) {
				throw new FolderException("폴더 DTO 생성 중 무한루프 발생 or 반복횟수 너무 많음");
			}
		}
		return folder;
	}
	
	
	public Folder fillSub(final Folder folder) {
		List<Folder> subList = folderDAO.selectSub(folder.getFolderIdx());
		folder.setChildFolderList(subList);
		return folder;
	}
	
	@Override
	public Folder fillDocument(final int folderIdx) {
		Folder folder = folderDAO.select(folderIdx);
		List<Document> documentList = documentDAO.selectByFolderIdx(folderIdx);
		folder.setDocumentList(documentList);
		return folder;
	}
	
	@Override
	public Folder completeFolderWithDocument(final int folderIdx) throws FolderException {
		int count = 0;
		Folder folder = folderDAO.select(folderIdx);
		List<Folder> folderList1 = new ArrayList<Folder>();
		List<Folder> folderList2 = new ArrayList<Folder>();
		List<Document> documentList = new ArrayList<>();
		folderList1.add(folder);
		while(true) {
			for (Folder folderDTO : folderList1) {			
				List<Folder> subList = folderDAO.selectSub(folderDTO.getFolderIdx());				
				folderDTO.setChildFolderList(subList);
				folderDTO.setDepth(selectDepth(folderDTO.getFolderIdx()));
				List<Document> subDocumentList = documentDAO.selectByFolderIdx(folderDTO.getFolderIdx());
				for(int i = 0; i < subDocumentList.size(); i++) {
					Document document = subDocumentList.get(i);
					document = documentService.fillVersionLog(document);
					document.setFolder(folderDAO.select(folderDTO.getFolderIdx()));
					subDocumentList.set(i, document);
				}
				folderDTO.setDocumentList(subDocumentList);
				folderList2.addAll(subList);
				documentList.addAll(subDocumentList);
			}
			folderList1.clear();
			if (folderList2.isEmpty()) {
				break;
			}
			folderList1.addAll(folderList2);
			folderList2.clear();
			count++;
			if (count > 1000) {
				throw new FolderException("폴더 DTO 생성 중 무한루프 발생 or 반복횟수 너무 많음");
			}
		}
		return folder;
	}
	
	@Override
	public List<Folder> selectByProjectIdx(final int projectIdx) {
		return folderDAO.selectByProjectIdx(projectIdx);
	}
	
	@Override
	public Folder selectProjectRootFolder(final int projectIdx) {
		Folder result = new Folder();
		List<Folder> folderList = folderDAO.selectByProjectIdx(projectIdx);
		for(Folder folder : folderList) {
			if(folder.getParentFolder() == null) {
				result = folder;
			}
		}
		return result;
	}
	
	// 조상부터 시작해서 자기까지
	public List<Folder> selectParentList(final int folderIdx){
		Folder folder = folderDAO.select(folderIdx);
		List<Folder> folderList = new ArrayList<>();
		Folder parentFolder = folder;
		while(parentFolder != null) {
			folderList.add(parentFolder);
			parentFolder = parentFolder.getParentFolder();
		}
		Collections.reverse(folderList);
		
		return folderList;
	}
	
	@Override
	public Folder selectRestoreFolder() {
		return folderDAO.selectRestoreFolder();
	}
}
