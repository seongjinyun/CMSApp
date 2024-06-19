package com.sds.cmssettings.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class EncryptionManager {
	
	public static String getConvertedData(String password) {
		
		StringBuilder hash=new StringBuilder(); //최종적인 암호화 결과를 담게될 스트링
		
		//암호화 알고리즘을 처리하는 객체 MessageDigest 객체
		try {
			MessageDigest digest= MessageDigest.getInstance("SHA-256"); //64자 해시값 만들기
			System.out.println("다이제스트 생성 성공");
			
			byte[] bytes = password.getBytes(); //스트링을 바이트 배열로 변환

//			for(int i=0;i<bytes.length;i++) {
//				System.out.println(bytes[i]);
//			}
			
			bytes = digest.digest(bytes); //암호화시킬 대상 데이터를 byte[] 형 배열로 넣어줘야 함
			System.out.println("다이제스트가 처리한 이후의 배열의 길이 "+ bytes.length);
			
			for(int i=0;i<bytes.length;i++) {
				//System.out.println(bytes[i]);
				String hex = Integer.toHexString(0xff & bytes[i]);
				System.out.println(hex);

				if(hex.length()==1) {
					hash.append("0");
				}
				
				hash.append(hex);
			}
			
			//System.out.println(hash.length());	
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("알고리즘 이름을 확인해 주세요");
		}
		
		return hash.toString();
	}
}