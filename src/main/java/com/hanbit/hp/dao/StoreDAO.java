package com.hanbit.hp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("clientStoreDAO")
public class StoreDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List selectList(int page, int rowsPerPage) {
		Map param = new HashMap();
		param.put("firstIndex", (page - 1) * rowsPerPage);
		param.put("rowsPerPage", rowsPerPage);
		
		return sqlSession.selectList("store.selectList", param);
	}
	
	public int countSearch(String keyword) {
		return sqlSession.selectOne("store.countSearch", keyword);
	}
	
	public List selectSearch(String keyword, int page, int rowsPerPage) {
		Map param = new HashMap();
		param.put("keyword", keyword);
		param.put("firstIndex", (page - 1) * rowsPerPage);
		param.put("rowsPerPage", rowsPerPage);
		
		return sqlSession.selectList("store.selectSearch", param);
	}
	
}
