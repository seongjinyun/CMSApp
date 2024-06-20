package com.sds.cmsdocument.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Likes {
	private int likesIdx;
	private Member member;
	private Document document;
	private Timestamp regdate;

}
