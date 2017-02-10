package com.hanbit.hp.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hanbit.hp.controller.WelcomeController;
import com.hanbit.hp.dao.MemberDAO;

@Service
public class MemberService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

	private static final String SECRET_KEY = "hanbit";
	private PasswordEncoder passwordEncoder = new StandardPasswordEncoder(SECRET_KEY);
	
	@Autowired
	private MemberDAO memberDAO;
	
	private String generateKey(String prefix) {
		String key = prefix + StringUtils.leftPad(
				String.valueOf(System.nanoTime()), 30, "0");
		
		key += StringUtils.leftPad(
				String.valueOf((int) (Math.random() * 1000) % 100), 2, "0");
		
		return key;
	}
	
	public String addMember(String userId, String userPw) {
		String uid = generateKey("UID");
		String encryptedUserPw = passwordEncoder.encode(userPw); 
		//springframework.security 플러그인의 passwordEncoder API의 encode() 함수를 사용해서 자동으로 비밀번호를 암호화함
		//나중에 raw 비번이 암호화된 비번이랑 같은지 확인하는 건 	matches(CharSequence rawPassword, String encodedPassword)함수를 사용하면 된다. (리턴타입 : boolean)
		
		memberDAO.insertMember(uid, userId, encryptedUserPw);
		
		return uid;
	}

	public boolean isValidMember(String userId, String userPw) {
		String encryptedUserPw = memberDAO.selectUserPw(userId);
		
		return passwordEncoder.matches(userPw, encryptedUserPw);
	}

	public void modifyMember(String uid, String userPw) {
		String encryptedUserPw = passwordEncoder.encode(userPw);
		
		memberDAO.updateMember(uid, encryptedUserPw);
	}

	public String getUid(String userId) {
		return memberDAO.selectUid(userId);
	}
	
}

