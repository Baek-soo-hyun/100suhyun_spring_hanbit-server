package com.hanbit.hp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.hp.service.MainService;
import com.hanbit.hp.service.PickService;
import com.hanbit.hp.service.ToplistService;

@RestController
public class MainController {

	@Autowired
	private MainService mainService;
	
	@Autowired
	private ToplistService toplistService;
	
	@Autowired
	private PickService pickService; 
	
	
	@RequestMapping("/api2/main/imgs")
	public List<String> getMainImgs() {
		return mainService.getMainImgs();
	}
	
	@RequestMapping("/api2/main/section/{sectionCode}/items")
	public List<Map<String, Object>> getSectionItems(
			@PathVariable("sectionCode") String sectionCode) {
		if ("01".equals(sectionCode)) {
			return toplistService.getAll();	
		}
		else if("02".equals(sectionCode)) {
			return pickService.getPicks(6);
		}
		return new ArrayList<>();
	}
}
