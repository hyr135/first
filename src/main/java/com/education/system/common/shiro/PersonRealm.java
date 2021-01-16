package com.education.system.common.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.sql.parser.Token;
import com.education.system.common.entity.ShiroUser;
import com.education.system.sys.entity.SysUser;
import com.education.system.sys.service.impl.SysRoleServiceImpl;
import com.education.system.sys.service.impl.SysUserServiceImpl;
@Component
public class PersonRealm extends AuthorizingRealm{
	//注入依赖
	@Autowired
	private SysRoleServiceImpl iSysRoleService;
	@Autowired
	private SysUserServiceImpl iSysUserService;
	/**
	 * Authorization 授权
	 * /**
	 * Shiro登录认证(原理：用户提交 用户名和密码 ---shiro 封装令牌---- realm 通过用户名将密码查询返回</br>
	 * ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
	 * 
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//得到用户认证信息，这个是从认证那边存放的数据拿到的，只有认证成功之后，才可以
		//进行下一步的授权
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		//得到用户角色id
		//创建一个无序的集合进行存放url数据
		HashSet<String> urlSet = new HashSet<>();
		List<Integer> roleList = shiroUser.getRoleList();
		//循环遍历角色id进行角色权限的搜索
		for (Integer roleId : roleList) {
			//调用iSysRoleService的方法进行角色的查询
			List<Map<Integer, String>> roleResource = iSysRoleService.findRoleResourceListByRoleId(roleId);
			//判断查询的结果是否为空
			if (roleResource !=null) {
				//进行遍历集合
				for (Map<Integer,String> map : roleResource) {
					//使用工具包进行辅助操作
					if (StringUtils.isNoneBlank(map.get("resource_permission"))) {
						//将权限数据存放到无序集合中
						urlSet.add(map.get("resource_permission"));
					}
				}
			}
			
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//添加权限列表（这个列表是可以自己定死在代码，但是这样设置不容易扩展，所以权限的内容就是
		//数据库里面获取之后见权限存放到代码中。
		//）
		info.setStringPermissions(urlSet);
		
		return info;
	}
	/**
	 * Authentication 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		//首先进行认证，在进行授权
		
			//主令牌，其实是将一个唯一的字段存入作为主令牌的一个名称，一般是用户名，手机号，身份证号
			String  userName = (String) token.getPrincipal();
			//通过登录名查找用户，将用户的信息存放到令牌中	
			SysUser user = iSysUserService.findSysUserByLoginName(userName);
			//对查询结果进行判断和认证步骤的实现
			if(user==null) {
				//异常这里只进行抛出不进行处理，方便在aop的时候进行捕获异常。
				throw new UnknownAccountException();
			}
			//用户存在在下一步判断,判断账号是否停用
			if(user.getUserState() == 2) {
				//账号停用异常
				throw new DisabledAccountException();
			}
			
			//根据用户id查询到他所对应的角色id，在角色表中查询
			List<Integer> roleList = iSysRoleService.findRoleIdListByUserId(user.getId());
			//将数据存放到ShiroUser中，在后面将数据存放到shiro中
			ShiroUser shiroUser = new ShiroUser(user.getId(), user.getLoginName(), user.getUserName(), roleList);
			//加盐，将数据打成字节
			ByteSource salts = ByteSource.Util.bytes(user.getLoginName());
			//最后将信息交给shiro（权限框架）
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUser,user.getUserPassword(), salts, getName());
			
				
				
		
		//将认证的信息返回
		return info;
	}

}
