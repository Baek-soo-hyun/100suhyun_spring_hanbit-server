package com.hanbit.hp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hanbit.hp.dao.MemberDAO;

@Service
public class MemberService {
	
	private static final String SECRET_KEY = "hanbit1111";
	private PasswordEncoder passwordEncoder = new StandardPasswordEncoder(SECRET_KEY);
	
	@Autowired
	private MemberDAO memberDAO;
		
	private static String generateKey(String prefix) {
		String key = prefix + StringUtils.leftPad(
				String.valueOf(System.nanoTime()), 30, "0");
		
		key += StringUtils.leftPad(
				String.valueOf((int) (Math.random() * 1000) % 100), 2, "0");
		
		return key;
	}
	
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new StandardPasswordEncoder("hanbit");
		
		String passwd1 = passwordEncoder.encode("1234");
		
		PasswordEncoder passwordEncoder2 = new StandardPasswordEncoder();
		
		String passwd2 = passwordEncoder.encode("1234");
		
		
		System.out.println(passwordEncoder2.matches("1234", passwd2));
	}
	
	public String addMember(String userId, String userPw) {
		String uid = generateKey("UID");
		String encryptedUserPw = passwordEncoder.encode(userPw);
		
		memberDAO.insertMember(uid, userId, encryptedUserPw);
		
		return uid;		
	}	
}

