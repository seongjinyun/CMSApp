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
	public int delete(Integer trash_idx);
	
	// 휴지통 비우기
	public int deleteAll();
	
	// 휴지통 한건 조회
	public Trash select(Integer trash_idx);
	
	// 휴지통 문서 수 조회
	public int selectCount();
	
	// 휴지통 내 문서 전체조회
	public int selectAll();
	
	// 휴지통 내 문서 리스트 조회
	public List<Trash> selectAllWithRange(Map map);
	
	// 문서번호로 휴지통 한건 조회
	public Trash selectByDocumentIdx(int document_idx);

}
