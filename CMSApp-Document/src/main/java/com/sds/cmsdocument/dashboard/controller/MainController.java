package com.sds.cmsdocument.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value="dashboardMainController")
public class MainController {

	@GetMapping("/dashboard")
	public String getMain() {
		return "dashboard/activity";
	}
	
}