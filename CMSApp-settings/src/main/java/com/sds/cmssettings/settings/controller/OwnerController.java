package com.sds.cmssettings.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.sds.cmssettings.domain.Owner;
import com.sds.cmssettings.model.owner.OwnerService;

@Controller
public class OwnerController {	
	
	@Autowired
	private OwnerService ownerService;
	
	@PostMapping("/settings/owner/update")
	public String updateOwner(Owner owner) {	
		ownerService.update(owner);
		return "redirect:/settings/general";
	}
}
