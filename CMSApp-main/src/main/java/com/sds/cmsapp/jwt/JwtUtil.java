package com.sds.cmsapp.jwt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
	
	private PrivateKey privateKey; // 개인키 - 서명에 사용할 예정 
	private PublicKey publicKey;   // 공개키 - 클라이언트가 보유한 서명(jwt + 개인키로 암호화된 결과)
	
	/*---------------------------------
	RSA 의 키 쌍을 생성하는 메서드 
	 ---------------------------------*/
	public JwtUtil() throws Exception{
		//RSA알고리즘을 이용한 키 쌍을 생성할 수 있는 객체 얻기 
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(2048); //비트 단위의 크기를 명시
		
		//이 시점에, 공개키와 개인키의 쌍을 만들어버리자 
		KeyPair keyPair = keyPairGen.generateKeyPair();
		privateKey = keyPair.getPrivate(); //개인키 생성 
		publicKey = keyPair.getPublic(); //공개키 생성 
		
		log.info("RSA 키 쌍 생성 완료");
	}
	
	//base64로 인코딩된 문자열을 인수로 넘기면, PublicKey 객체반환
	public PublicKey getPublicKeyFromString(String base64Key) throws Exception {
		byte[] keybytes = Base64.getDecoder().decode(base64Key); //인코딩된 문자열을 다시 푼다
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePublic(keySpec);
	}
	
	/*---------------------------------
	JWT에 서명된 토큰 생성 (JWT+privateKey) 
	JWT에는 왠만해서는, 보안상 민감한 정보는 절대로 넣지말자!!
	 ---------------------------------*/
	public String generateToken(int empIdx, Long expireTime) throws Exception {
		
		Map<String, Object> claims = new HashMap<>();
        claims.put("empIdx", empIdx);
		
		//jwt 생성 
		return Jwts.builder()
			.setClaims(claims)
            .setSubject(Integer.toString(empIdx))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTime))
			.signWith(SignatureAlgorithm.RS256, privateKey)
			.compact(); 													// jwt 토큰 생성 및 직렬화(string으로 변환) 
	}
	
	/*------------------------------------
	공개키를 Base64기반의 인코딩 문자열 반환
	------------------------------------*/
	public String getEncodedPublicKey() {
		
		byte[] publicKeyBytes = publicKey.getEncoded();
		String encodedPublicKey = Base64.getEncoder().encodeToString(publicKeyBytes);
		log.debug("Base64 기반의 인코딩 결과 "+encodedPublicKey);
		
		return encodedPublicKey;
	}
}