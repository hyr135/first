package com.education.system.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.BusOrg;
import com.education.system.sys.entity.SysLog;
import com.education.system.sys.mapper.BusOrgMapper;
import com.education.system.sys.service.IBusOrgService;

@Service
public class BusOrgServiceImpl implements IBusOrgService{

	//进行依赖注入
	@Autowired
	private BusOrgMapper busOrgMapper;
	@Override
	public List<BusOrg> findTreeGrid() {
		//调用mapper接口方法
		List<BusOrg> treeList = busOrgMapper.findOrgAll();
		
		return treeList;
	}
	@Override
	public List<Tree> findTree() {
		List<Tree> trees = new ArrayList<Tree>();
		// 查找父资源的信息 ;
		List<BusOrg> organizationFather = busOrgMapper.findOrgAllByPidNull();
		if (organizationFather != null) {
			for (BusOrg organizationOne : organizationFather) {
				Tree treeOne = new Tree();
				treeOne.setId(organizationOne.getId());
				treeOne.setText(organizationOne.getOrgName());
				treeOne.setIconCls(organizationOne.getOrgIcon());

				List<BusOrg> organizationSon = busOrgMapper.findOrgAllByPid(organizationOne.getId());
				if (organizationSon != null) {
					List<Tree> tree = new ArrayList<Tree>();
					for (BusOrg organizationTwo : organizationSon) {
						Tree treeTwo = new Tree();
						treeTwo.setId(organizationTwo.getId());
						treeTwo.setText(organizationTwo.getOrgName());
						treeTwo.setIconCls(organizationTwo.getOrgIcon());
						tree.add(treeTwo);
					}
					treeOne.setChildren(tree);
				} else {
					treeOne.setState("closed");
				}
				trees.add(treeOne);
			}
		}
		
		
		return trees;	
	}
	@Override
	public int orginsert(BusOrg org) {
		//调用mapper层的数据
		int insert = busOrgMapper.insert(org);
		return insert;
	}
	@Override
	public int orgDeleteById(Integer id) {
		
		return busOrgMapper.deleteOrgById(id);
	}
	@Override
	public BusOrg findOrgById(Integer id) {
		
		return busOrgMapper.findOrgById(id);
	}
	@Override
	public int updateOrg(BusOrg org) {
		
		return busOrgMapper.updateOrg(org);
	}
	

}
