package com.group3.projectmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProjectManagementApiApplication {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry corsRegistry) {
				corsRegistry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "POST", "DELETE", "PUT")
						.maxAge(3600);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApiApplication.class, args);
	}

}
