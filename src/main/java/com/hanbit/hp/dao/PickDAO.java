package com.hanbit.hp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PickDAO {

	@Autowired
	private SqlSession sqlsession;
	
	public List<Map<String, Object>> selectPick(Integer count) {
		return sqlsession.selectList("pick.selectPick", count);
	}
	
	
}
