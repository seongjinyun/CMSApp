package com.sds.cmsapp.jwt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.sds.cmsapp.domain.CustomUserDetails;
import com.sds.cmsapp.domain.Emp;

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
	public String generateToken(String empName, int empIdx, String role, Long expireTime) throws Exception {
		log.debug("jwt 생성에 필요한 데이터 중 name = "+empName);
		log.debug("jwt 생성에 필요한 데이터 중 idx = "+empIdx);
		log.debug("jwt 생성에 필요한 데이터 중 role = "+role);
		log.debug("jwt 생성에 필요한 데이터 중 expireTime = "+expireTime);
		
		//jwt 생성 
		return Jwts.builder()
			.setSubject(empName)
			.claim("role", role)                                            // claim() 메서드를 이용하여 넣고 싶은 데이터를 더 넣을 수 있다
			.claim("empIdx", empIdx)
			.setIssuedAt(new Date(System.currentTimeMillis()))              // 발행 시간
			.setExpiration(new Date(System.currentTimeMillis()+expireTime)) // 만료 시간 설정, 밀리세컨드 단위
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
	
	public boolean validateToken(String token, CustomUserDetails userDetails) {
	    final String username = getUsernameFromToken(token);
	    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String getUsernameFromToken(String token) {
	    return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public Emp getEmpFromToken(String token) {
	    final Emp emp = getEmpFromToken(token);
	    return emp;
	}

	public boolean isTokenExpired(String token) {
	    final Date expiration = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody().getExpiration();
	    return expiration.before(new Date());
	}
	
	
}