package com.hanbit.hp.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanbit.hp.admin.controller.StoreController;
import com.hanbit.hp.service.FileService;

@Controller
public class FileController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileService fileService;
	
	// HTML에서 <img>태그가 src로 이미지를 요청할 때
	// store.js에서 add할 때, 파일 선택하면 img 구현
	@RequestMapping("/api2/file/{fileId}")
	public void getFile(@PathVariable("fileId") String fileId,
			HttpServletResponse response) throws Exception {
		
		// DB에 저장된 src정보(/api2/file/store_id)를 받아서 알아낸 store_id로
		// PC에 저장된 Path(/hanbit/upload/) 동일한 store_id의 파일을 맵핑
		String filePath = FileService.PATH_PREFIX + fileId;
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		
		//DB에 있는 파일 정보(리스트) 가져오기
		Map fileInfo = fileService.get(fileId);
				
		response.setContentType((String) fileInfo.get("file_type"));
		response.setContentLengthLong((Long) fileInfo.get("file_size"));

		// buffer에 fis를 전달해서 BufferedInputStream 통로 타입의 bis 객체를 생성
		BufferedInputStream bis = IOUtils.buffer(fis);

		// 메모리 용량보다 더 큰 파일크기를 전달할 수 없으니, 조금씩 넘겨주는 방식으로 처리해야 한다.
		// 4096byte만큼 계속 읽으면서 쏴주는 것
		// 4096byte보다 작게 남으면 작은 것들을 쏴줘라.
		// available() : an estimate of the number of bytes that can be read
		// read() : Reads the next byte of data from the input stream
		while (bis.available() > 0) {
			byte[] buffer = new byte[4096];
			// length : 얼만큼 읽었는지 체크
			// read(buffer) : buffer라는 그릇은 read에 줌
			int length = bis.read(buffer);
			
			// write(buffer, 0, length) : buffer라는 그릇의 0부터 length까지 전달
			response.getOutputStream().write(buffer, 0, length);
			
		}
		
		bis.close();
		fis.close();
		
		// stream을 통해 보내기(전송)
		response.flushBuffer();
		
	}
	
}
