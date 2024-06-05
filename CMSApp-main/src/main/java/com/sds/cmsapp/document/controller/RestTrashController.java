package com.sds.cmsapp.document.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.common.Pager;
import com.sds.cmsapp.exception.TrashException;
import com.sds.cmsapp.model.document.DocumentVersionService;
import com.sds.cmsapp.model.trash.TrashService;

@RestController
public class RestTrashController {
	
	@Autowired
	Pager pager;
	
	@Autowired
	TrashService trashService;
	
	@Autowired
	DocumentVersionService documentVersionService;
	
	@PostMapping("/document/trash/test2")
	public ResponseEntity restoreTrash(@RequestParam("trash_idxList") List<Integer> trash_idxList) {
		System.out.println("복원메서드 연결");
		System.out.println("복원 대상" + trash_idxList);
		trash_idxList.forEach(trashService :: restore);
		ResponseEntity entity = ResponseEntity.ok("복원 성공");
		System.out.println("RestTrashController 복원메서드 호출");
		return entity;
	}
	
	@DeleteMapping("/document/trash/test1")
	public ResponseEntity deleteTrash(@RequestParam("trash_idxList") List<Integer> trash_idxList) {
		System.out.println("삭제메서드 연결");
		System.out.println("삭제 대상" + trash_idxList);
		trash_idxList.forEach(trashService :: delete);
		ResponseEntity entity = ResponseEntity.ok("삭제 성공");
		System.out.println("RestTrashController 삭제 메서드 호출");

		return entity;
	}
	
	@DeleteMapping("/document/trash/list")
	public ResponseEntity emptyTrash() {
		trashService.deleteAll();
		ResponseEntity entity = ResponseEntity.ok("휴지통 비우기 성공");
		System.out.println("RestTrashController 비우기메서드 호출");

		return entity;
	}
	
	@ExceptionHandler(TrashException.class)
	public ResponseEntity handle(TrashException e) {
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;
	}
	
}
