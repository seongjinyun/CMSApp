package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Folder {
	private int folder_idx;
	private Folder parent_folder;
	private String folder_name;
	private Project project;
	private String regdate;
}
