package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Bookmark {
	private int bookmark_idx;
	private Emp emp;
	private Document document;
	private Timestamp regdate;
}
