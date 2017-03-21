package com.hanbit.hp.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List selectList(int page, int rowsPerPage) {
		Map param = new HashMap();
		param.put("firstIndex", (page - 1) * rowsPerPage);
		param.put("rowsPerPage", rowsPerPage);
		
		return sqlSession.selectList("admin.store.selectList", param);
	}
	
	public int count() {
		return sqlSession.selectOne("admin.store.count");
	}
	
	public Map selectOne(String storeId) {
		return sqlSession.selectOne("admin.store.selectOne", storeId);
	}
	
	public int update(String storeId, String storeName,
			String categoryId, String locationId) {
		
		Map param = new HashMap();
		param.put("storeId", storeId);
		param.put("storeName", storeName);
		param.put("categoryId", categoryId);
		param.put("locationId", locationId);
		
		return sqlSession.insert("admin.store.update", param);
	}
	
	public int updateDetail(Map storeDetail) {
		return sqlSession.update("admin.store.updateDetail", storeDetail);
	}
	
	public int delete(String storeId) {
		return sqlSession.delete("admin.store.delete", storeId);
	}
	
	public int deleteDetail(String storeId) {
		return sqlSession.delete("admin.store.deleteDetail", storeId);
	}
	
	public int insert(String storeId, String storeName,
			String storeImg, String categoryId, String locationId) {
		
		Map param = new HashMap();
		param.put("storeId", storeId);
		param.put("storeName", storeName);
		param.put("storeImg", storeImg);
		param.put("categoryId", categoryId);
		param.put("locationId", locationId);
		
		return sqlSession.insert("admin.store.insert", param);
	}
	
	public int insertDetail(Map storeDetail) {
		return sqlSession.insert("admin.store.insertDetail", storeDetail);
	}
	
}
