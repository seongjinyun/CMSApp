package com.sds.cmsapp.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


// Dashboard 메뉴에서 문서 목록을 조회할 때 사용할 필터 조건
@Getter @Setter
public final class RequestDocumentDTO {
	
	private List<Integer> statusCodeList;
	private Timestamp startDate;
	private Timestamp endDate;
	private List<Integer> projectIdxList;
	
	//private List<Integer> trashDocumentIdxList;
	//private List<Integer> folderIdxList;
	
}

/*
 * 생성자 생성하는 방법이 더 권장
@NoArgsConstructor
기본 생성자를 생성해준다.이 경우 초기값 세팅이 필요한 final 변수가 있을 경우 컴파일 에러가 발생함으로 주의한다.
@NoArgsConstructor(force=true) 를 사용하면 null, 0 등 기본 값으로 초기화 된다.

@RequiredArgsConstructor
final 변수, Notnull 표시가 된 변수처럼 필수적인 정보를 세팅하는 생성자를 만들어준다.
출처: https://goyunji.tistory.com/98 [윤복로그:티스토리]

@AllArgsConstructor
전체 변수를 생성하는 생성자를 만들어준다.
출처: https://goyunji.tistory.com/98 [윤복로그:티스토리]
*/

/*
기본 생성자가 필요하지 않다면 DTO는 아래와 같은 불 객체로 충분

// 예제 1
@Getter
@RequiredArgsConstructor // 또는 @AllArgsConstructor
public final class RequestDto {

    private final String searchText;

    private final String searchType;
} 
출처: [Spring Boot] DTO를 홀대하지 않는 방법
 */
