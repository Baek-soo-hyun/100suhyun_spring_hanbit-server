package com.hanbit.hp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.hp.dao.MemberDAO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO memberDAO;
		
	private static String generateKey(String prefix) {
		String key = prefix + StringUtils.leftPad(
				String.valueOf(System.nanoTime()), 30, "0");
		
		key += StringUtils.leftPad(
				String.valueOf((int) (Math.random() * 1000) % 100), 2, "0");
		
		return key;
	}
	
	public String addMember(String userId, String userPw) {
		String uid = generateKey("UID");
		
		memberDAO.insertMember(uid, userId, userPw);
		
		return uid;		
	}	
}

