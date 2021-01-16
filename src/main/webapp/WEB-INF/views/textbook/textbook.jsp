<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教材管理</title>
<script type="text/javascript">
    var dataGrid;
    $(function() {
    	dataGrid = $('#dataGrid').datagrid({
            url : '${path }/textbook/dataGrid',
            rownumbers : false,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'id',
            sortOrder : 'desc',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100],
            columns : [ [ {
                width : '50',
                title : '教材id',
                field : 'id',
                sortable : true,
                align : 'center'
            },{
                field : 'textbookName',
                title : '教材名称',
                width : 120,
                align : 'center'
            },{
                field : 'textbookTitle',
                title : '教材标题',
                width : 180,
                align : 'center'
            },{
                field : 'levelId',
                title : '等级',
                width : 80,
                align : 'center',
                //在这里将数据传回来的时候再进行处理,不在后端进行数据的处理.
                formatter : function(value, row, index) {
                    switch (value) {
	                    case 1:
	                        return 'level 1';
	                    case 2:
	                        return 'level 2';
	                    case 3:
	                        return 'level 3';
	                    case 4:
	                        return 'level 4';
	                    case 5:
	                        return 'level 5';
	                    case 6:
	                        return 'level 6';
	                    case 7:
	                        return 'level 7';
	                    case 8:
	                        return 'level 8';
	                    case 9:
	                        return 'level 9';
	                    case 10:
	                        return 'level 10';
                    }
                }
            }, {
                field : 'textbookImg',
                title : '教材封面',
                width : 150,
                align : 'center',
                formatter:showImg
            }, {
                field : 'totalCourses',
                title : '总课时',
                width : 80,
                align : 'center'
            }, {
                field : 'semesterId',
                title : '所属学期',
                width : 100,
                align : 'center',
                formatter : function(value, row, index) {
                    switch (value) {
	                    case "1":
	                        return '上学期';
	                    case "2":
	                        return '寒假';
	                    case "3":
	                        return '下学期';
	                    case "4":
	                        return '暑假';
	                    case "5":
	                        return '全学期';
                    }
                }
            }, {
                field : 'press',
                title : '出版社',
                width : 160,
                align : 'center'
            }, {
                field : 'textbookPrice',
                title : '市场价格',
                width : 80,
                align : 'center'
            }, {
                field : 'programTools',
                title : '编程工具',
                width : 100,
                align : 'center'
            }, {
                field : 'textbookState',
                title : '教材状态',
                width : 80,
                align : 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '已上架';
                    case 2:
                        return '已下架';
                    }
                }
            }, {
                field : 'textbookIntroduction',
                title : '教材简介',
                width : 160,
                align : 'center'
            },{
                field : 'createTime',
                title : '创建时间',
                width : 150,
                align : 'center'
            }, {
                field : 'action',
                title : '操作',
                width : 220,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
	                    <shiro:hasPermission name="textbook:upper">
	                    	str += $.formatString('<a href="javascript:void(0)" onclick="upperFun(\'{0}\');" >上架</a>', row.id);
	               	 	</shiro:hasPermission>
                        <shiro:hasPermission name="textbook:lower">
                        	str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" onclick="lowerFun(\'{0}\');" >下架</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="textbook:edit">
                        	str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="textbook:delete">
                        	str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-del\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.organization-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'define-edit'});
                $('.organization-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'define-del'});
                $(this).treegrid("fixRowHeight");
            },
            toolbar : '#toolbar'
        });
	});
  	//显示图片
    function showImg(value, row, index){
    	if(row.textbookImg){
    		return '<img src="'+'${path }/file/download?fileName=' + row.textbookImg+'" width="130px" height="130px"/>';
    	}else{
    		return "";
    	}
    }
  	//编辑教材
    function editFun(id) {
         if (id != undefined) {
        	// dataGrid.datagrid('select', id);
        	 var rows = dataGrid.datagrid('getSelections');
         id = rows[0].id;
        }
       var node = dataGrid.datagrid('getSelected');
       if (node) {
            parent.$.modalDialog({
                title : '编辑',
                width : 700,
                height : 600,
                href : '${path }/textbook/editPage?id=' + id,
                buttons : [ {
                    text : '编辑',
                    handler : function() {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#textbookEditForm');
                        f.submit();
                    }
                } ]
            });
        }
    }
    //删除教材
    function deleteFun(id) {
        if (id != undefined) {
        	//dataGrid.datagrid('select', id);
        	 var rows = dataGrid.datagrid('getSelections');
          id = rows[0].id;
        }
       var node = dataGrid.datagrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除当前教材？删除当前教材会连同下属教材一起删除!', function(b) {
                if (b) {
                    progressLoad();//加载的进度条
                    $.post('${path }/textbook/delete', {
                        id : id
                    }, function(result) {
                        progressClose();
                        if (result.success) {
                            treeGrid.treegrid('reload');
                            layer.msg(result.msg);
                        }else{
                            layer.msg(result.msg);
                        }
                    }, 'JSON');
                }
            });
        }
    }
    //添加教材
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 700,
            height : 600,
            href : '${path }/textbook/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#textbookAddForm');
                    f.submit();
                }
            } ]
        });
    }
  //查询
    function searchFun() {
    	dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    //清除
    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }
    </script>
</head>
<body>
	 <div data-options="region:'north',border:false" style="height: 50px; line-height: 50px; margin-left: 2%; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <td>教材</td>
                    <td><input name="textbookName" autocomplete="off" placeholder="请输入教材名称"/></td>
                    <td> 
                    	<a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'define-search',plain:true" onclick="searchFun();">查询</a>
                        <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center',border:false" style="overflow: hidden;">
            <table id="dataGrid"></table>
        </div>
        
        <div id="toolbar" style="display: none;">
            <shiro:hasPermission name="textbook:add">
                <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'define-add'">添加</a>
            </shiro:hasPermission>
        </div>
    </div>
</body>
</html>