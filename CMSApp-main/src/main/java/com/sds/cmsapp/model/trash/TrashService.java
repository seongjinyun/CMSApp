package com.sds.cmsapp.model.trash;

import java.util.List;
import java.util.Map;

import com.sds.cmsapp.domain.Trash;

public interface TrashService {
	// 휴지통에 넣기
	public int insert(Trash trash);
	
	// 복원
	public int restore(Integer trash_idx);
	
	// 영구삭제(선택)
	public int delete(Integer trash_idx);
	
	// 휴지통 비우기(전체)
	public int deleteAll();
	
	// 전체 리스트 조회
	public List selectAllWithRange(Map map);
	
	// 휴지통에 있는 문서인지 조회(true면 휴지통에 있음)
	public boolean isTrash(Integer document_idx);
	
	// 총 레코드 수
	public int selectCount();
	
	
}
