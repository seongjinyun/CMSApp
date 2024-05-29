package com.sds.cmsapp.domain;

import java.util.List;

import lombok.Data;

@Data
public class Folder {
	private int folder_idx;
	private Folder parent_folder;
	private String folder_name;
	private String regdate;
	private List<Folder> child_folder;
}
