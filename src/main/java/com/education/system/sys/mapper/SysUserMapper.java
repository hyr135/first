package com.education.system.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.education.system.common.entity.PageInfo;
import com.education.system.sys.entity.SysUser;
import com.education.system.sys.vo.SysUserVo;
@Repository
public interface SysUserMapper {
	/**
	 * 通过用户名查询用户信息
	 * @param loginName
	 * @return 返回的是一个用户表存储的所有字段信息，保存进了一个与数据库表想对应的实体类中
	 */
    SysUser findUserByloginName(String loginName);
    /**
     * 通过用户id查询用户信息
     * @param id
     * @return
     */
    SysUser findUserById(Integer id);
    
    /**
	 * 根据用户 Id查找,返回Vo对象
	 */
	SysUserVo findUserVoById(Integer id);
	/**
	 * 编辑个人资料
	 * 
	 * @param user
	 * @return
	 */
	int updatePersonFile(SysUser user);

	/**
	 * 修改用户密码
	 * @param userId
	 * @param pwd
	 */
	int updateUserPwdById(@Param("userId") Integer userId, @Param("pwd") String pwd);
	
	/**
	 * 用户列表
	 */
	List findUserPageCondition(PageInfo pageInfo);

	/**
	 * 统计用户
	 */
	int findUserPageCount(PageInfo pageInfo);
	
	
	/**
	 * 添加用户
	 */
	int insert(SysUser user);
	
	/**
	 * 通过 id 删除用户
	 */
	int deleteUserById(Integer id);
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	int updateUser(SysUser user);
	
	
}