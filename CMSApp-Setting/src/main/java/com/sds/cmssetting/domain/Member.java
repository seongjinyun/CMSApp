package com.sds.cmssetting.domain;

import lombok.Data;

@Data
public class Member {
	private int memberIdx;
	private Sns sns;
	private String memberId;
	private String memberName;
	private String memberEmail;
}
