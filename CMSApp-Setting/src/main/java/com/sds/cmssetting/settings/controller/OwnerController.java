package com.sds.cmssetting.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.cmssetting.domain.Owner;
import com.sds.cmssetting.model.owner.OwnerService;

@Controller
public class OwnerController {	
	
	@Autowired
	private OwnerService ownerService;
	
	@PostMapping("/setting/owner/update")
	public String updateOwner(Owner owner) {	
		ownerService.update(owner);
		return "redirect:/setting/general";
	}
}
