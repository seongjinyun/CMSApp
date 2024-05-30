package com.sds.cmsapp.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class Folder {
	private int folder_idx;
	private Folder parent_folder;
	private String folder_name;
	private Project project;
	private Timestamp regdate;
	private List<Folder> child_folder;
}
