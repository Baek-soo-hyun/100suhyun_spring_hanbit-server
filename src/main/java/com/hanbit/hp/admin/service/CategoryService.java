package com.hanbit.hp.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.hp.admin.dao.CategoryDAO;
import com.hanbit.hp.util.KeyUtils;

@Service
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	public List getList() {
		return categoryDAO.selectList();
	}
	
	public Map get(String categoryId) {
		return categoryDAO.selectOne(categoryId);
	}
	
	public int modify(String categoryId, String categoryName) {
		return categoryDAO.update(categoryId, categoryName);
	}	
	
	public int remove(String categoryId) {
		return categoryDAO.delete(categoryId);
	}
	
	public int add(String categoryName) {
		String categoryId = KeyUtils.generateKey("CAT");
		return categoryDAO.insert(categoryId, categoryName);
	}	
}
