package com.sds.cmsdocument.model.trash;

import java.util.List;
import java.util.Map;

import com.sds.cmsdocument.domain.Trash;

public interface TrashService {
	// 휴지통에 넣기
	public int insert(final Integer documentIdx, final Integer empIdx);
	
	// 복원
	public int restore(final Integer trashIdx);
	
	// 영구삭제(선택)
	public int delete(final Integer trashIdx);
	
	// 휴지통 비우기(전체)
	public int deleteAll();
	
	// 전체 리스트 조회
	public List<Trash> selectAllWithRange(final Map<String, Integer> map);
	
	// 휴지통에 있는 문서인지 조회(true면 휴지통에 있음)
	public boolean isTrash(final Integer documentIdx);
	
	// 총 레코드 수
	public int selectCount();
	
	// 문서와 폴더를 분리
	
	/**
	* @methodName	: seperateObjectList
	* @author		: Junhyung Park
	* @date			: 2024.06.17
	* @param 스트링 리스트 (d20, f23..)등 d 또는 f + Idx
	* @param 문서를 원하면 'd', 폴더를 원하면 'f'
	* @return 문자열을 떼고 정수 리스트를 반환합니다
	*/
	public List<Integer> seperateObjectList(List<String> objectIdxList, char firstLetter);
	
}
