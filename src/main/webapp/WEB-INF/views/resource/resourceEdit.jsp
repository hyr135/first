<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript" src="${path}/static/layui/layui.js"></script>
<script type="text/javascript" src="${path}/static/js/index.js"></script>
<script type="text/javascript" src="${path}/static/js/bodyTab.js"></script>
<script type="text/javascript" src="${path}/static/js/leftNav.js"></script>

<script type="text/javascript">
	/* //页面一加载就会执行这一个脚本.
	$(function(){
		
		//加载菜单树
		$('pid').combotree({
            url : '${path}/resource/findMenuTree',
           
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${resource.resourcePid}'
        });
		
		 //编辑资源
        $('#resourceEditForm').form({
            url : '${path}/resource/edit',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
                    // 刷新页面
                    parent.location.reload();
                }else{
                    layer.msg(result.msg)
                }
            }
        });
        $("#status").val('${resource.resourceStatus}');
        $("#resourcetype").val('${resource.resourceType}');
}); */
		
	
	
	 $(function() {
	    	//加载菜单树
	        $('#pid').combotree({
	            url : '${path}/resource/findMenuTree',
	            parentField : 'pid',
	            lines : true,
	            panelHeight : 'auto',
	            value : '${resource.resourcePid}'
	        });
	        //编辑资源
	        $('#resourceEditForm').form({
	            url : '${path}/resource/edit',
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
	                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
	                    // 刷新页面
	                    parent.location.reload();
	                }else{
	                    layer.msg(result.msg)
	                }
	            }
	        });
	        $("#status").val('${resource.resourceStatus}');
	        $("#resourcetype").val('${resource.resourceType}');
	    });
</script>
<div style="padding: 3px;">
    <form id="resourceEditForm" method="post">
        <table class="grid">
            <tr>
                <td>资源名称</td>
                <%--  通过el表达式的方式加载  --%>
                <td><input name="id" type="hidden"  value="${resource.id}" >
                <input name="resourceName" type="text" placeholder="请输入资源名称" value="${resource.resourceName}" class="easyui-validatebox span2" autocomplete="off" data-options="required:true" ></td>
                <td>资源类型</td>
                <td>
                    <select id="resourcetype" name="resourceType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="1">菜单</option>
                        <option value="2">按钮</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>资源路径</td>
                <td><input name="resourceUrl" type="text" value="${resource.resourceUrl}" autocomplete="off" placeholder="请输入资源路径" class="span2" ></td>
                <td>资源权限</td>
                <td><input name="resourcePermission" type="text" value="${resource.resourcePermission}" autocomplete="off" placeholder="请输入资源权限标识符" class="span2" ></td>
            </tr>
            <tr>
                <td>排序</td>
                <td><input name="resourceSort" value="${resource.resourceSort}"  class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                <td>菜单图标</td>
                <td><input name="resourceIcon" autocomplete="off" type="text" placeholder="请输入菜单图标" class="span2" value="${resource.resourceIcon}"/></td>
            </tr>
            <tr>
                <td>状态</td>
                <td >
                    <select id="status" name="resourceStatus" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="1">启用</option>
                        <option value="2">停用</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>上级资源</td>
                <td colspan="3">
                    <select id="pid" name="resourcePid" style="width: 200px; height: 29px;"></select>
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>