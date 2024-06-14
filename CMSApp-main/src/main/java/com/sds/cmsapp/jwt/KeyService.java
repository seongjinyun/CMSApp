package com.sds.cmsapp.jwt;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

//같은 클라우드 내에서 실행중인 프로젝트의 공개키를 api를 이용하여 가져오자

@Slf4j
@Service
public class KeyService {
	
	public String getPublicKey() {
		// 현재 어플리케이션이 아닌, 외부의 어플리케이션과 비동기로 통신하기 위해서 RestTemplate 사용해보자
		HttpHeaders headers = new HttpHeaders();
		HttpEntity entity   = new HttpEntity(headers);
		
		// 비동기 GET요청 
		String url="/jwt/key";
		RestTemplate restTemplate=new RestTemplate();
		ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		log.debug("외부사이트로 부터 가져온 공개키는 "+response.getBody());
		
		return response.getBody();
	}
}