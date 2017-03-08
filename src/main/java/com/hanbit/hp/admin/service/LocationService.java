package com.hanbit.hp.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.hp.admin.dao.LocationDAO;
import com.hanbit.hp.util.KeyUtils;

@Service
public class LocationService {
	
	@Autowired
	private LocationDAO locationDAO;
	
	public List getList() {
		return locationDAO.selectList();
	}
	
	public Map get(String locationId) {
		return locationDAO.selectOne(locationId);
	}
	
	public int modify(String locationId, String locationName) {
		return locationDAO.update(locationId, locationName);
	}	
	
	public int remove(String locationId) {
		return locationDAO.delete(locationId);
	}
	
	public int add(String locationName) {
		String locationId = KeyUtils.generateKey("LOC");
		return locationDAO.insert(locationId, locationName);
	}	
}
