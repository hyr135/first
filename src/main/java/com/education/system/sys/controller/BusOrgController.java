package com.education.system.sys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.education.system.common.aop.MySysLog;
import com.education.system.common.entity.Result;
import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.BusOrg;
import com.education.system.sys.service.impl.BusOrgServiceImpl;
import com.education.system.sys.vo.BusOrgVo;
/**
 * 该控制器是关于机构管理的界面.
 * @author Dell
 *
 */
@Controller
@RequestMapping("org")
public class BusOrgController extends BaseContorller{
	//进行依赖注入
	@Autowired
	private BusOrgServiceImpl busOrgServiceImpl;
	
	@MySysLog(value="查看机构列表")
	@RequestMapping("/manager")
	public String orgPage() {
		
		return "org/orgPage";
	}
	/**
	 * 获取机构树
	 * @return 机构列表
	 */
	
	@RequiresPermissions("org:list")
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<BusOrgVo> treeGrid(){
		List<BusOrg> treeGrid  = busOrgServiceImpl.findTreeGrid();
		
		
		// 处理机构学段、电脑配置的中文显示
		List<BusOrgVo> resultttreeGrid = new ArrayList<BusOrgVo>();
		
		if (treeGrid != null && treeGrid.size() > 0) {
			for (BusOrg org : treeGrid) {
				BusOrgVo vo = new BusOrgVo();
				vo.setCreateTime(org.getCreateTime());
				vo.setOrgAddress(org.getOrgAddress());
				vo.setOrgBoss(org.getOrgBoss());
				vo.setOrgCity(org.getOrgCity());
				vo.setOrgCode(org.getOrgCode());
				// 电脑配置(1-人手一台；2-多人共用)
				vo.setOrgComputer(org.getOrgComputer());
				if (org.getOrgComputer() == 1) {
					vo.setOrgComputerName("人手一台");
				} else {
					vo.setOrgComputerName("多人共用");
				}
				vo.setOrgCounty(org.getOrgCounty());
				vo.setOrgIcon(org.getOrgIcon());
				vo.setOrgImg(org.getOrgImg());
				vo.setId(org.getId());
				vo.setOrgName(org.getOrgName());
				vo.setOrgPerson(org.getOrgPerson());
				vo.setOrgPhone(org.getOrgPhone());
				vo.setOrgPid(org.getOrgPid());
				vo.setOrgProvince(org.getOrgProvince());
				vo.setOrgSort(org.getOrgSort());

				// 机构学段(1-小学，2-初中，3-高中)
				vo.setOrgStage(org.getOrgStage());
				switch (org.getOrgStage()) {
				case 1:
					vo.setOrgStageName("小学");
					break;
				case 2:
					vo.setOrgStageName("初中");
					break;
				case 3:
					vo.setOrgStageName("高中");
					break;
				}

				// 把新的对象放到集合中
				resultttreeGrid.add(vo);
			}
		}
		
		return resultttreeGrid;
	}
	
	
	
	
	/**
	 * 添加机构页
	 *
	 * @return
	 */
	@MySysLog(value="添加机构页")
	@RequiresPermissions("org:add")
	@RequestMapping("/addPage")
	public String addPage() {
		return "org/orgAdd";
	}
	/**
	 * 添加机构中的机构树和用户管理中组织机构的 机构资源树
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> tree(){
		//调用服务层方法
		List<Tree> trees = busOrgServiceImpl.findTree();
		
		return trees;
	}
	
	@MySysLog(value="添加机构")
	@RequiresPermissions("org:add")
	@RequestMapping("/add")
	@ResponseBody
	public Result addOrg(BusOrg org) {
		Result result = new Result();
		//调用服务层方法
		try {
			int orgInsert = busOrgServiceImpl.orginsert(org);
			if (orgInsert >0) {
				result.setSuccess(true);
				result.setMsg("添加机构成功");
				
				return result;
			}
			result.setMsg("数据存储出现问题,请稍后重试");
			return result;
		} catch (Exception e) {
			result.setMsg("机构添加失败");
			return result;
		}
	}
	
	@MySysLog(value="删除机构")
	@RequiresPermissions("org:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Result orgDelete(Integer id) {
		
		Result result = new Result();
		try {
			int orgDeleteById = busOrgServiceImpl.orgDeleteById(id);
			if (orgDeleteById > 0) {
				result.setMsg("机构删除成功!");
				result.setSuccess(true);
				
				return result;
			}
		} catch (Exception e) {
			result.setMsg("机构删除失败!");
			result.setSuccess(false);
			
			return result;
		}
		
		return result;
		
		
	}
	
	@MySysLog(value="前往编辑列表")
	@RequiresPermissions("org:edit")//使用该注解就会让该方法在执行的时候去权限的的验证应该也是aop
	@RequestMapping(value="/editPage",method=RequestMethod.GET)
	public String orgEditPage(HttpServletRequest request ,Integer id) {
		//在参数列表里写什么,对应前端传过来的数据,就能接受到,ID是从get的地址栏得到
		//request是为了数据的流转.
		//调用服务层方法
		BusOrg findOrgById = busOrgServiceImpl.findOrgById(id);
		request.setAttribute("org",findOrgById);
		return "org/editPage";
	}
	
	@MySysLog(value="编辑机构")
	@RequiresPermissions("org:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Result editOrgMsg(BusOrg org) {
		Result result = new Result();
		try {
			int updateOrg = busOrgServiceImpl.updateOrg(org);
			result.setMsg("修改机构信息成功");
			result.setSuccess(true);
			return result;
		} catch (Exception e) {

			e.printStackTrace();
			result.setMsg("机构删除失败");
			return result;
			
		}	
	}
	
	
	
}
