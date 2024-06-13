package com.sds.cmsapp.model.bookmark;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.cmsapp.domain.Bookmark;

@Mapper
public interface BookmarkDAO {
	// 북마크에 한건 추가
	public int insert(Bookmark bookmark);
	
	// 북마크에서 한건 제거
	public int delete(Bookmark bookmark);
	
	// 북마크 한건 조회
	public Bookmark select(int bookmark_idx);
	
	// 북마크 페이지에서 한건 제거 (북마크번호로)
	public int deleteByBookmarkIdx(int bookmark_idx);
	
	// 문서 번호로 삭제
	public Integer deleteByDocumentIdx(final int documentIdx);
	
	// 사용자별 북마크된 문서리스트 조회
	public List<Bookmark> selectByEmpIdx(@Param("emp_idx") int emp_idx, @Param("startIndex") int startIndex, @Param("rowCount") int rowCount);
	
	// 북마크 갯수(사원별)
	public int selectCountByEmpIdx(int emp_idx);
	
	// 북마크 갯수(문서별)
	public int selectCountByDocumentIdx(int document_idx);
	
	// 북마크인지 조회
	public Bookmark isBookmark(Bookmark bookmark);
}
