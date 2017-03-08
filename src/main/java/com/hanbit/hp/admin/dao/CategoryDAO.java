package com.hanbit.hp.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List selectList() {
		return sqlSession.selectList("admin.category.selectList");
	}
	
	public Map selectOne(String categoryId) {
		return sqlSession.selectOne("admin.category.selectOne", categoryId);
	}
	
	public int update(String categoryId, String categoryName) {
		Map param = new HashMap();
		param.put("categoryId", categoryId);
		param.put("categoryName", categoryName);
		
		return sqlSession.insert("admin.category.update", param);
	}
	
	public int delete(String categoryId) {
		return sqlSession.insert("admin.category.delete", categoryId);
	}
	
	public int insert(String categoryId, String categoryName) {
		Map param = new HashMap();
		param.put("categoryId", categoryId);
		param.put("categoryName", categoryName);
		
		return sqlSession.insert("admin.category.insert", param);
	}
}
