package com.sds.cmsdocument.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Bookmark {
	private int bookmarkIdx;
	private Emp emp;
	private Document document;
	private Timestamp regdate;
}
