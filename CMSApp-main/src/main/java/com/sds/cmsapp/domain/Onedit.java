package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Onedit {
	private int onedit_idx;
	private Emp emp;
	private Document document;
	private String regdate;
}
