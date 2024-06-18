package com.sds.cmssettings.model.trash;

import java.util.List;
import java.util.Map;

import com.sds.cmssettings.domain.Trash;

public interface TrashService {
	
	// 휴지통에 넣기
	public int insert(final Integer documentIdx, final Integer empIdx);
	
}
