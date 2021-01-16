<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构管理</title>
<script type="text/javascript">
    var treeGrid;
    $(function() {
    	treeGrid = $('#treeGrid').treegrid({
            url : '${path }/org/treeGrid',
            idField : 'id',
            treeField : 'name',
            parentField : 'pid',
            rownumbers : false ,
            fit : true,
            fitColumns : false,
            border : false,
            animate : true,
            frozenColumns : [ [ {
                title : 'id',
                field : 'id',
                width : 40,
                hidden : true,
                align : 'center'
            } ] ],
            columns : [ [ {
                field : 'orgCode',
                title : '机构编号',
                width : 100,
                align : 'center'
            },{
                field : 'orgName',
                title : '机构名称',
                width : 150,
                align : 'center'
            },{
                field : 'orgPid',
                title : '上级机构ID',
                width : 50,
                hidden : true,//隐藏
                align : 'center'
            }, {
                field : 'orgAddress',
                title : '机构地址',
                width : 180,
                align : 'center'
            }, {
                field : 'orgPerson',
                title : '联系人',
                width : 80,
                align : 'center'
            }, {
                field : 'orgPhone',
                title : '联系方式',
                width : 100,
                align : 'center'
            }, {
                field : 'orgStageName',
                title : '机构学段',
                width : 80,
                align : 'center'
            }, {
                field : 'orgComputerName',
                title : '电脑配置',
                width : 80,
                align : 'center'
            }, {
                field : 'orgBoss',
                title : '业绩归属人',
                width : 80,
                align : 'center'
            }, {
                field : 'orgProvince',
                title : '省',
                width : 80,
                align : 'center'
            }, {
                field : 'orgCity',
                title : '市',
                width : 80,
                align : 'center'
            }, {
                field : 'orgCounty',
                title : '县/区',
                width : 80,
                align : 'center'
            },  {
                field : 'orgSort',
                title : '排序',
                width : 40,
                align : 'center'
            },  {
                field : 'orgIcon',
                title : '图标',
                width : 100,
                align : 'center'
            }, {
                field : 'orgImg',
                title : '机构封面',
                width : 200,
                align : 'center',
                //这是显示机构图片的测试
                formatter:function showImg(value, row, index){
                		   	if(row.orgImg){
                				return '<img src="'+'${path }/file/download?fileName=' + row.orgImg+'" width="130px" height="130px"/>';
                			}else{
                				return "";
                			}
                		  }
                
            },{
                field : 'createTime',
                title : '创建时间',
                width : 150,
                align : 'center'
            }, {
                field : 'action',
                title : '操作',
                width : 130,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="org:edit">
                            str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="org:delete">
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
    
  //添加机构
    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 700,
            height : 600,
            href : '${path }/org/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#orgAddForm');
                    f.submit();
                }
            } ]
        });
    }
  	//删除机构
    function deleteFun(id) {
        if (id != undefined) {
            treeGrid.treegrid('select', id);
        }
       var node = treeGrid.treegrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除当前机构？删除当前机构会连同下属机构一起删除!', function(b) {
                if (b) {
                    progressLoad();//加载的进度条
                    $.post('${path }/org/delete', {
                        id : id
                    }, 
                    //回调函数,显示操作的结果
                    function(result) {
                        progressClose();
                        if (result.success) {
                            treeGrid.treegrid('reload');
                            layer.msg(result.msg);
                        }else{
                            layer.msg(result.msg);
                        }
                    }, 
                    //要求返回的数据为json格式
                    'JSON');
                }
            });
        }
    }
  

  	
  //编辑机构
    function editFun(id) {
         if (id != undefined) {
            treeGrid.treegrid('select', id);
        }
       var node = treeGrid.treegrid('getSelections');
       if (node) {
            parent.$.modalDialog({
                title : '编辑机构信息',
                width : 700,
                height : 600,
                href : '${path }/org/editPage?id=' + id,
                buttons : [ {
                    text : '确认修改',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#orgEditForm');
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
        <div data-options="region:'center',border:false" style="overflow: hidden;">
            <table id="treeGrid"></table>
        </div>
        
        <div id="toolbar" style="display: none;">
            <shiro:hasPermission name="org:add">
                <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'define-add'">添加</a>
            </shiro:hasPermission>
        </div>
    </div>
</body>
</html>