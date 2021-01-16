<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <title>日志管理</title>
    <script type="text/javascript">
        var dataGrid;
        $(function() {
        	//这种方式是使用javascript来声明一个datagrid
            dataGrid = $('#dataGrid').datagrid({
                url : '${path}' + '/log/dataGrid',
                //设置为 true , 则把行条纹化。（即奇偶行使用不同背景色）
                // striped : true,
                // 设置为true , 则显示带有行号的列
                rownumbers : true,
                // 是否启用分页
                pagination : true,
                // 是否设置单个选中
                // singleSelect : true,
                // 指定哪个字段是表示字段
                idField : 'id',
                // 定义可以排序的列
                // sortName : 'id',
                // 定义列的排序顺序
                // sortOrder : 'asc',
                // 当设置了 pagination 属性时, 初始化页面尺寸
                pageSize : 18,
                // 当设置了 pagination 属性时,初始化页面尺寸的选择列表
                pageList : [ 10, 18, 36, 40, 50, 100, 200, 300, 400, 500 ],
                // frozenColumns 所指向的列会被冻结在左边
                columns : [ [ {
                    width : '100',
                    title : '日志id',
                    field : 'id',
                    align : 'center',
                    hidden : true
                },{
                    field:'ck',
                    checkbox:true
                }, {
                    width : '130',
                    title : '操作',
                    field : 'operationName',
                    align : 'center'
                } , {
                    width : '340',
                    title : '请求类',
                    field : 'operationClass',
                    align : 'center'
                }, {
                    width : '140',
                    title : '请求路径',
                    field : 'operationAddress',
                    align : 'center'
                } , {
                    width : '160',
                    title : '请求参数',
                    field : 'operationParams',
                    align : 'center'
                } ,{
                    width : '90',
                    title : 'ip地址',
                    field : 'operationIp',
                    align : 'center'
                } ,{
                    width : '90',
                    title : '操作人',
                    field : 'loginName',
                    align : 'center'
                } ,{
                    width : '160',
                    title : '创建时间',
                    field : 'createTime',
                    align : 'center'
                },{
                    field : 'action',
                    title : '操作',
                    align : 'center',
                    width : 72,
                    formatter : function(value, row, index) {
                        var str = '';
                        str += $.formatString('<a href="javascript:void(0)" class="log-easyui-linkbutton" onclick="detailFun(\'{0}\');" >详情</a>', row.id);
                        return str;
                    }
                } ] ],
                onLoadSuccess:function(data){
                    $('.log-easyui-linkbutton').linkbutton({text:'详情',plain:true,iconCls:'define-list'});
                    $(this).datagrid("fixRowHeight");
                },
                // toolbar : '#toolbar'
            });
        });
        
      //日志详情
        function detailFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            } else {
                dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
            }
            //打开一个会话
            parent.$.modalDialog({
                title : '日志详情',
                width : 500,
                height : 300,
                href : '${path }/log/detailPage?id=' + id,
                buttons : [ {
                    text : '关闭',
                    handler : function() {
                        parent.$.modalDialog.handler.dialog('close');
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
		
      //批量删除
        function batchDeleteAll() {
    	  	
            var ids = getSelectRowsID();
            console.info(ids);
            if (ids=="" || ids == undefined){
            	return;
            }
            parent.$.messager.confirm('询问', '您是否要批量删除当前选择数据？', function(b) {
                if (b) {
                    progressLoad();
                    $.post('${path}/log/batchDelete', {
                        ids : ids
                    }, function(result) {
                        progressClose();
                        if (result.success) {
                            window.location.reload();
                            alert(result.msg);
                        }else{
                            layer.msg(result.msg);
                        }
                    }, 'JSON');
                }
            });
        }
      //获取选中数据网格的js代码.
      
      function getSelectRowsID(){
    	  //获取选中的中
    	  var objs = dataGrid.datagrid("getSelections");
    	  if(objs == null && objs.length == 0){
    		  layer.alert("请选择要删除的数据........");
    		  return "";
    	  }
    	  
    	  var data = "";
    	  for(var i = 0; i < objs.length; i++){
    		  if (data == "") {
				data = objs[i].id;
			}else{
				data = data +"," +objs[i].id;
				
			}
    	  }
    	  
    	  return data;
      }
      
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:true" style="height: 50px; line-height: 50px; margin-left: 2%; background-color: #fff;padding-top: 10px">
        <form id="searchForm">
            <table>
                <tr>
                    <th style="font-size: 14px">操作名称:</th>
                    <td><input name="operationName" autocomplete="off" style="padding-left: 6px" placeholder="请输入相应操作"/></td>
                    <th style="font-size: 14px">创建时间:</th>
                    <td>
                        <input name="createdateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                        至
                        <input  name="createdateEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                        <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'define-search',plain:true" onclick="searchFun();">查询</a>
                        <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空查询</a>
                    </td>
                    <shiro:hasPermission name="log:batchDelete">
                    <td>
                        <button style="color: red;" onclick="batchDeleteAll();" type="button">批量删除</button>
                    </td>
                    </shiro:hasPermission>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false">
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
</body>
</html>