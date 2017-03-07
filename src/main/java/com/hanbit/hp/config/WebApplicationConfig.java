package com.hanbit.hp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
// 웹스톰의 CSS와 JS를 연동하기 위한 장치
public class WebApplicationConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
		.addResourceLocations("file:/hanbit/webstormpjt/web/dist/");
	}
	
	// src/main/resources > META-INF>resources 경로에 있는 건 기본적으로 읽어옴
}
