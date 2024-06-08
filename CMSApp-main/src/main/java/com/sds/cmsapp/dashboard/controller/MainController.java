package com.sds.cmsapp.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value="dashboardMainController")
public class MainController {

	@GetMapping("/admin/dashboard/activity")
	public String getAdminDashboardActivity() {
		return "admin/dashboard/activity";
	}
	
	@GetMapping("/admin/dashboard/publishing")
	public String getAdminDashboardPublishing() {
		return "admin/dashboard/publishing";
	}
	
	@GetMapping("/admin/dashboard/kanban")
	public String getAdminDashboardKanban() {
		return "admin/dashboard/kanban";
	}
	
	@GetMapping("/admin/dashboard/analytics")
	public String getAdminDashboardAnalytics() {
		return "admin/dashboard/analytics";
	}
	
	@GetMapping("/writer/dashboard/activity")
	public String getWriterDashboardActivity() {
		return "writer/dashboard/activity";
	}
	
}