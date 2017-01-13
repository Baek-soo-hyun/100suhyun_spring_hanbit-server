package com.hanbit.hp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

	
	@RequestMapping("/")
	@ResponseBody
	public Map welcome() {
		Map welcome = new HashMap();
		welcome.put("msg", "Hello, Hanbit Plate");
		
		return welcome;
	}
	
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
