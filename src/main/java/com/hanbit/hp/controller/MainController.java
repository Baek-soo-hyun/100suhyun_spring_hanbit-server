package com.hanbit.hp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.hp.service.JsonFileService;
import com.hanbit.hp.service.MainService;
import com.hanbit.hp.service.PickService;
import com.hanbit.hp.service.StoreService;
import com.hanbit.hp.service.ToplistService;

@RestController
public class MainController {
	
	@Autowired
	private MainService mainService;
	
	@Autowired
	private ToplistService toplistService;
	
	@Autowired
	private PickService pickService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private JsonFileService jsonFileService;
	
	@RequestMapping("/api2/main/imgs")
	public List<String> getMainImgs() {
		return mainService.getMainImgs();
	}
	
	@RequestMapping("/api2/main/section/{sectionCode}/items")
	public List<Map<String, Object>> getSectionItems(
			@PathVariable("sectionCode") String sectionCode) throws Exception {
		
		if ("01".equals(sectionCode)) {
			return toplistService.getAll();
		}
		else if ("02".equals(sectionCode)) {
			return pickService.getPicks(6);
		}
		
		// spring의 Json파일을 자바 파일로 변환하여 다시 Json형식으로 return 해주는 것 
		else if ("03".equals(sectionCode) || "04".equals(sectionCode) || "05".equals(sectionCode)) {
			return storeService.getEight();
			// List.class => List에 쩜 찍고 class 쓰면 그 오브젝트이 타입이 나온다. 
		}
		return new ArrayList<>();
	}
	
	@RequestMapping("/api2/main/section/05/categories")
	public List getSectionCategories() throws Exception {
		String filePath = "json/section05.categories.json";
		
		return jsonFileService.getJsonFile(filePath, List.class);
	}
	
	@RequestMapping("/api2/common/hotplaces")
	public List getHotPlaces() throws Exception {
		String filePath = "json/common.hotplaces.json";
		
		return jsonFileService.getJsonFile(filePath, List.class);
	}
	
}