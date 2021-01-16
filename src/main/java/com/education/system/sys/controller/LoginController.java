package com.education.system.sys.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.education.system.common.aop.MySysLog;
import com.education.system.common.entity.Result;

@Controller
public class LoginController extends BaseContorller {
	/**
	 * 
	 * @param loginName  登录名
	 * @param userPassword 登录密码
	 * @param txtCode 验证码
	 * @param session 会话存储
	 * @return 使用@ResponseBody 这一个注解将执行这一个控制器之后的结果以特定的形式返回
	 */
	@MySysLog(value="用户登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Result loginPost(String loginName, String userPassword, String txtCode, HttpSession session) {
		Result result = new Result();
		//如果这个这些参数是从前端传过来的参数，有跟前端传过来的参数要一致才行
		// 比对验证码是否正确
		if (!(txtCode.equals(session.getAttribute("code")))) {
			result.setMsg("验证码错误");
			return result;
		}
		Subject user = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginName, userPassword);
		try {
			user.login(token);
			
		} catch (UnknownAccountException e) {
			result.setMsg("账号不存在");
			return result;
		} catch (DisabledAccountException e) {
			result.setMsg("账号未启用");
			return result;
		} catch (IncorrectCredentialsException e) {
			result.setMsg("密码错误");
			return result;
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setMsg("未知错误,请刷新界面重新登录！重复登录没用请带上报错截图联系管理员！" + e.getMessage());
			return result;
		}
		result.setSuccess(true);
		session.setAttribute("userGender", getUserGender());
		//将结果通过response，响应回去，但是这是底层去封装的时候去实现的代码
		//这个数据的返回应该是json格式的一个数据，或者说
		return result;
	}
	/**
	 * 
	 * @return 返回视图映射文件
	 */
	@MySysLog(value="用户退出")
	@RequestMapping("/logout")
	public String logout() {
		return "redirect:login";
	}
	
}
	

