package com.sds.cmsapp.domain;

import java.util.List;

import lombok.Data;

@Data
public class Folder {
	private Integer folderIdx;
	private Folder parentFolder;
	private String folderName;
	private Project project;
	private String regdate;
	private List<Folder> childFolderList;
	private Integer depth;
	private List<Document> documentList;

}
