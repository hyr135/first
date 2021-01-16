package com.education.system.sys.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.system.common.entity.PageInfo;
import com.education.system.sys.entity.SysUser;
import com.education.system.sys.entity.SysUserRole;
import com.education.system.sys.mapper.SysRoleMapper;
import com.education.system.sys.mapper.SysUserMapper;
import com.education.system.sys.mapper.SysUserRoleMapper;
import com.education.system.sys.service.ISysUserService;
import com.education.system.sys.vo.SysUserVo;
@Service
public class SysUserServiceImpl implements ISysUserService {
	//注入依赖，注入mapper层依赖，原则注入接口
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public SysUser findSysUserById(Integer id) {
		//调用mapper接口进行调用
		return sysUserMapper.findUserById(id);
	}

	@Override
	public SysUser findSysUserByLoginName(String username) {
		
		return sysUserMapper.findUserByloginName(username);
	}

	@Override
	public SysUserVo findSysUserVoById(Integer id) {
		
		return sysUserMapper.findUserVoById(id);
	}

	@Override
	public int updatePersonFile(SysUserVo sysUserVo) {
		//创建一个用户表的实体类来存放前端传过来的数据，对用户表进行数据的update
		SysUser recordUser = new SysUser();
		//使用工具类进行数据的拷贝是Apache旗下的工具包
		try {
			PropertyUtils.copyProperties(recordUser, sysUserVo);
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		}
		int updateFlag = sysUserMapper.updatePersonFile(recordUser);
		
		return updateFlag;
	}

	@Override
	public int updateSysUserPwdById(Integer sysUserId, String pwd) {
		//调用mapper层的函数
		return sysUserMapper.updateUserPwdById(sysUserId, pwd);
	}

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		//进行业务的处理
		// 用户列表,将结果放在了分页的实体类.将实体类的所有结果返回给前端就可以了
				pageInfo.setRows(sysUserMapper.findUserPageCondition(pageInfo));
				// 统计用户
				pageInfo.setTotal(sysUserMapper.findUserPageCount(pageInfo));
		
	}
	@Transactional
	@Override
	public void addSysUser(SysUserVo sysUserVo) {
		//创建一个用户实体类对象
		SysUser sysUser = new SysUser();
		//将视图对象的数据存放到用户对象
		try {
			// 赋值 SysUserVo 中相同的属性到 SysUser 中
			PropertyUtils.copyProperties(sysUser, sysUserVo);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("类型转换异常：{}", e);
		}
		//将数据插入到用户表
		sysUserMapper.insert(sysUser);
		
		
		Integer id = sysUser.getId();
		//从视图对象中拿到角色ID
		String[] roles = sysUserVo.getRoleIds().split(",");
		//根据实体类进行创建用户角色视图
		SysUserRole sysUserRole = new SysUserRole();
		for (String string : roles) {
			
			sysUserRole.setUserId(id);
			System.out.println("判断是否是空值:"+sysUserRole.getUserId()); 
			sysUserRole.setRoleId(Integer.valueOf(string));
			sysRoleMapper.inserts(sysUserRole);
		}
			
	}
	
	@Transactional
	@Override
	public void deleteSysUserById(Integer id) {
		//这里应该使用事务,因为涉及到了一个多表的删除操作
		List<Integer> userRoleId = sysUserRoleMapper.findUserRoleIdByUserId(id);
		if (userRoleId != null && userRoleId.size() > 0) {
			//进行批量的删除中间表
			int batchDelete = sysUserRoleMapper.batchDelete(userRoleId);
		}
		
		//删除用户表中的用户,使用了事务之后就不用去担心会造成数据的不完整
		sysUserMapper.deleteUserById(id);
	}

	@Override
	public void updateSysUser(SysUserVo sysUserVo) {
		//进行业务的处理
		//创建一个用户表的实体类对象,作为参数进行数据的封装.
		SysUser sysUser = new SysUser();
		//将用户视图对象中的数据复制到用户实体类中
		try {
			PropertyUtils.copyProperties(sysUser, sysUserVo);
			
			
		} catch (Exception e) {
			//抛出运行时异常,这就是java代码的健壮性,能够对数据进行异常的处理,丰富的异常处理.
			 throw new RuntimeException("类型转换异常: {}",e);
		}
		
		//更新用户表的数据
		sysUserMapper.updateUser(sysUser);
		
		//删除用户与角色之间的中间表中的数据
		int id = sysUserVo.getId();
		//根据用户ID查找资源ID列表
		List<Integer> roleId = sysUserRoleMapper.findRoleIdListByUserId(id);
		//根据id批量删除
		if (roleId != null && roleId.size() > 0) {
			sysUserRoleMapper.batchDelete(roleId);
		}
		//删除完在插入数据
		//
		// 再更新,从视图模型中的数据是重新要更新的
		//将字符串类型的角色ID使用","进行分割,为了进行遍历,才进行这个操作.
		String[] roleIds = sysUserVo.getRoleIds().split(",");
		
		List<SysUserRole> batch = new ArrayList<SysUserRole>();
		if (roleIds != null && roleIds.length > 0) {
			//批量插入
			for (String string : roleIds) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(id);
				sysUserRole.setRoleId(Integer.valueOf(string));
				batch.add(sysUserRole);
			}
			
			sysUserRoleMapper.batchInsert(batch);
		}
		
		
		
	}
	
	
}
