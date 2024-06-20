package com.sds.cmsclient.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Folder {
    private Integer folderIdx;
    private Integer parentFolderId;
	private Folder parentFolder;
    private String folderName;
    private Project project;
    private String regdate;
    private List<Folder> childFolders = new ArrayList<>();
    private Integer depth;
    private List<Document> documentList;
    private List<PublishedVersion> publishedVersionList;

    public void addChildFolder(Folder childFolder) {
        this.childFolders.add(childFolder);
    }
}
