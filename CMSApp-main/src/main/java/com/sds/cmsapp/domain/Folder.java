package com.sds.cmsapp.domain;

import java.util.List;

import lombok.Data;

@Data
public class Folder {
	private Integer folder_idx;
	private Folder parent_folder;
	private String folder_name;
	private Project project;
	private String regdate;
	private List<Folder> childFolderList;
	private Integer depth;
	private List<Document> documentList;
}
