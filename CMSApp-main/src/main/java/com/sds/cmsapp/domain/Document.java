package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Document {
	private int document_idx;
	private Emp emp;
	private int hit;
}
