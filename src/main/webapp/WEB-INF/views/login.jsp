<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.Date"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>桌世未来-后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="${path}/static/images/BM32px.ico">
    <link href="${path}/static/layui/css/layui.css" rel="stylesheet" />
    <script src="${path}/static/layui/layui.js"></script>
    <script src="${path}/static/js/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/animate.css" media="all">
    <link rel="stylesheet" type="text/css" href="${path}/static/css/login.css" media="all">
    <!-- [JS] -->
    <script type="text/javascript" src="${staticPath }/static/js/extJs.js" charset="utf-8"></script>
    	
    
   
    
    <script type="text/javascript">
    function refreshImg(){
		/* 加入一个时间戳接在get请求后面防止浏览器的缓存功能不会去重新请求相同的路径 */
		/* document.getElementById("imgCode").src */
		document.getElementById("imgCode").src="${path}/imageCode/showCode?timestamp="+(new Date()).getTime();
		
	}
    
    $(function() {
    	// 用户登录
    	layui.use([ 'layer', 'form' ], function() {
    		var layer = layui.layer, form = layui.form;
    		form.on('submit(loginForm)', function(data) {
    			var url = "${staticPath}/login";
    			$.ajax({
    				url : url,
    				method : 'post',
    				data : data.field,
    				dataType : 'JSON',
    				success : function(data) {
    					if (data.success) {
    						layer.msg("登录成功", {
    							offset : '60px',
    							icon : 6,
    							anim : 6,
    							time : 1000
    						});
    						//跳转到后台首页
    						window.location.href = '${path}/index';
    					} else {
    						//刷新验证码
    						refreshImg();
    						layer.msg(data.msg, {
    							offset : '60px',
    							icon : 5,
    							anim : 6,
    							time : 3000
    						});
    					}
    				}
    			});
    		});
    	});
    });
    </script>
</head>
<body>
<div class="main layui-layout animated shake larry-delay2" id="larry_login" style="margin-top: 94px;">
    <div class="title">桌世未来-后台管理系统</div>
    <p class="info">登录中心</p>
    <div class="user-info">
        <div class="admin"><img src="${path}/static/images/user.png"></div>
        <form class="layui-form" id="login" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">账号:</label>
                <input type="text" name="loginName" style="ime-mode:disabled" required lay-verify="required" aautocomplete="off" class="layui-input larry-input" placeholder="请输入账号" autocomplete="off">
            </div>
            <div class="layui-form-item" id="password">
                <label class="layui-form-label">密码:</label>
                <input type="password" name="userPassword" style="ime-mode:disabled" required lay-verify="required|password" aautocomplete="off" class="layui-input larry-input" placeholder="请输入密码" autocomplete="off">
            </div>
            <div class="layui-form-item larry-verfiy-code" id="larry_code">
                <input type="text" maxlength="4" name="txtCode" class="layui-input larry-input" required lay-verify="required" placeholder="输入验证码">
                <div class="code">
                    <div class="arrow"></div>
                    <div class="code-img">
             
                        <!-- <img src="" alt="点击切换" id="imgCode"> 
                        	debugger;这个调试是js里面的调试用语，而不是这个html的用语
                        -->
                        <img src="${path}/imageCode/showCode" alt="点击切换" id="imgCode" onclick="refreshImg()"/>
                        
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <input type="button" class="layui-btn larry-btn" value="立即登录" id="loginForm"  lay-submit lay-filter="loginForm">
            </div>
        </form>
    </div>
</div>
</body>
</html>

