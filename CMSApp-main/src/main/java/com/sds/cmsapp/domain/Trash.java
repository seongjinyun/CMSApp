package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Trash {
	private int trash_idx;
	private Emp emp;
	private Document document;
	private String regdate;
}
