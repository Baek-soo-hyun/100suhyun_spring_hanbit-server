package com.hanbit.hp.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hanbit.hp.admin.dao.StoreDAO;
import com.hanbit.hp.service.FileService;
import com.hanbit.hp.util.KeyUtils;

@Service
public class StoreService {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private StoreDAO storeDAO;
	
	public List getList(int page, int rowsPerPage) {
		return storeDAO.selectList(page, rowsPerPage);
	}
	
	public int count() {
		return storeDAO.count();
	}
	
	public Map get(String storeId) {
		return storeDAO.selectOne(storeId);
	}
	
	@Transactional
	public int modify(String storeId, String storeName,
			String categoryId, String locationId,
			MultipartFile storeImgFile) {
		
		int result = storeDAO.update(storeId, storeName, categoryId, locationId);
		
		if (storeImgFile != null) {
			fileService.updateAndSave(storeId, storeImgFile);
		}
		
		return result;
	}

	// 자동 rollback을 위해서 @Transactional을 걸어준다.
	// @Transactional => 에러가 발생했을 때, 에러 발생한 그 데이터를 DB에 저장하지 않게 롤백해준다.
	@Transactional
	public int add(String storeName,
			String categoryId, String locationId,
			MultipartFile storeImgFile) {
		
		String storeId = KeyUtils.generateKey("STO");
		String storeImg = "/api2/file/" + storeId;	
		
		int result = storeDAO.insert(storeId, storeName, storeImg, categoryId, locationId);
		
		fileService.addAndSave(storeId, storeImgFile);
		
		return result;
	}
	
}