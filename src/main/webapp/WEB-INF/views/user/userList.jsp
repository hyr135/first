<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 50px; line-height: 50px; margin-left: 2%; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="userName" autocomplete="off" placeholder="请输入用户姓名"/></td>
                    <th>创建时间:</th>
                    <td>
                        <input name="createdateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                            至
                        <input  name="createdateEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                        <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'define-search',plain:true" onclick="searchFun();">查询</a>
                        <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'west',border:true,split:false,title:'组织机构'"  style="width:150px;overflow: hidden; ">
        <ul id="orgTree"  style="width:160px;margin: 10px 10px 10px 10px">
        </ul>
    </div>
    <div id="toolbar" style="display: none;">
        <shiro:hasPermission name="user:add">
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'define-add'">添加</a>
        </shiro:hasPermission>
    </div>
</body>
<script type="text/javascript">
    var dataGrid;
    var orgTree;
    $(function() {
        orgTree = $('#orgTree').tree({
            url : '${path}/org/tree',
            parentField : 'pid',
            lines : true,
            onClick : function(node) {
                var childrenNodes = $('#orgTree').tree('getChildren', node.target);
                //判断是否为空
                var nodeId = node.id ;
                if (Object.keys(childrenNodes).length != 0){
                    for (var childrenNode in childrenNodes){
                        nodeId += ","+childrenNodes[childrenNode].id
                    }
                }
                dataGrid.datagrid('load', {
                    orgId: nodeId
                });
            }
        });

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/user/dataGrid',//从远程站点请求数据的 URL。
            fit : true,//设置为 true，则会自动扩大或缩小列的尺寸以适应网格的宽度并且防止水平滚动。
            rownumbers : false,//设置为 true，则显示带有行号的列。
            pagination : true,//设置为 true，则在数据网格（datagrid）底部显示分页工具栏。
            singleSelect : true,//设置为 true，则只允许选中一行。
            idField : 'id',//id字段名称
            sortName : 'id',//排序字段名称
            sortOrder : 'asc',//排序规则
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '100',
                title : '账号',
                field : 'loginName',
                sortable : true,
                align : 'center'
            }, {
                width : '100',
                title : '姓名',
                field : 'userName',
                sortable : true,
                align : 'center'
            },{
                width : '50',
                title : '部门ID',
                field : 'orgId',
                hidden : true,//隐藏
                //sortable : true,
                align : 'center'
            },{
                width : '200',
                title : '所属部门',
                field : 'orgName',
                align : 'center'
            },{
                width : '150',
                title : '创建时间',
                field : 'createTime',
                sortable : true,
                align : 'center'
            },  {
                width : '50',
                title : '性别',
                field : 'userGender',
                sortable : true,
                align : 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 1:
                        return '男';
                    case 2:
                        return '女';
                    }
                }
            }, {
                width : '50',
                title : '年龄',
                field : 'userAge',
                sortable : true,
                align : 'center'
            },{
                width : '120',
                title : '电话',
                field : 'userPhone',
                sortable : true,
                align : 'center'
            }, 
            {
                width : '400',
                title : '角色',
                field : 'rolesList',
                align : 'center',
                formatter : function(value, row, index) {
                    var roles = [];
                    for(var i = 0; i< value.length; i++) {
                        roles.push(value[i].roleName);  
                    }
                    return(roles.join('\n'));
                }
            }, {
                width : '80',
                title : '用户类型',
                field : 'userType',
                sortable : true,
                align : 'center',
                formatter : function(value, row, index) {
                    if(value == 1) {
                        return "管理员";
                    }else if(value == 2) {
                        return "普通用户";
                    }
                    return "未知类型";
                }
            },{
                width : '60',
                title : '用户状态',
                field : 'userState',
                sortable : true,
                align : 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '启用';
                        case 2:
                            return '停用';
                    }

                }
            } , {
                field : 'action',
                title : '操作',
                width : '180',
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="user:edit">
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'define-edit'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'define-del'});
                $(this).datagrid("fixRowHeight");
            },
            toolbar : '#toolbar'
        });
    });
    
  //用户添加
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 300,
            href : '${path }/user/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
  //用户删除
  function deleteFun(id){
	  if (id == undefined) {//点击右键菜单才会触发这个
          var rows = dataGrid.datagrid('getSelections');
          id = rows[0].id;
      } else {//点击操作里面的删除图标会触发这个
          dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
      }
	  
	  parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
          if (b) {
              progressLoad();
              $.post('${path }/user/delete', {
                  id : id
              }, function(result) {
                  progressClose();
                  if (result.success) {
                      dataGrid.datagrid('reload');
                      layer.msg(result.msg);
                  }else{
                      layer.msg(result.msg);
                  }
              }, 'JSON');
          }
      });
	  
  }
  
  
//用户编辑
  function editFun(id) {
      if (id == undefined) {
          var rows = dataGrid.datagrid('getSelections');
          id = rows[0].id;
      } else {
          dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
      }
      parent.$.modalDialog({
          title : '编辑',
          width : 500,
          height : 300,
          href : '${path }/user/editPage?id=' + id,
          buttons : [ {
              text : '确定',
              handler : function() {
                  parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                  var f = parent.$.modalDialog.handler.find('#userEditForm');
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
</html>