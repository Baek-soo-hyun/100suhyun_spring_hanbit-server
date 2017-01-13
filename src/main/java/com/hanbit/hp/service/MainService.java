package com.hanbit.hp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.hp.dao.MainDAO;

@Service
public class MainService {
	
	@Autowired
	private MainDAO mainDAO;
	
	public List getMainImgs() {

		return mainDAO.selectMainImgs();
	}

}
