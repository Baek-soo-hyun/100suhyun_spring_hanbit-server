package com.hanbit.hp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.hp.dao.StoreDAO;

@Service("clientStoreService")
public class StoreService {
	
	@Autowired
	private StoreDAO storeDAO;

	public List getEight() {
		return storeDAO.selectList(1, 8);
	}
	
}

