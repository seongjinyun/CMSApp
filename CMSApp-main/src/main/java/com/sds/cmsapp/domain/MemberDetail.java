package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class MemberDetail {
	private int member_detail_idx;
	private Member member;
	private String member_pw;
	private String member_phone;
	private String member_addr;
}
