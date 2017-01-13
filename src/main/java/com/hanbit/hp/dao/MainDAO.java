package com.hanbit.hp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MainDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List selectMainImgs() {
		return sqlSession.selectList("main.imgs");
	}

}
