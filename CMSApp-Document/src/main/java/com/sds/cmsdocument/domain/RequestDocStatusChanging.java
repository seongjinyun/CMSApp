package com.sds.cmsdocument.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestDocStatusChanging {

	public String statusName;
	
	public int empIdx;
	public String roleName;
	public String comments;
	
}
