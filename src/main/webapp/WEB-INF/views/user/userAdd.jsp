<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<<script type="text/javascript">
$(function() {
	//查询机构
    $('#orgId').combotree({
    	//调用机构查询已经写好的方法
        url : '${path}/org/tree',
        parentField : 'pid',
        lines : true,
        panelHeight : 'auto'
    });
	
	
});



//查询角色
$('#roleId').combotree({
    url: '${path }/role/tree',
    multiple: true,
    required: true,
    panelHeight : 'auto'
});


//提交用户
$('#userAddForm').form({
    url : '${path }/user/add',
    onSubmit : function() {
        progressLoad();
        var isValid = $(this).form('validate');
        if (!isValid) {
            progressClose();
        }
        return isValid;
    },
    success : function(result) {
        result = $.parseJSON(result);
        progressClose();
        parent.$.modalDialog.handler.dialog('close');
        if (result.success) {
            parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
            layer.msg(result.msg);
        } else {
            layer.msg(result.msg);
        }
    }
});

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="userAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>登录名</td>
                    <td><input name="loginName" autocomplete="off" type="text" placeholder="请输入账号" class="easyui-validatebox span2" data-options="required:true" ></td>
                    <td>姓名</td>
                    <td><input name="userName" autocomplete="off" type="text" placeholder="请输入姓名" class="easyui-validatebox span2" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input name="userPassword" autocomplete="off" type="password" placeholder="请输入密码" class="easyui-validatebox span2" data-options="required:true"></td>
                    <td>性别</td>
                    <td>
                        <select name="userGender" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1" >男</option>
                            <option value="2" >女</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>年龄</td>
                    <td><input name="userAge" autocomplete="off" placeholder="请输入年龄" type="text" class="easyui-numberbox span2"/></td>
                    <td>用户类型</td>
                    <td>
                        <select name="userType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1">管理员</option>
                            <option value="2" selected="selected">普通用户</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td><select id="orgId" name="orgId" style="width: 140px; height: 29px;" class="easyui-validatebox span2" data-options="required:true"></select></td>
                    <td>角色</td>
                    <td><select id="roleId" name="roleIds" style="width: 140px; height: 29px; " data-options="required:true"></select></td>
                </tr>
                <tr>
                    <td>电话</td>
                    <td>
                        <input name="userPhone" autocomplete="off" type="text" placeholder="请输入电话" class="easyui-numberbox span2"/>
                    </td>
                    <td>用户状态</td>
                    <td>
                        <select id="status" name="userState" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1">启用</option>
                            <option value="2">停用</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>