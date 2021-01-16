package com.education.system.bus.controller;


import java.util.HashMap;
import java.util.List; 
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.education.system.bus.entity.BusTextbook;
import com.education.system.bus.mapper.BusTextbookMapper;
import com.education.system.common.entity.PageInfo;
import com.education.system.sys.entity.SysRole;

@Controller
@RequestMapping("/textbook")
public class BusTextbookController {
	//依赖注入
	@Autowired
	private BusTextbookMapper BusTextbookMapper;
	//跳转到教材管理
	@RequiresPermissions("textbook:list")
	@RequestMapping("/manager")
	public String gotoTextbookPage(HttpServletRequest request ) {
		return "textbook/textbook";
	}
	/**
	 * 获取教材
	 * @param busTextbook 从前端获取数据
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return 分页的全部信息包括所有的数据网格.
	 */
	@RequiresPermissions("textbook:list")
	@RequestMapping(value="/dataGrid",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo datagrid(BusTextbook busTextbook,Integer page, Integer rows, String sort, String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		//将从前端传来的数据保存进将要进行传输的实体类.
		Map<String,Object> map = new HashMap<>();
		if ((busTextbook.getTextbookName()) != null && !(busTextbook.getTextbookName().equals(""))) {
			map.put("textbookName", busTextbook.getTextbookName());
		}
		pageInfo.setCondition(map);
		//调用服务层进行数据的查询.涉及到的是动态语句的执行.
		BusTextbookMapper.findData(pageInfo);
		return pageInfo;
	}
}