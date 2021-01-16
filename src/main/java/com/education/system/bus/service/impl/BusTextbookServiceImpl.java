package com.education.system.bus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.system.bus.mapper.BusTextbookMapper;
import com.education.system.bus.service.IBusTextbookService;
import com.education.system.common.entity.PageInfo;

@Service
public class BusTextbookServiceImpl implements IBusTextbookService{
	//依赖注入
	@Autowired
	private BusTextbookMapper busTextbookMapper;

	@Override
	public void textbookDataGrid(PageInfo pageInfo) {
		
		//调用方法
		pageInfo.setRows(busTextbookMapper.findData(pageInfo));
		pageInfo.setTotal(busTextbookMapper.findDataCount(pageInfo));
		
	}
	
	
	
	
}
