package com.sds.cmsapp.settings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletContext;

@RestController
public class RestKeyController {
	
	@Autowired
	private ServletContext servletContext;
	
	@GetMapping("/jwt/key")
	public String getKey() {
		return (String)servletContext.getAttribute("key");
	}
	
}