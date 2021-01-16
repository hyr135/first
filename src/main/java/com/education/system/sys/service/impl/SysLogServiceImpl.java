package com.education.system.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.system.common.entity.PageInfo;
import com.education.system.sys.entity.SysLog;
import com.education.system.sys.mapper.SysLogMapper;
import com.education.system.sys.service.ISysLogService;

@Service
public class SysLogServiceImpl implements ISysLogService{
	//
	@Autowired
	private SysLogMapper sysLogMapper;
	@Override
	public void findDataGrid(PageInfo pageInfo) {
		//调用方法
		pageInfo.setRows(sysLogMapper.findDataGrid(pageInfo));
		pageInfo.setTotal(sysLogMapper.findDataGridCount(pageInfo));
		
	}
	@Override
	public void insertLog(SysLog sysLog) {
		
		try {
			sysLogMapper.insert(sysLog);
		} catch (Exception e) {
			e.getSuppressed();
		}
		
	}
	@Override
	public SysLog selectById(Integer id) {
		
		return sysLogMapper.selectById(id);
	}
	@Override
	public void batchDelete(List<String> ids) {
		sysLogMapper.batchDelete(ids);
	}
	@Override
	public int delByDate(String date) {
		int count = sysLogMapper.delLogCount(date);
		sysLogMapper.delByDate(date);
		
		return count;
	}
	
	
	

	
}
