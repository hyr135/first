<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
	var resourceTree;
	 $(function() {
	        // 加载 所有的权限 (通过树的形式)
	        resourceTree = $('#resourceTree').tree({
	            url : '${path}/resource/FindAllRoleTrees',
	            parentField : 'pid',
	            lines : true,
	            checkbox : true,
	            onClick : function(node) {
	            },
	            onLoadSuccess : function(node, data) {
	                progressLoad();
	                $.post( '${path }/role/findResourceIdListByRoleId', {
	                    id : '${roleId}'
	                }, function(result) {
	                    var ids;
	                    if (result.success == true && result.obj != undefined) {
	                        ids = $.stringToList(result.obj + '');
	                    }
	                    if (ids.length > 0) {
	                        for ( var i = 0; i < ids.length; i++) {
	                            if (resourceTree.tree('find', ids[i])) {
	                                resourceTree.tree('check', resourceTree.tree('find', ids[i]).target);
	                            }
	                        }
	                    }
	                }, 'json');
	                progressClose();
	            },
	            cascadeCheck : false,
	            onCheck: function (node, checked) {
	                // 选中时一般不进行操作 让用户自己选中
	                if (checked) {
	                    var parentNode = $("#resourceTree").tree('getParent', node.target);
	                    if (parentNode != null) {
	                        $("#resourceTree").tree('check', parentNode.target);
	                    }
	                }
	                // 取消时一般进行全部取消
	                else {
	                    var childNode = $("#resourceTree").tree('getChildren', node.target);
	                    if (childNode.length > 0) {
	                        for (var i = 0; i < childNode.length; i++) {
	                            $("#resourceTree").tree('uncheck', childNode[i].target);
	                        }
	                    }
	                }
	            }
	        });

	        //提交修改的js函数,使用的是表单提交
	       $('#roleGrantForm').form({
	            url : '${path }/role/grant',
	            onSubmit : function() {
	                progressLoad();
	                //this,who invoke this function its object is that
	                var isValid = $(this).form('validate');
	                if (!isValid) {
	                    progressClose();
	                }
	                var checknodes = resourceTree.tree('getChecked');
	                //判断为空的情况
	                if(checknodes.length ==0){
	                	layer.msg("请至少选择一个权限");
	                }else{
		                var ids = [];
		                if (checknodes && checknodes.length > 0) {
		                    for ( var i = 0; i < checknodes.length; i++) {
		                        ids.push(checknodes[i].id);
		                    }
		                }
		                $('#resourceIds').val(ids);
	                }
	                return isValid;
	            },
	            success : function(result) {
	                result = $.parseJSON(result);
	                progressClose();
	                parent.$.modalDialog.handler.dialog('close');
	                if (result.success) {
	                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
	                    layer.msg(result.msg);
	                    
	                    parent.location.reload();
	                } else {
	                    layer.msg(result.msg);
	                }
	            }
	        });
	    });
	 
		//选中所有
	    function checkAll() {
	        var nodes = resourceTree.tree('getChecked', 'unchecked');
	        if (nodes && nodes.length > 0) {
	            for ( var i = 0; i < nodes.length; i++) {
	                resourceTree.tree('check', nodes[i].target);
	            }
	        }
	    }
		//全不选
	    function uncheckAll() {
	        var nodes = resourceTree.tree('getChecked');
	        if (nodes && nodes.length > 0) {
	            for ( var i = 0; i < nodes.length; i++) {
	                resourceTree.tree('uncheck', nodes[i].target);
	            }
	        }
	    }


</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'" title="系统资源" style="width: 300px; padding: 1px;">
        <div class="well well-small">
            <form id="roleGrantForm" method="post">
                <input name="id" type="hidden" value="${roleId}" readonly="readonly">
                <ul id="resourceTree" style="margin-left: 36%"></ul>
                <input id="resourceIds" name="resourceIds" type="hidden" />
            </form>
        </div>
    </div>
    <div data-options="region:'south'" title="" style="overflow: hidden; padding:10px; padding-left: 36% ">
        <div>
            <button class="btn btn-success" onclick="checkAll();">全部选中</button>
            <button class="btn btn-inverse" style="margin-left: 6%" onclick="uncheckAll()">取消选中</button>
        </div>
    </div>
</div>