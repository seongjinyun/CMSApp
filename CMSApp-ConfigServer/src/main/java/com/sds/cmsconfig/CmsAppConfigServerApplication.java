package com.sds.cmsconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CmsAppConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsAppConfigServerApplication.class, args);
	}

}
