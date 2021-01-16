<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="roleAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>角色名称</td>
                    <td><input name="roleName" autocomplete="off" type="text" placeholder="请输入角色名称" class="easyui-validatebox span2" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>角色排序</td>
                    <td><input name="roleSort" value="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
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
                    <td colspan="3"><textarea name="roleDescription" style="padding-left: 4px" rows="6" cols="50" ></textarea></td>
                </tr>
            </table>
        </form>
    </div>	
</div>

<script type="text/javascript">

	$(function () {
		//明显的jQuery
		$('#roleAddForm').form({
			url : '${path}/role/add',
			onSubmit : function() {
				
				progressLoad();
				var isValid = $(this).form('validate'); // 防止表单的重复提交
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
					//这里进行判断传回来的success是否是true,在controller层进行控制.
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');	
					layer.msg(result.msg);
				} else {
					layer.msg(result.msg);
				}
			}
		});
	});
</script>