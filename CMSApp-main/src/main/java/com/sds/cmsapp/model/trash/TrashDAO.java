package com.sds.cmsapp.model.trash;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.cmsapp.domain.Trash;

@Mapper
public interface TrashDAO {
	// 휴지통에 넣기
	public int insert(Trash trash);
	
	// 휴지통에서 제거 (복원, 영구삭제)
	public int delete(int trash_idx);
	
	// 휴지통 문서 수 조회
	public int selectCount();
	
	// 휴지통 내 문서 리스트 조회
	public List selectAll(Map map);
	
	// 휴지통 비우기
	public int deleteAll();
	
	// 문서로 휴지통에 있는 문서인지 조회(있다면 Trash 반환)
	public Trash isTrash(int document_idx);


}
