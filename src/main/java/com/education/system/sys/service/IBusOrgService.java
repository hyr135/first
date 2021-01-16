package com.education.system.sys.service;

import java.util.List;

import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.BusOrg;
import com.education.system.sys.entity.SysLog;

public interface IBusOrgService {
	/**
	 * 接口.查找所有机构数
	 * @return
	 */
	List<BusOrg> findTreeGrid();
	/**
	 * 查询资源数
	  * 查找组织机构的资源树 <br>
	 * 第一步: 先加载父资源 <br>
	 * 第二步: 通过父资源的 id 查询子资源 加入到 实体层中<br>
	 * @return
	 */
	List<Tree> findTree();
	/**
	 * 插入机构
	 * @param org 实体类类型,存放的数据.
	 * @return 影响的行数
	 */
	int orginsert(BusOrg org);
	/**
	 * 根据机构ID删除机构
	 * @param id
	 * @return
	 */
	int orgDeleteById(Integer id);
	
	/**
	 * 根据机构ID
	 * 查询该机构的全部信息
	 * @param id
	 * @return
	 */
	BusOrg findOrgById(Integer id);
	
	/**
	 * 根据传入的机构信息进行数据的更新
	 * @param org
	 * @return 影响的行数
 	 */
	int updateOrg(BusOrg org);
	
	
	

}
