package com.hanbit.hp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);
	//slf4j 인터페이스에 있는 걸로 import
	//사용하는 모든 클래스 위에 클래스명만 바꿔서 이렇게 써주면 로거를 사용할 수 있다.  
	
	
	
	@RequestMapping("/")
	@ResponseBody
	public Map welcome() {
		Map welcome = new HashMap();
		welcome.put("msg", "Hello, Hanbit Plate");
		
		LOGGER.trace("First Log");
		LOGGER.debug("First Log");
		LOGGER.info("First Log");
		LOGGER.warn("First Log");
		LOGGER.error("First Log");
		
		return welcome;
	}	
	
	
	@RequestMapping("/form") 
	public String form() {
		return "hello";
	}


	@RequestMapping("/api2/calc") 
	@ResponseBody
	public Map calculate (@RequestParam(name="operator", required=false) String operator,
			@RequestParam(name="left", required=false) String leftStr,
			@RequestParam(name="right", required=false) String rightStr)	{
		
		int left = 0;
		int right = 0;
		int result = 0;
		
		try {
			left = Integer.valueOf(leftStr);
			right = Integer.valueOf(rightStr);
		}		
		catch (Exception e) {
	
		}
		if("plus".equals(operator)) {
			result = left + right;
		}
		else if("minus".equals(operator)) {
			result = left - right;
		}		
		
		Map map = new HashMap();
		map.put("result", result);
		
		return map;
	}
	
	
	/*@RequestMapping("/form") 
	@ResponseBody
	public String form (Model model, 
			@RequestParam(name="operator", required=false) String operator,
			@RequestParam(name="left", required=false) String leftStr,
			@RequestParam(name="right", required=false) String rightStr)	{
		
		int left = 0;
		int right = 0;
		int result = 0;
		
		try {
			left = Integer.valueOf(leftStr);
			right = Integer.valueOf(rightStr);
		}		
		catch (Exception e) {
	
		}
		if("plus".equals(operator)) {
			result = left + right;
		}
		else if("minus".equals(operator)) {
			result = left - right;
		}		
		
		model.addAttribute("result", result);
		
		return "hello";
	}*/
	
	
	@RequestMapping("/hanbit") //표현할 url 파라미터를 써준다.
	public String hanbit() {
		/*model.addAttribute("name", "한빛");  html 파일에서 {{name}} 부분에 "한빛"을 넣어라.*/
		return "sub/hanbit"; //html 파일의 경로를 상대 경로로 표현하면 된다.
	}
	// HTML 템플릿명으로 뒤에 .html 없이 url을 표현하고 싶을 때 이 방식을 사용하면 된다.
	
	
	@RequestMapping("/api2/hello")
	@ResponseBody
	public List api() {
		List list = new ArrayList();
		list.add("Hanbit");
		list.add("Plate");
		list.add("API");
		
		return list;
	}
	
}
