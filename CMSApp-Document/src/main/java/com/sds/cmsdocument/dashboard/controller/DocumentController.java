package com.sds.cmsdocument.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value="dashboardDocumentController")
public class DocumentController {

	@GetMapping("/admin/dashboard/activity")
	public String getAdminDashboardActivity() {
		return "dashboard/activity";
	}
	
	@GetMapping("/admin/dashboard/publishing")
	public String getAdminDashboardPublishing() {
		return "dashboard/publishing";
	}
	
}
