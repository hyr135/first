package com.education.system.sys.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.education.system.common.aop.MySysLog;
import com.education.system.common.entity.PageInfo;
import com.education.system.common.entity.Result;
import com.education.system.sys.entity.SysLog;
import com.education.system.sys.service.impl.SysLogServiceImpl;
import com.education.system.sys.vo.LogVo;

@Controller
@RequestMapping("log")
public class SysLogController extends BaseContorller{
	//依赖注入
	@Autowired
	private SysLogServiceImpl sysLogServiceImpl;
	/**
	 * 日志列表
	 * @param request
	 * @return jsp页面
	 */
	@MySysLog(value="跳转到日志列表")
	@RequiresPermissions("log:list")
	@RequestMapping("/manager")
	public String logPage(HttpServletRequest request ) {
		
		return "log/log";
	}
	
	/**
	 * 获取数据网格数据的控制器
	 * @param logVo
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return 分页实体类,包含了信息.
	 */
	@MySysLog(value="日志列表查看")
	@RequiresPermissions("log:list")
	@RequestMapping(value="/dataGrid",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo dataGrid(LogVo logVo, Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		//使用map来存储,因为实体类中也是使用map类型来存储
		Map<String, Object> condition = new HashMap<String, Object>();
		if (StringUtils.isNoneBlank(logVo.getOperationName())) {
			condition.put("operationName", logVo.getOperationName());
		}
		if (logVo.getCreatedateStart() != null) {
			condition.put("startTime", logVo.getCreatedateStart());
		}
		if (logVo.getCreatedateEnd() != null) {
			condition.put("endTime", logVo.getCreatedateEnd());
		}
		
		pageInfo.setCondition(condition);
		
		//调用服务层的方法
		sysLogServiceImpl.findDataGrid(pageInfo);
		
		return pageInfo;
		
	}
	
	/**
	 * 日志详情页面
	 * @param request ,用于数据的流转
	 * @param id 前端传过来的id数据
	 * @return
	 */
	@RequestMapping(value="/detailPage",method=RequestMethod.GET)
	public String logDetail(HttpServletRequest request, Integer id) {
		//调用服务层方法
		SysLog selectById = sysLogServiceImpl.selectById(id);
		request.setAttribute("log", selectById);
		
		return "log/logDetail";
		
	}
	/**
	 * 删除日志
	 * @param ids
	 * @return
	 */
	@MySysLog("日志删除")
	@RequiresPermissions("log:batchDelete")
	@RequestMapping(value="/batchDelete",method=RequestMethod.POST)
	@ResponseBody
	public Result logDete(String ids) {
		
		Result result = new Result();
		//将前端传过来的ID进行字符串
		List<String> cons = new ArrayList<String>();
		
		String[] id = ids.split(",");
		
		for (String idss : id) {
			if (!idss.trim().equals("")) {
				cons.add(idss);
			}
		}
		try {
			
			sysLogServiceImpl.batchDelete(cons);
			result.setMsg("删除成功");
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			result.setMsg("删除失败");
			e.printStackTrace();
			return result;
		}
		
		
	}
	
	
	
	
	
	
	
}
