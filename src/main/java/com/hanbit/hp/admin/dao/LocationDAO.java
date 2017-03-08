package com.hanbit.hp.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List selectList() {
		return sqlSession.selectList("admin.location.selectList");
	}
	
	public Map selectOne(String locationId) {
		return sqlSession.selectOne("admin.location.selectOne", locationId);
	}
	
	public int update(String locationId, String locationName) {
		Map param = new HashMap();
		param.put("locationId", locationId);
		param.put("locationName", locationName);
		
		return sqlSession.insert("admin.location.update", param);
	}
	
	public int delete(String locationId) {
		return sqlSession.insert("admin.location.delete", locationId);
	}
	
	public int insert(String locationId, String locationName) {
		Map param = new HashMap();
		param.put("locationId", locationId);
		param.put("locationName", locationName);
		
		return sqlSession.insert("admin.location.insert", param);
	}
}
