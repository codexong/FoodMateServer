package com.example.service;

import java.util.List;

import com.example.model.Bar;

public interface BarService {

	public Bar getBarDetail(String id) throws Exception;
	
	public List<Bar> getAllBars() throws Exception;
}
