package com.sds.cmsapp.common;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Pager {
	private int totalRecord; 		//총 레코드 수 
	private int pageSize=10; 		//페이지당 보여질 레코드 수 
	private int totalPage; 			//총 페이지 수 
	private int blockSize=10; 		//블럭당 보여질 페이지 수 
	private int currentPage=1; 	//사용자가 현재 보는 페이지
	private int firstPage; 			//블럭당 반복문의 시작 값 
	private int lastPage;  			//블럭당 반복문의 끝값 
	private int startIndex; 			// mysql의 limit 첫번째 요소값(페이지당 시작 index) ex) 1page = 0 , 2page =10....
	private int num; 					//페이지당 시작 번호 (게시물수를 알 수 있다)
	
	public void init(int totalRecord, int currentPage) {
		this.totalRecord = totalRecord;
		this.totalPage = (int)Math.ceil((float)this.totalRecord/this.pageSize);
		this.currentPage=currentPage;
		this.firstPage = this.currentPage - (this.currentPage-1)%this.blockSize;
		this.lastPage = this.firstPage + (this.blockSize-1);
		//총 가진 페이지 수가 lastPage 작을 경우 totalPage가 lastPage가 되면 됨 
		if(totalPage <=lastPage) {
			lastPage = totalPage;
		}
		this.startIndex = (this.currentPage-1)*this.pageSize;
		this.num = this.totalRecord - this.startIndex;
	}
}
