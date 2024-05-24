package com.sds.cmssettings.common;

/*
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sds.cmssettings.domain.Movie;
import com.sds.cmssettings.exception.UploadException;

// 파일과 관련된 업무 전담
@Component
public class FileManager {
	
	// jsp에서 application 내장 객체에 해당하는 servletContext 자료형을 스프링이 메모리에서 관리
	// autowired로 자동 주입
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private String savePath;
	
	// 확장자 반환
	public String getExt(String path) {
		return path.substring(path.lastIndexOf(".")+1, path.length());
	}
	
	// 파일명 생성
	public String createFilename(String filename) {
		long time = System.currentTimeMillis();
		String ext = getExt(filename);
		return time+"."+ext;
	}
	
	// 파일 저장, 파일명 반환
	public String save(Movie movie) throws UploadException { // Movie 내의 MultipartFile 사용
		MultipartFile file = movie.getFile(); // 업로드된 파일이 저장된 객체
		String realPath = null;
		String newName = null;
		try {
			realPath = servletContext.getRealPath(savePath);
			// System.out.println(realPath);
			newName = createFilename(file.getOriginalFilename());
			file.transferTo(new File(realPath+newName));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UploadException("업로드 실패", e);
		}
		return realPath+newName;
	}
	
}

*/
