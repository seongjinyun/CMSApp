package com.sds.cmsapp.jwt;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Data
public class JwtParser {
	
	//base64로 인코딩된 문자열을 인수로 넘기면, PublicKey 객체반환
	public PublicKey getPublicKeyFromString(String base64Key) throws Exception{
		byte[] keybytes = Base64.getDecoder().decode(base64Key); //인코딩된 문자열을 다시 푼다
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePublic(keySpec);
	}
	
	/*--------------------------------------------------
	 JWT 가 보유한 내용물을 보안 분야에서는 Claim이라 한다 
	---------------------------------------------------*/
	public Claims getClaim(String publicKey, String token) {
		return Jwts.parser()
			.setSigningKey(publicKey)	
			.parseClaimsJws(token)
			.getBody();
	}
	
}