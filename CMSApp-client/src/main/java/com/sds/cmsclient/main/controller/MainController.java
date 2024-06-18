package com.sds.cmsclient.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.cmsclient.domain.Project;
import com.sds.cmsclient.model.main.MainDAO;
import com.sds.cmsclient.model.main.MainService;


@Controller
public class MainController {

	@Autowired
	private MainService mainService;
	
	@GetMapping("/main")
	public String getMain(Model model) {
		List<Project> project = mainService.projcetAllSelect();
		model.addAttribute("project", project);
		return "main/main";
	}
}
