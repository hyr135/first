<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge" />
<title>资源管理</title>

<script type="text/javascript">
        var treeGrid;
        $(function() {
            treeGrid = $('#treeGrid').treegrid({
                url : '${path}/resource/treeGrid',// 获取资源树的数据
                idField : 'id',// 资源树中的id名称
                treeField : 'resourceName',// 资源树中的资源名称
                parentField : 'resourcePid',// 资源树中的上级id名称
                rownumbers : true,// 是否行数显示，true-是 false-否
                fit : true,  // 是否自适应父容器的大小
                fitColumns : false,// 是否自适应列的大小
                border : false,// 是否自设置边框
                animate : true,// 是否自动加载
                frozenColumns : [ [ {  //冻结某些列，这里冻结资源编号的列
                    title : '资源编号',
                    field : 'id',
                    width : 80,
                    align : 'center'
                } ] ],
                columns : [ [ {
                    field : 'resourceName',
                    title : '资源名称',
                    width : 200
                }, {
                    field : 'resourceUrl',
                    title : '资源路径',
                    width : 300,
                    align : 'center'
                },{
                    field : 'resourcePermission',
                    title : '资源权限',
                    width : 200,
                    align : 'center'
                }, {
                    field : 'resourceSort',
                    title : '资源排序',
                    width : 80,
                    align : 'center'
                }, {
                    field : 'resourceIcon',
                    title : '资源图标',
                    width : 200,
                    align : 'center'
                }, {
                    field : 'resourceType',
                    title : '资源类型',
                    width : 80,
                    align : 'center',
                    // 单元格的格式化函数
                    formatter : function(value, row, index) {
                        switch (value) {
                            case 1:
                                return '菜单';
                            case 2:
                                return '按钮';
                        }
                    }
                }, {
                    field : 'resourcePid',
                    title : '上级资源ID',
                    width : 150,
                    hidden : true,
                    align : 'center'
                }, {
                    field : 'resourceStatus',
                    title : '资源状态',
                    width : 80,
                    align : 'center',
                    formatter : function(value, row, index) {
                        switch (value) {
                            case 1:
                                return '启用';
                            case 2:
                                return '停用';
                        }
                    }
                }, {
                    field : 'action',
                    title : '操作',
                    width : 200,
                    align : 'center',
                    formatter : function(value, row, index) {
                        var str = '';
                        <shiro:hasPermission name="resource:edit">
                        str += $.formatString('<a href="javascript:void(0)" class="resource-easyui-linkbutton-edit" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="resource:delete">
                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        str += $.formatString('<a href="javascript:void(0)" class="resource-easyui-linkbutton-del" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                        return str;
                    }
                } ] ],
                onLoadSuccess:function(data){
                    $('.resource-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'define-edit'});
                    $('.resource-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'define-del'});
                    $(this).treegrid("fixRowHeight");
                },
                toolbar : '#toolbar'
            });
        });
        
        
      //添加资源
        function addFun() {
            parent.$.modalDialog({
                title : '添加',
                width : 500,
                height : 350,
                href : '${path}/resource/addPage',
                buttons : [ {
                    text : '添加',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = treeGrid; //因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#resourceAddForm');
                        f.submit();
                    }
                } ]
            });
        }
      
      
        //删除资源，其中id是参数，这个参数可以在点击事件的时候进行控制，比如点击的哪一个就使用this就是点解的该对象
        function deleteFun(id) {
            if (id != undefined) {
                treeGrid.treegrid('select', id);
            }
            var node = treeGrid.treegrid('getSelected');
            if (node) {
            	/* '询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!' 这是一个参数，这个参数是一个string类型数据
            	在进行删除的时候回弹出一个弹窗，在弹窗中显示的内容就是这个参数的数据，第二个参数是一个回调函数（也称为事件）
            	也就是当弹窗中选择的是是的时候b这个参数的值就是true，这个参数可以不用声明是因为js是一种弱类型语言。
            	就会执行花括号的内容，反之亦然。
            	
            	*/
                parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
                    if (b) {
                        progressLoad();
                        // Jquery 简单的 Post 请求函数，使用post请求将参数到后台，通过ajax技术（浏览器端技术，使用同步或者异步请求）
                        $.post('${pageContext.request.contextPath}/resource/delete', {
                            id : id
                        }, function(result) {
                        	//注意这个参数的值是要跟后台返回的数据同步，不然就出现接收不到数据的境地
                            progressClose();
                            if (result.success) {
                            	//如果result中的success属性是true就是可以执行这个。代码块。
                                parent.$.messager.alert('提示', result.msg, 'info');
                                treeGrid.treegrid('reload');
                                parent.location.reload();
                            }else{
                                layer.msg(result.msg);
                            }
                        }, 'JSON');
                        //声明数据格式是json格式（一种在js领域使用非常广泛的数据格式，仅仅是一种数据格式而已不用想的太复杂，有时候有的 人就喜欢把简单东西
                        //东西复杂化，这是很难理解的行为，抛开晦涩难懂的人为干预字面意思，应该概念的东西就要简洁明了。所以多看英文的原本定位声明。有助于理解。
                        
                    }
                });
           }
        }
        //id是从点击的地方,获取的当前对象
        function editFun(id){
        	if (id != undefined) {
        		 treeGrid.treegrid('select', id);
			}
        	//获取选中行的参数值
        	  var node = treeGrid.treegrid('getSelected');
        	if (node) {
				parent.$.modalDialog({
					//设置属性值
                    title : '编辑',
                    width : 500,
                    height : 350,
                    href : '${path}/resource/editPage?id=' + id,
                    buttons : [ {
                        text : '确定',
                        handler : function() {
                        	parent.$.modalDialog.openner_treeGrid = treeGrid;  
                        	var f = parent.$.modalDialog.handler.find('#resourceEditForm');
                            f.submit();
                        	
                            
                        }
                    } ]
                });
			}
        	
        	
        }
        
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"  style="overflow: hidden;">
        <table id="treeGrid"></table>
    </div>
</div>
<div id="toolbar" style="display: none;">
<!-- 添加权限控制 -->
<shiro:hasPermission name="resource:add">
<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'define-add'">添加</a>
</shiro:hasPermission>
</div>
</body>
</html>

