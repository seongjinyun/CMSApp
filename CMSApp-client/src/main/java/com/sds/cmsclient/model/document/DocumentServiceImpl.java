package com.sds.cmsclient.model.document;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.cmsclient.domain.Document;
import com.sds.cmsclient.domain.Folder;
import com.sds.cmsclient.domain.PublishedVersion;
import com.sds.cmsclient.domain.VersionLog;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDAO documentDAO;

    public List<Folder> getFolderListByProjectIdx(int projectIdx) {
        List<Folder> folderList = documentDAO.getFolderListByProjectIdx(projectIdx);
        
        // 상위 폴더 객체 참조 설정
        for (Folder folder : folderList) {
            if (folder.getParentFolderId() != null) {
                folder.setParentFolder(findParentFolder(folderList, folder.getParentFolderId()));
            }

        }
        
        List<Folder> rootFolders = folderList.stream()
                .filter(f -> f.getParentFolderId() == null)
                .collect(Collectors.toList());
        setChildFoldersAndDocuments(rootFolders, folderList);
        return rootFolders;
    }

    private void setChildFoldersAndDocuments(List<Folder> parentFolders, List<Folder> allFolders) {
        for (Folder parent : parentFolders) {
            List<Folder> childFolders = allFolders.stream()
                    .filter(f -> f.getParentFolderId() != null && f.getParentFolderId().equals(parent.getFolderIdx()))
                    .collect(Collectors.toList());
            parent.setChildFolders(childFolders);
            setChildFoldersAndDocuments(childFolders, allFolders);
            // 해당 폴더의 문서 목록 설정
            List<PublishedVersion> publishedVersions = documentDAO.getPublishedVersionIdx(parent.getFolderIdx());
            parent.setPublishedVersionList(publishedVersions);
        }
    }

    private Folder findParentFolder(List<Folder> folderList, Integer parentFolderId) {
        return folderList.stream()
                .filter(f -> f.getFolderIdx().equals(parentFolderId))
                .findFirst()
                .orElse(null);
    }
    
    public List<PublishedVersion> getPublishedVersionByDocumentIdx(int documentIdx) {
        return documentDAO.getPublishedVersionByDocumentIdx(documentIdx);
    }
    
	public String projectNameSelect(int projectIdx) {
		return documentDAO.projectNameSelect(projectIdx);
	}

}
