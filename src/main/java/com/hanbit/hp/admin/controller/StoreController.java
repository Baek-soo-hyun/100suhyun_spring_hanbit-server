package com.hanbit.hp.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hanbit.hp.admin.service.StoreService;

@RestController
@RequestMapping("/admin/api/store")
public class StoreController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired
	private StoreService storeService;
	
	@RequestMapping("/list")
	public Map getList(@RequestParam("page") int page,
			@RequestParam("rowsPerPage") int rowsPerPage) {
		
		List list = storeService.getList(page, rowsPerPage);
		int count = storeService.count();
		
		Map result = new HashMap();
		result.put("list", list);
		result.put("count", count);
		
		return result;
	}
	
	@RequestMapping(value="/{storeId}", method=RequestMethod.GET)
	public Map get(@PathVariable("storeId") String storeId) {
		return storeService.get(storeId);
	}
	
	@RequestMapping(value="/{storeId}", method=RequestMethod.POST)
	public Map modify(@PathVariable("storeId") String storeId,
			MultipartHttpServletRequest request) {
		
		String storeName = request.getParameter("storeName");
		String branchName = request.getParameter("branchName");
		String categoryId = request.getParameter("categoryId");
		String locationId = request.getParameter("locationId");
		String storeAddr = request.getParameter("storeAddr");
		float storeLat = Float.parseFloat(request.getParameter("storeLat"));
		float storeLng = Float.parseFloat(request.getParameter("storeLng"));
		String storeTel = request.getParameter("storeTel");
		String storePrice = request.getParameter("storePrice");
		String storeParking = request.getParameter("storeParking");
		String storeTime = request.getParameter("storeTime");
		String storeBreak = request.getParameter("storeBreak");
		String storeHoliday = request.getParameter("storeHoliday");
		String storeWebsite = request.getParameter("storeWebsite");
		
		Map storeDetail = new HashMap();
		storeDetail.put("storeId", storeId);
		storeDetail.put("branchName", branchName);
		storeDetail.put("storeAddr", storeAddr);
		storeDetail.put("storeLat", storeLat);
		storeDetail.put("storeLng", storeLng);
		storeDetail.put("storeTel", storeTel);
		storeDetail.put("storePrice", storePrice);
		storeDetail.put("storeParking", storeParking);
		storeDetail.put("storeTime", storeTime);
		storeDetail.put("storeBreak", storeBreak);
		storeDetail.put("storeHoliday", storeHoliday);
		storeDetail.put("storeWebsite", storeWebsite);
		
		MultipartFile storeImgFile = request.getFile("storeImg");
		
		storeService.modify(storeId, storeName, categoryId, locationId, storeDetail,
				storeImgFile);
		
		Map result = new HashMap();
		result.put("result", "ok");
		
		return result;
	}
	
	@RequestMapping(value="/{storeId}", method=RequestMethod.DELETE)
	public Map remove(@PathVariable("storeId") String storeId) {
		
		storeService.remove(storeId);
		
		Map result = new HashMap();
		result.put("result", "ok");
		
		return result;
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Map add(MultipartHttpServletRequest request) {
		String storeName = request.getParameter("storeName");
		String branchName = request.getParameter("branchName");
		String categoryId = request.getParameter("categoryId");
		String locationId = request.getParameter("locationId");
		String storeAddr = request.getParameter("storeAddr");
		float storeLat = Float.parseFloat(request.getParameter("storeLat"));
		float storeLng = Float.parseFloat(request.getParameter("storeLng"));
		String storeTel = request.getParameter("storeTel");
		String storePrice = request.getParameter("storePrice");
		String storeParking = request.getParameter("storeParking");
		String storeTime = request.getParameter("storeTime");
		String storeBreak = request.getParameter("storeBreak");
		String storeHoliday = request.getParameter("storeHoliday");
		String storeWebsite = request.getParameter("storeWebsite");
		
		Map storeDetail = new HashMap();
		storeDetail.put("branchName", branchName);
		storeDetail.put("storeAddr", storeAddr);
		storeDetail.put("storeLat", storeLat);
		storeDetail.put("storeLng", storeLng);
		storeDetail.put("storeTel", storeTel);
		storeDetail.put("storePrice", storePrice);
		storeDetail.put("storeParking", storeParking);
		storeDetail.put("storeTime", storeTime);
		storeDetail.put("storeBreak", storeBreak);
		storeDetail.put("storeHoliday", storeHoliday);
		storeDetail.put("storeWebsite", storeWebsite);
		
		MultipartFile storeImgFile = request.getFile("storeImg");
		
		storeService.add(storeName, categoryId, locationId, storeDetail, 
				storeImgFile);
		
		Map result = new HashMap();
		result.put("result", "ok");
		
		return result;
	}
}