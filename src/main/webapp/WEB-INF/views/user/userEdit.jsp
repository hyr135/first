<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        var roleIds = ${roleIds};
        //获取机构树,是自定义下拉树
        $('#orgId').combotree({
            url : '${path}/org/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${user.orgId}'
        });
		//获取自定义下拉树
        $('#roleIds').combotree({
            url : '${path}/role/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            multiple : true,
            required : true,
            cascadeCheck : false,
            value : roleIds
        });

//回显
        $("#sex").val('${user.userGender}');
        $("#usertype").val('${user.userType}');
        $("#status").val('${user.userState}');
    });
    
    
    $('#userEditForm').form({
        url : '${path }/user/edit',
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
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');                    layer.msg(result.msg);
            } else {
                layer.msg(result.msg);
            }
        }
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="userEditForm" method="post">
            <div class="light-info" style="overflow: hidden;padding: 3px;">
                <div>密码不修改请留空。</div>
            </div>
            <table class="grid">
                <tr>
                    <td>账号</td>
                    <td><input name="id" type="hidden" value="${user.id}">
                    <input name="loginName" autocomplete="off" type="text" placeholder="请输入账号" class="easyui-validatebox span2" data-options="required:true" value="${user.loginName}"></td>
                    <td>姓名</td>
                    <td><input name="userName" autocomplete="off" type="text" placeholder="请输入姓名" class="easyui-validatebox span2" data-options="required:true" value="${user.userName}"></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input type="text" autocomplete="off" name="userPassword" class="span2" readonly="readonly" /></td>
                    <td>性别</td>
                    <td>
                        <select name="userGender" id="sex" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>年龄</td>
                    <td><input type="text" autocomplete="off" name="userAge" value="${user.userAge}" class="easyui-numberbox span2"/></td>
                    <td>用户类型</td>
                    <td>
                        <select name="userType" id="usertype" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1">管理员</option>
                            <option value="2">普通用户</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>机构</td>
                    <td>
                        <select name="orgId" id="orgId" autocomplete="off" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true"></select>
                    </td>
                    <td>角色</td>
                    <td><input name="roleIds" id="roleIds" autocomplete="off" style="width: 140px; height: 29px;"/></td>
                </tr>
                <tr>
                    <td>电话</td>
                    <td>
                        <input name="userPhone" autocomplete="off" type="text" class="easyui-numberbox span2" value="${user.userPhone}"/>
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