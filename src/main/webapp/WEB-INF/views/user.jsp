<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<html>
<head>
    <title>个人资料</title>
    <link rel="stylesheet" href="${path}/static/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="${path}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${path}/static/js/jquery-3.2.1.min.js"></script>
    <style>
        .layui-form-item .layui-input-inline{
            width:500px;
        }
    </style>
</head>
<body style="padding-top: 40px;">
<form class="layui-form" id="personFile">
    <input type="hidden" name="id" value="${user.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">账号：</label>
        <div class="layui-input-inline">
            <input id="loginName" onclick="logN()" class="layui-input" value="${user.loginName}" readonly="readonly" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">姓名：</label>
        <div class="layui-input-inline">
            <input name="userName" required  lay-verify="required" value="${user.userName}" autocomplete="off" placeholder="请输入名字" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">性别：</label>
        <div class="layui-input-inline">
            <select name="userGender" id="userGender">
                <option value="1">男</option>
                <option value="2">女</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年龄：</label>
        <div class="layui-input-inline">
            <input name="userAge" required  lay-verify="required|number" value="${user.userAge}" autocomplete="off" placeholder="请输入年龄" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">电话：</label>
        <div class="layui-input-inline">
            <input name="userPhone" required  lay-verify="required" value="${user.userPhone}" autocomplete="off" placeholder="请输入电话" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">机构：</label>
        <div class="layui-input-inline">
            <input id="org" onclick="organ()" readonly="readonly" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色：</label>
        <div class="layui-input-inline">
            <input id="role" onclick="rol()" readonly="readonly" class="layui-input" type="text">
        </div>
    </div>
     <div class="layui-form-item">
        <label class="layui-form-label">用户类型：</label>
        <div class="layui-input-inline">
            <input id="userType" onclick="useT()" readonly="readonly" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户状态：</label>
        <div class="layui-input-inline">
            <input id="userState" onclick="stat()" readonly="readonly" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item" id="dButton" style="margin-left: 160px;margin-top: 20px">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="personFileEdit">保存</button>
        </div>
    </div>
</form>
</body>

<script type="text/javascript">
	 // 从作用域中获取数据
    var path = "${path}" ;
    var roleNames = "${roleNames}";
    var orgName = "${user.orgName}";
    var userGender = "${user.userGender}";
    var userState = "${user.userState}";
    var userType = "${user.userType}";
    
    $(function() {
    	//赋值
    	$("#userGender").val(userGender);
    	$("#org").val(orgName);
    	// 正则表达式 , 去掉特殊符号
    	$("#role").val(roleNames.replace(/[^\u4e00-\u9fa5\w]/g, " "));
    	$("#userType").val(getUserType(userType));
    	$("#userState").val(getUserState(userState));
    	
    	layui.use([ 'layer', 'form' ], function() {
    		var form = layui.form, layer = layui.layer;
    		form.on('submit(personFileEdit)', function(data) {
    			var url = path + "/user/personFileEdit";
    			$.ajax({
    				url : url,
    				method : 'post',
    				data : data.field,
    				dataType : 'JSON',
    				success : function(data) {
    					if (data.success) {
    						layer.msg(data.msg, {
    							offset : [ '20%', '22%' ],
    							icon : 6,
    							anim : 6,
    							time : 1000
    						});
    					} else {
    						alert(data.msg);
    						// 刷新当前窗口
    						window.location.reload();
    						// 刷新整个页面
    						// parent.location.reload();
    					}
    				}
    			});
    			return false;
    		});
    	});
    });

    // 获取状态
    function getUserState(userState) {
    	switch (userState) {
    	case "1":
    		return '启用';
    	case "2":
    		return '停用';
    	}
    }
    // 获取类型
    function getUserType(userType) {
    	switch (userType) {
    	case "1":
    		return '管理员';
    	case "2":
    		return '普通用户';
    	}
    }
    // 这里更好的方法应该是找到一个可共用的方法
    function logN() {
    	layer.tips('不可编辑', '#loginName', {
    		tips : 2,
    		time : 1000
    	});
    }
    function useT() {
    	layer.tips('不可编辑', '#userType', {
    		tips : 2,
    		time : 1000
    	});
    }
    function organ() {
    	layer.tips('不可编辑', '#org', {
    		tips : 2,
    		time : 1000
    	});
    }
    function rol() {
    	layer.tips('不可编辑', '#role', {
    		tips : 2,
    		time : 1000
    	});
    }
    function stat() {
    	layer.tips('不可编辑', '#status', {
    		tips : 2,
    		time : 1000
    	});
    }
</script>

</html>
