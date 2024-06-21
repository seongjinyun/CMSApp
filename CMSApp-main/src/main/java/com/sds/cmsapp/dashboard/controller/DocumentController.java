package com.sds.cmsapp.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value="dashboardDocumentController")
public class DocumentController {

	@GetMapping("/admin/dashboard/activity")
	public String getAdminDashboardActivity() {
		return "admin/dashboard/activity";
	}
	
	@GetMapping("/admin/dashboard/publishing")
	public String getAdminDashboardPublishing() {
		return "admin/dashboard/publishing";
	}
	
//	@GetMapping("/admin/dashboard/published-edition")
//	public String getAdminDashboardPublishedEdition() {
//		return "admin/dashboard/published";
//	}
	
}
