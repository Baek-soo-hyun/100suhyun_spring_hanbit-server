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
	public String addAndSave(String fileId, MultipartFile multipartFile) {
		String filePath = PATH_PREFIX + fileId;
		File file = new File(filePath);
		
		fileId = add(fileId, multipartFile.getContentType(), multipartFile.getSize(),
				multipartFile.getOriginalFilename());
		
		try {
			FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return fileId;
	}
	
	private String add(String fileType, long fileSize, String fileName) {
		return add(null, fileType, fileSize, fileName);
	}
	
	private String add(String fileId, String fileType, long fileSize, String fileName) {
		if (fileId == null) {
			fileId = KeyUtils.generateKey("FILE");
		}
		
		fileDAO.insert(fileId, fileType, fileSize, fileName);
		
		return fileId;
	}
	
	public Map get(String fileId) {
		return fileDAO.selectOne(fileId);
	}
}
