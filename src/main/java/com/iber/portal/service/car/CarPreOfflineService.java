package com.iber.portal.service.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.car.CarPreOfflineMapper;

@Service
public class CarPreOfflineService {
    @Autowired
	private CarPreOfflineMapper carPreOfflineMapper;
	
	
}
