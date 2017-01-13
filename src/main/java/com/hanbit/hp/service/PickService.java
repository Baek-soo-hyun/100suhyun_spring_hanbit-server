package com.hanbit.hp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.hp.dao.PickDAO;

@Service
public class PickService {

	@Autowired
	private PickDAO pickDAO;
	
	public List<Map<String, Object>> getPicks(int count) {
		return pickDAO.selectPick(count);
	}
}
