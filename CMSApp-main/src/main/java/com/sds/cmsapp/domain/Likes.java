package com.sds.cmsapp.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Likes {
	private int likes_idx;
	private Member member;
	private Document document;
	private Timestamp regdate;

}
