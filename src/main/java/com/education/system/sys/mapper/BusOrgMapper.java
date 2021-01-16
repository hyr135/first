package com.education.system.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.education.system.sys.entity.BusOrg;
@Repository
public interface BusOrgMapper {
    /**
             * 查询所有机构的列表集合
     * @return 返回机构列表
     */
	List<BusOrg> findOrgAll();
	
	/**
	 * 查询一级机构
	 *
	 * @return
	 */
	List<BusOrg> findOrgAllByPidNull();

	/**
	 * 查询机构子集
	 *
	 * @param pid
	 * @return
	 */
	List<BusOrg> findOrgAllByPid(Integer pid);
	
	/**
	 * 
	 * @param org 从前端的添加页面将数据以json的格式放到实体类中.
	 * @return 影响的行数
	 */
	int insert(BusOrg org);
	
	/**
	 * 根据机构ID
	 * 删除机构
	 * @param id
	 * @return
	 */
	int deleteOrgById(Integer id);
	/**
	 * 根据机构ID,进行查找对应机构的数据,进行显示到编辑页面.
	 * @param id
	 * @return
	 */
	BusOrg findOrgById(Integer id);
	/**
	 * 依据传输过来的 全部信息进行数据的更新.
	 * @param org
	 * @return
	 */
	int updateOrg(BusOrg org);
	
}