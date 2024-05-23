package com.sds.cmsapp.domain;

import lombok.Data;

@Data
public class Member {
	private int member_idx;
	private Sns sns;
	private String member_id;
	private String member_name;
	private String member_email;
}
