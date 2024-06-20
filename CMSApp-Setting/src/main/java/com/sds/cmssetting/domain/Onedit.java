package com.sds.cmssetting.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Onedit {
	private int oneditIdx;
	private Emp emp;
	private Document document;
	private Timestamp regdate;
}
