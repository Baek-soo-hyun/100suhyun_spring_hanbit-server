package com.hanbit.hp.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanbit.hp.service.FileService;

@Controller
public class FileController {
	

	
	@Autowired
	private FileService fileService;

	@RequestMapping("/api2/file/{fileId}")
	public void getFile(@PathVariable("fileId") String fileId,
			HttpServletResponse response) throws Exception {
		
		String filePath = fileService.PATH_PREFIX + fileId;
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		
		Map fileInfo = fileService.get(fileId);
		
		response.setContentType((String) fileInfo.get("image/jpeg"));
		response.setContentLength(107178);

		BufferedInputStream bis = IOUtils.buffer(fis);
		
		// 메모리 용량보다 더 큰 파일크기를 전달할 수 없으니, 조금씩 넘겨주는 방식으로 처리해야 한다.
		// 4096byte만큼 계속 읽으면서 쏴주는 것
		// 4096byte보다 작게 남으면 작은 것들을 쏴줘라.
		while (bis.available() > 0) {
			byte[] buffer = new byte[4096];
			int length = bis.read(buffer);
			
			response.getOutputStream().write(buffer, 0, length);
		}
		
		response.flushBuffer();
	}
	
}
