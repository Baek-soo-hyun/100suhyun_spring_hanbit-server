package com.hanbit.hp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.hp.service.StoreService;

@RestController("clientStoreController")
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@RequestMapping("/api2/store/search")
	public Map search(@RequestParam("keyword") String keyword,
			@RequestParam("page") int page,
			@RequestParam("rowsPerPage") int rowsPerPage) {
		
		int count = storeService.countSearch(keyword);
		List list = null;
		
		if (count > 0) {
			list = storeService.search(keyword, page, rowsPerPage);
		}
		else {
			list = new ArrayList();
		}
		
		Map result = new HashMap();
		result.put("count", count);
		result.put("list", list);
		
		return result;
	}
	
}




