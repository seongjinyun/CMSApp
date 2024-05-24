package com.sds.cmsapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditorDashboardController {

	@GetMapping("/editor/dashboard")
	public String getEditorDashboard() {
		return "editor/dashboard";
	}
}
