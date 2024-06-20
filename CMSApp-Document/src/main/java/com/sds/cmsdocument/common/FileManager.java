package com.sds.cmsdocument.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sds.cmsdocument.domain.EmpDetail;
import com.sds.cmsdocument.exception.UploadException;

//파일과 관련된 업무를 전담하는 객체 
@Service
public class FileManager {
	
	@Value("${file.upload-dir}")
    private String savePath;
	
	// 확장자 반환 
	public String getExt(String path) {
		return path.substring(path.lastIndexOf(".")+1 , path.length());
	}
	
	// 파일명 생성 
	public String createFilename(String filename) {
		// 파일명 만들기 
		long time = System.currentTimeMillis(); //34234234234
		String ext = getExt(filename);
		
		return time+"."+ext;
	}
	
	// 파일저장(웹서버에 저장)
	public String save(EmpDetail empDetail) throws UploadException{
		// MultipartFile을 꺼내어, 하드디스크에 저장 
		MultipartFile file = empDetail.getFile(); //업로드된 파일이 저장된 객체 꺼내기
		String newName = createFilename(file.getOriginalFilename());
		
		try {
			System.out.println("Save Path: "+savePath);
			Path path = Paths.get(savePath);
			
			// 저장 경로가 존재하지 않으면 생성
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
			
            // 파일을 저장할 경로 설정
            Path filePath = Paths.get(savePath, newName);
            // 파일을 지정된 경로에 저장
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			// file.transferTo(new File(savePath + File.separator + newName));
            // 파일에 접근할 URL 생성
            String fileUrl = newName;
            
            System.out.println("DB에 입력되는 파일 url 값: "+fileUrl);
            return fileUrl;
            
		} catch (IOException e) {
            e.printStackTrace();
            throw new UploadException("파일 업로드 실패", e);
        }
	}
	
}