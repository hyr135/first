<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>后台首页</title>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<link rel="stylesheet" type="text/css" href="${path}/static/layui/css/layui.css">
<link rel="stylesheet" href="${path}/static/css/main.css">
<script type="text/javascript" src="${staticPath }/static/js/jquery-3.2.1.min.js" charset="utf-8"></script>
</head>
<body>
	<div class="layui-fluid"style="padding: 15px;">
		<div class="larry-container animated fadeIn">
			<div style="margin-top: 30px; margin-bottom: 30px">
				欢迎您： <span class="x-red"><shiro:principal property="userName"></shiro:principal></span><span id="userGender"></span>
			</div>
			<!-- 系统信息 -->
			<fieldset class="layui-elem-field">
				<legend>系统简介</legend>
				<div class="layui-elem-quote layui-quote-nm">
					<ul class="layui-timeline">
						<li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis" style="color: rgb(47, 143, 250);">&#xe756;</i>
							<div class="layui-timeline-content layui-text">
								<div class="layui-timeline-title">
									<h2 class="layui-inline">桌世未来后台管理系统 前端是通过layui + easyui来实现的，后端基于ssm框架，数据库使用mysql数据库。</h2>
								</div>
							</div>
						</li>
						<li class="layui-timeline-item"><i class="layui-icon layui-timeline-axis" style="color: rgb(47, 143, 250);">&#xe756;</i>
							<div class="layui-timeline-content layui-text">
								<div class="layui-timeline-title">
									<h2 class="layui-inline">桌世未来是一家面向中小学的教育培训机构，其后台管理系统的主要包括系统模块和业务模块，两个模块又有各自的功能模块。</h2>
								</div>
								<div style="float: left;margin-right: 150px;">
									<h3 class="layui-inline">系统管理模块：</h3>
									<ul>
		                                <li>资源管理</li>
		                                <li>角色管理</li>
		                                <li>机构管理</li>
		                                <li>用户管理</li>
		                                <li>日志管理</li>
		                                <li>字典管理</li>
		                                <li>....</li>
		                            </ul>
	                            </div>
	                            <div>
		                           <h3 class="layui-inline">业务管理模块：</h3>
									<ul>
		                                <li>硬件管理</li>
		                                <li>校区管理</li>
		                                <li>教材管理</li>
		                                <li>课时管理</li>
		                                <li>班级管理</li>
		                                <li>教师管理</li>
		                                <li>学生管理</li>
		                            </ul>
	                            </div>
							</div>
					 </li>
					</ul>
				</div>
			</fieldset>
		</div>
		<fieldset class="layui-elem-field">
			<legend>开发团队</legend>
			<div class="layui-field-box">
				<table class="layui-table">
					<tbody>
						<tr>
							<th>版权所有</th>
							<td>嘻嘻</td>
						</tr>
						<tr>
							<th>开发者</th>
							<td>哈哈</td>
						</tr>
					</tbody>
				</table>
			</div>
		</fieldset>		
	</div>
</body>
<script type="text/javascript">
$(function () {
    $("#userGender").text(userGender());
    function userGender() {
        switch (${sessionScope.userGender}){
            case 1:{
                return "先生";
            }
            case 2:{
                return "女士";
            }
        }
    }
});
</script>
</html>