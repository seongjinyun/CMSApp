package com.sds.cmsapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

	@GetMapping("/admin/dashboard")
	public String getAdminDashboard() {
		return "admin/dashboard";
	}
}