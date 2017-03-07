package com.hanbit.hp.util;

import org.apache.commons.lang3.StringUtils;

public class KeyUtils {
	
	public static String generateKey(String prefix) {
		String key = prefix + StringUtils.leftPad(
				String.valueOf(System.nanoTime()), 30, "0");
		
		key += StringUtils.leftPad(
				String.valueOf((int) (Math.random() * 1000) % 100), 2, "0");
		
		return key;
	}
}
