package com.hanbit.hp.admin.controller;

import java.net.URI;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller

// preFixed 되는 path이고
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	public RestTemplate restTemplate;
	// Spring 컴포넌트 내부에서 GET, POST, PUT, PATCH, DELETE REST 요청을 처리하는 방법 //
	// rest 형식의 응답이 오면 자바에서 처리할 수 있게 해줌 //

	// preFixed 된 path 뒤에 다음 path명이 붙으면 아래 함수가 실행됨
	// model : 뷰에 전달되는 모델 데이터
	@RequestMapping("")
	public String main(Model model) {
		model.addAttribute("cssName", "main");
		model.addAttribute("jsName", "main");
		return "admin/index";
	}
	
	@RequestMapping("/{menuId}")
	public String main(Model model, @PathVariable("menuId") String menuId) {
		model.addAttribute("menuId", menuId);
		
		String cssName = "main";
		
		if ("store".equals(menuId)) {
			cssName = menuId;
		}
		
		model.addAttribute("cssName", cssName);
		model.addAttribute("jsName", menuId);
		return "admin/" + menuId;
	}
	
	//주소 검색
	@RequestMapping("/juso")
	public String juso(HttpServletRequest request, Model model) {
		Enumeration<String> paramNames = request.getParameterNames();
		
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			
			LOGGER.debug(paramName + " : " + paramValue);
			
			// 클라이언트에 넘기기위해 model에 담아서 정보들을 모두 넘김
			model.addAttribute(paramName, paramValue);
			
			if ("roadAddrPart1".equals(paramName)) {
				String url = "https://maps.googleapis.com/maps/api/geocode/json";
				// 본인 키
				String key = "AIzaSyBMsKoO3fXt5FIiSZikmyWuLrHz2-LSqfA";
				
				// URL을 생성한다.
				// fromHttpUrl => 베이스 URL
				URI uri = UriComponentsBuilder.fromHttpUrl(url)
						.queryParam("address", paramValue)
						.queryParam("key", key)
						.build()
						.toUri();
				
				String geoInfo = restTemplate.getForObject(uri, String.class);
				// String.class => 받는 형식
				
				model.addAttribute("geoInfo", geoInfo);
			
			}
		}
		
		return "admin/juso";
	}
}
