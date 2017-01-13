package com.hanbit.hp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.hp.dao.ToplistDAO;

@Service
public class ToplistService {

	@Autowired
	private ToplistDAO toplistDAO;
	
	public List<Map<String, Object>> getAll() {
		return toplistDAO.selectAll();
	}
}
