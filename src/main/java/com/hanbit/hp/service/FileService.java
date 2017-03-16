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

@Service
public class FileService {

	public static final String PATH_PREFIX = "/hanbit/upload/";

	@Autowired
	private FileDAO fileDAO;
	
	@Transactional
	public void updateAndSave(String fileId, MultipartFile multipartFile) {
		delete(fileId);
		
		addAndSave(fileId, multipartFile);
	}
	
	@Transactional
	public String addAndSave(String fileId, MultipartFile multipartFile) {
		fileId = add(fileId, multipartFile.getContentType(), multipartFile.getSize(),
				multipartFile.getOriginalFilename());
		
		String filePath = PATH_PREFIX + fileId;
		File file = new File(filePath);
		
		try {
			FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return fileId;
	}
	
	private String add(String fileId, String fileType, long fileSize, String fileName) {
		if (fileId == null) {
			fileId = KeyUtils.generateKey("FILE");
		}
		
		fileDAO.insert(fileId, fileType, fileSize, fileName);
		
		return fileId;
	}
	
	@Transactional
	public void delete(String fileId) {
		fileDAO.delete(fileId);
		
		String filePath = PATH_PREFIX + fileId;
		File file = new File(filePath);
		
		try {
			FileUtils.forceDelete(file);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map get(String fileId) {
		return fileDAO.selectOne(fileId);
	}
	
}

