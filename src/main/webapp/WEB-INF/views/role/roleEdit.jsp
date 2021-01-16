<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="roleEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>角色名称</td>
                    <td><input name="id" type="hidden"  value="${role.id}">
                    <input name="roleName" autocomplete="off" type="text" placeholder="请输入角色名称" class="easyui-validatebox span2" data-options="required:true" value="${role.roleName}"></td>
                </tr>
                <tr>
                    <td>角色排序</td>
                    <td><input name="roleSort" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" value="${role.roleSort}"></td>
                </tr>
                <tr>
                    <td>角色状态</td>
	                <td >
		                <select id="status" name="roleStatus" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1">启用</option>
                            <option value="2">停用</option>
		                </select>
	                </td>
                </tr>
                <tr>
                    <td>角色描述</td>
                    <td colspan="3"><textarea id="description" name="roleDescription" style="padding-left: 4px" rows="6" cols="50" ></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $('#roleEditForm').form({
            url:'${path}/role/edit',
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
                //关闭对话框
                parent.$.modalDialog.handler.dialog('close');
                //如果修改成功
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');    
                    layer.msg(result.msg);
                } else {
                    layer.msg(result.msg);
                }
            }
        });
        //由于这两个状态是通过数字形式在数据库中存在的.所以使用了vo视图时候的来映射数据.
        $("#description").val('${role.roleDescription}');
        $("#status").val('${role.roleStatus}');
    });
    </script>
    
    