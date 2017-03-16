package com.hanbit.hp.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(String fileId, String fileType, long fileSize, String fileName) {
		Map param = new HashMap();
		param.put("fileId", fileId);
		param.put("fileType", fileType);
		param.put("fileSize", fileSize);
		param.put("fileName", fileName);
		
		return sqlSession.insert("file.insert", param);
	}
	
	public Map selectOne(String fileId) {
		return sqlSession.selectOne("file.selectOne", fileId);
	}
	
	public int delete(String fileId) {
		return sqlSession.delete("file.delete", fileId);
	}
	
}
