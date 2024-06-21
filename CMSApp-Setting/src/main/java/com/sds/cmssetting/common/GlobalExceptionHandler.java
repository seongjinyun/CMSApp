package com.sds.cmssetting.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sds.cmssetting.exception.JwtException;

import lombok.extern.slf4j.Slf4j;

//어플리케이션 내의 모든 컨트롤러 내에서 발생하는 예외는 여기서 처리가능
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/*---------------------------------------------
	JWT 인증에 실패한 경우
	---------------------------------------------*/
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<Map<String, Object>> handle(JwtException e) {
		log.debug( e.getMessage()+", 글로벌 예외처리");
		
		//자바의 객체는 JSoN으로 변경되어 반환 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", e.getMessage()); //에러 메시지 
		map.put("status", HttpStatus.UNAUTHORIZED.value()); //상태 코드
		map.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());//상태 코드에 대한 문자열
		
		return new ResponseEntity(map, HttpStatus.UNAUTHORIZED);
	}
	
}