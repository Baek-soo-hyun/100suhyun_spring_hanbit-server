package com.hanbit.hp.service;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hanbit.hp.dao.FileDAO;
import com.hanbit.hp.util.KeyUtils;


// PC에 지정 경로에 파일을 upload함

@Service
public class FileService {

	public static final String PATH_PREFIX = "/hanbit/upload/";

	@Autowired
	private FileDAO fileDAO;
	
	// PC에 저장된 파일을 업데이트(삭제하고 추가)
	@Transactional
	public void updateAndSave(String fileId, MultipartFile multipartFile) {
		delete(fileId);
		
		addAndSave(fileId, multipartFile);
	}
	
	// PC에 파일 추가
	@Transactional
	public String addAndSave(String fileId, MultipartFile multipartFile) {
		fileId = add(fileId, multipartFile.getContentType(), multipartFile.getSize(),
				multipartFile.getOriginalFilename());
		
		String filePath = PATH_PREFIX + fileId;
		File file = new File(filePath);
		
		// writeByteArrayToFile(File file, byte[] data)
		// Writes a byte array to a file creating the file if it does not exist.
		// byte의 배열 형태의 정보를 file에 쓰는 것 
		// file : filePath를 File 그릇에 담아줌
		try {
			FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return fileId;
	}
	
	
	// DB에 파일 정보 추가
	private String add(String fileId, String fileType, long fileSize, String fileName) {
		if (fileId == null) {
			fileId = KeyUtils.generateKey("FILE");
		}
		
		fileDAO.insert(fileId, fileType, fileSize, fileName);
		
		return fileId;
	}
	
	
	@Transactional
	public void delete(String fileId) {
		
		// DB에 있는 file 정보를 삭제
		fileDAO.delete(fileId);
		
		// PC에 있는 file 정보를 삭제
		String filePath = PATH_PREFIX + fileId;
		File file = new File(filePath);
		
		try {
			FileUtils.forceDelete(file);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// DB에 있는 파일 정보 가져오기
	public Map get(String fileId) {
		return fileDAO.selectOne(fileId);
	}
	
}