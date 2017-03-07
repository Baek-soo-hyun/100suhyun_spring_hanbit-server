package com.hanbit.hp.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonFileService {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	// 파일명을 받아서 파일을 읽고 해당 파일의 타입으로 반환해주는 메서드
	// 밖에서 클래스 타입을 받아와서 그 타입으로 리턴해주는 것
	// <T> 와 class<T>가 같기만 하면 된다. (꼭 T가 아니어도 된다.)
	public <T> T getJsonFile(String filePath, Class<T> classType) throws Exception {
		
		// getClass().getClassLoader() => 클래스 패스에 있는 어떤 파일을 불러 오기 위해 사용
		// Stream : 연속된 무언가(=data 통로)
		// input: 프로그램 입장에서는 받아들이는 것(입력)
		// inputstream : 파일을 읽을 수 있는 통로를 만드는 것
		// outputstream : 파일의 데이터를 쓸 수 있는 통로를 만드는 것
		// getResourceAsStream(filePath) : 해당 filepath에서 리소스(데이터)를 스트림(통로)를 통해 받아온다.
		// byte로 전달받는다.
		InputStream jsonStream
			= getClass().getClassLoader().getResourceAsStream(filePath);
		
		return mapper.readValue(jsonStream, classType);
	}
}
