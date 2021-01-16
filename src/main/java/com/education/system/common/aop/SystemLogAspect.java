package com.education.system.common.aop;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.education.system.common.entity.ShiroUser;
import com.education.system.common.util.MyUtil;
import com.education.system.common.util.WebUtils;
import com.education.system.sys.entity.SysLog;
import com.education.system.sys.service.impl.SysLogServiceImpl;

/**
 * 切面类
 * @author Dell
 *
 */
@Component
@Aspect
public class SystemLogAspect {
	
	// 注入Service用于把日志保存数据库
	@Resource
	private SysLogServiceImpl sysLogServiceImpl;
	
	// 本地异常日志记录对象
	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
	/**
	 * 定义切点
	 */
	@Pointcut("@annotation(com.education.system.common.aop.MySysLog)")
	public void controllerAspect() {
		
	}
	
	/**
	 * 在使用了这个自定义的注解之前方法的执行这个切面.前置通知,该通知是进行日志的插入
	 * @param joinPoint
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		//从容器中获取到request对象
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		// 请求用户名 (读取shiro中保存的用户信息),将令牌中的数据存放进ShiroUser中
		ShiroUser  principal = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		// 请求类,获取进行切面操作的类是哪一个类.
		String strClassName  = joinPoint.getTarget().getClass().getName();
		//请求地址
		String requestURI = request.getRequestURI();
		// 请求参数
		//使用StringBuffer 进行创建String对象,是可变的线程安全的String ,可以进行拼接,最后使用toString方法变成一个String
		StringBuffer bfParams = new StringBuffer();
		Object[] args = joinPoint.getArgs();
		
		//使用枚举来存放数据
		Enumeration<String> paraNames = null;
		//判断参数是否为空
		if (args != null && args.length > 0) {
			
			paraNames = request.getParameterNames();
			String key;
			String value;
			
			//遍历
			while(paraNames.hasMoreElements()) {
				key = paraNames.nextElement();
				value = request.getParameter(key);
				bfParams.append(key).append("=").append(value).append("&");
				
			}
			
			if (StringUtils.isBlank(bfParams)) {
				bfParams.append(request.getQueryString());
			}
		}
		
		// 请求的IP,使用工具类进行地址的计算.
		String ip = WebUtils.getRemoteAddr(request);
		
		try {
			
			System.out.println("=====前置通知开始=====");
			//获取操作名
			String operation = getControllerMethodDescription(joinPoint); 
			
			// 获取登录用户名,使用令牌里面的信息
			String loginName = principal.loginName;
			//创建日志的实体类进行存放获取的信息
			SysLog log = new SysLog();
			
			log.setLoginName(loginName);
			log.setOperationName(operation);
			log.setOperationClass(strClassName);
			log.setOperationAddress(requestURI);
			log.setOperationParams(bfParams.toString());
			log.setOperationIp(ip);
			//以字符串的形式进行时间的存放
			log.setCreateTime(MyUtil.getNowDateStr2());
			// *========保存数据库日志=========*//
			// 保存数据库
			System.out.println("执行保存操作前");
			sysLogServiceImpl.insertLog(log);
			System.out.println("执行保存操作后");

			
			
		} catch (Exception e) {
			// 记录本地异常日志,记录当日志记录异常的时候进行显示日志
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
		
	}
	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * @param joinPoint 切点
	 * @return
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception{
		String targetName = joinPoint.getTarget().getClass().getName();
		//获取方法,获取签名
		String methodName = joinPoint.getSignature().getName();
		//获取参数列表
		Object[] arguments = joinPoint.getArgs();
		
		//利用反射获取到目标类
		Class<?> targetClass  = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			//当方法名是在哪个类中的方法是一样的时候才进行操作
			if (method.getName().equals(methodName)) {
				
				Class<?>[] clazzs  = method.getParameterTypes();
				
				if (clazzs.length == arguments.length) {
					//增加描述信息
					description = method.getAnnotation(MySysLog.class).value();
					break;
				}
			}
			
		}
		
		
		return description;
		
	}
	
	
	
}
