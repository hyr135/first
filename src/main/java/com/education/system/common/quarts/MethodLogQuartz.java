package com.education.system.common.quarts;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.education.system.common.util.DateUtil;
import com.education.system.common.util.GlobalUtil;
import com.education.system.common.util.MyUtil;
import com.education.system.sys.entity.SysLog;
import com.education.system.sys.service.impl.SysLogServiceImpl;

@Component
public class MethodLogQuartz {
	
	@Autowired
	private SysLogServiceImpl sysLogServiceImpl; 
	//将该类作为一个日志的方式,获取对象
	private static final Logger logger = LoggerFactory.getLogger(MethodLogQuartz.class);
	
	private static final String LOGDAYS= "log.days";
	
	@Scheduled(cron = "0 55 13 * * ?")
	public void clearLog() {
		
		System.out.println("开始执行定时任务");
		
		try {
			
			int logDays = Integer.valueOf(GlobalUtil.getValue(LOGDAYS));
			//获取执行时的时间
			Date date = DateUtil.getDate(DateUtil.getDate(), -logDays);
			//创建一个简单的时间格式转换类
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//将获取的date格式数据装换成固定类型的字符串
			String str = sdf.format(date);
			//记录指定日期之前的日志数量
			int count = sysLogServiceImpl.delByDate(str);
			
			//创建一个实体类来存放数据执行操作后的数据
			SysLog sysLog = new SysLog();
			//将数据放入到实体类中
			sysLog.setOperationName("定时删除日志:"+count+"条");
			sysLog.setOperationClass("");
			sysLog.setOperationAddress("");
			sysLog.setOperationParams("");
			sysLog.setOperationIp("");
			sysLog.setLoginName("系统自动任务");
			sysLog.setCreateTime(MyUtil.getNowDateStr2());
			//将日志信息插入到日志表中
			sysLogServiceImpl.insertLog(sysLog);
			
			
			
			
		} catch (RuntimeException e) {
			
			logger.error("定时操作失败:"+e.getMessage());
			
		}
		
	}
	
	
}
