package com.sds.cmsdocument.model.statuslog;

import org.apache.ibatis.annotations.Mapper;

import com.sds.cmsdocument.domain.StatusLog;

@Mapper
public interface StatusLogDAO {

	// 기록만을 위한 독립 테이블에 로그 추가
	public int insert(StatusLog statusLog);

}