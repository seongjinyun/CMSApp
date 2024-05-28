package com.sds.cmsapp.setting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.cmsapp.domain.Owner;
import com.sds.cmsapp.exception.OwnerException;
import com.sds.cmsapp.model.emp.OwnerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestSettingsController {	
		
	@Autowired
	private OwnerService ownerService;
	
	@PostMapping("/settings/owner/update")
	public ResponseEntity regist(Owner owner) {
		
		log.debug("id: "+owner.getOwner_id());
		log.debug("pw: "+owner.getOwner_pw());
		
		ownerService.update(owner);
		ResponseEntity entity = ResponseEntity.ok("mysql 수정 성공");
		return entity;
		//return "redirect:/settings/general";
	}
	
//	@PostMapping("/settings/owner/update")
//	public String update(Owner owner) {
//		ownerService.update(owner);
//		return "redirect:/settings/general";
//	}
	
	@ExceptionHandler(OwnerException.class)
	public ResponseEntity handle(OwnerException e) {
		ResponseEntity entity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return entity;
	}
}
