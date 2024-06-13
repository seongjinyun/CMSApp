package com.sds.cmsapp.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value="dashboardMainController")
public class MainController {

	@GetMapping("/admin/dashboard/activity")
	public String getAdminDashboardActivity() {
		return "admin/dashboard/activity";
	}
	
	@GetMapping("/admin/dashboard/publish/new")
	public String getAdminDashboardPublishing() {
		return "admin/dashboard/publish/new";
	}
	
//	@GetMapping("/admin/dashboard/publish/history")
//	public String getAdminDashboardPublishedHistory() {
//		return "admin/dashboard/publish/history";
//	}
	
//	@GetMapping("/admin/dashboard/analytics")
//	public String getAdminDashboardAnalytics() {
//		return "admin/dashboard/analytics";
//	}
	
	@GetMapping("/writer/dashboard/activity")
	public String getWriterDashboardActivity() {
		return "writer/dashboard/activity";
	}
	
}