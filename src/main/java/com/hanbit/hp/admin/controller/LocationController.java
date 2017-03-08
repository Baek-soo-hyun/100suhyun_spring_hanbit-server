package com.hanbit.hp.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.hp.admin.service.LocationService;

@RestController
@RequestMapping("/admin/api/location")
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	@RequestMapping("/list")
	public List list() {
		return locationService.getList();
	}
	

	@RequestMapping("/add")
	public Map add(@RequestParam("locationName") String locationName) {
		locationService.add(locationName);
		
		Map result = new HashMap();
		result.put(result, "ok");
	
		return result;
	}
	
	@RequestMapping(value="/{locationId}", method=RequestMethod.GET)
	public Map get(@PathVariable("locationId") String locationId) {
		
		return locationService.get(locationId);
	}
	
	
	@RequestMapping(value="/{locationId}", method=RequestMethod.PUT)
	public Map modify(@PathVariable("locationId") String locationId,
			@RequestParam("locationName") String locationName) {
		
		locationService.modify(locationId, locationName);
		
		Map result = new HashMap();
		result.put(result, "ok");
	
		return result;
	}
	
	@RequestMapping(value="/{locationId}", method=RequestMethod.DELETE)
	public Map remove(@PathVariable("locationId") String locationId) {
		
		locationService.remove(locationId);
		
		Map result = new HashMap();
		result.put(result, "ok");
	
		return result;
	}
}
