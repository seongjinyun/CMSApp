package com.sds.cmsdocument.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsdocument.common.Pager;
import com.sds.cmsdocument.exception.TrashException;
import com.sds.cmsdocument.model.document.DocumentVersionService;
import com.sds.cmsdocument.model.trash.TrashService;

@RestController
public class RestTrashController {
	
	@Autowired
	Pager pager;
	
	@Autowired
	TrashService trashService;
	
	@Autowired
	DocumentVersionService documentVersionService;
	
	@PostMapping("/document/trash")
	public ResponseEntity<String> restoreTrash(@RequestBody final List<Integer> trashIdxList) {
		System.out.println("복원메서드 연결");
		System.out.println("복원 대상" + trashIdxList);
		trashIdxList.forEach(trashService :: restore);
		ResponseEntity<String> entity = ResponseEntity.ok("복원 성공");
		System.out.println("RestTrashController 복원메서드 호출");
		return entity;
	}
	
	@DeleteMapping("/document/trash")
	public ResponseEntity<String> deleteTrash(@RequestBody final List<Integer> trashIdxList) {
		System.out.println("삭제메서드 연결");
		System.out.println("삭제 대상" + trashIdxList);
		trashIdxList.forEach(trashService :: delete);
		ResponseEntity<String> entity = ResponseEntity.ok("삭제 성공");
		System.out.println("RestTrashController 삭제 메서드 호출");

		return entity;
	}
	
	@DeleteMapping("/document/trash/list")
	public ResponseEntity<String> emptyTrash() {
		trashService.deleteAll();
		ResponseEntity<String> entity = ResponseEntity.ok("휴지통 비우기 성공");
		System.out.println("RestTrashController 비우기메서드 호출");

		return entity;
	}
	
	@ExceptionHandler(TrashException.class)
	public ResponseEntity<String> handle(TrashException e) {
		ResponseEntity<String> entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Trash 처리 중 오류가 발생했습니다.");
		return entity;
	}
	
}
