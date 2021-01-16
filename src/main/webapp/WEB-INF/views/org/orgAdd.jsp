<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!-- 省市区三级联动 -->
<script type="text/javascript" src="${staticPath }/static/js/area.js"></script>
<script type="text/javascript" src="${staticPath }/static/js/select.js"></script>

<script type="text/javascript">
	$(function() {
		//获取机构树
		$('#orgPid').combotree({
			url : '${path}/org/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto'
		});
		//添加机构
		$('#orgAddForm').form({
			url : '${path }/org/add',
			onSubmit : function() {
				progressLoad();//加载的进度条
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();//关闭进度条
				}
				return isValid;
			},
			success : function(result) {
				result = $.parseJSON(result);
				progressClose();//关闭进度条
				parent.$.modalDialog.handler.dialog('close');
				if (result.success) {
					var x= parent.$.modalDialog.openner_treeGrid;
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为org.jsp页面预定义好了
					layer.msg(result.msg);
				} else {
					layer.msg(result.msg);
				}
			}
		});
	});
	
	//初始化上传控件
	$('#fileUpload').filebox({
		 buttonText: '选择文件',  //按钮文本
		 prompt:'选择后显示原文件全路径',
         buttonAlign: 'right',   //按钮对齐
         multiple: true,//允许多文件
         accept: "image/*", //指定文件类型
		onChange : function(e) {
			uploadFile("fileUpload");//上传处理
		}
	});
	
	//上传文件操作：参数是上传按钮的id值
    function uploadFile(fileUpload) {
        //获取文件
         var files = $("#" + fileUpload).next().find('input[type=file]')[0].files;
       	//构建一个FormData存储对象
         var formData = new FormData();
         for (var i = 0; i < files.length; i++) {
             formData.append('file', files[i]);
         }
         
         //上传,jquery 封装的最底层的ajax代码,功能也最全,但是代码写的也是最多的.
        $.ajax({
            url: '${path }/file/uploadFile', //单文件上传
            type: 'POST',
            dataType: 'json',
            contentType: false, // 不处理类型
            processData: false,  // 不处理参数
            data: formData,
            success: function (data) {
            	//给页面的路径重新赋值
                var ref = $('#fileUpload').attr("data-ref");
                $("input[name='" + ref + "']").val(data.url);
                //回显到页面，即下载
                $("#huixian").html('<img src="'+'${path }/file/download?fileName=' + data.url+'" width="130px" height="130px"/>');
            },
            error: function (xhr, status, error) {
                $.messager.alert("提示", "操作失败");
            }
        });
    }
	
	
</script>

<div style="padding: 3px;">
	<form id="orgAddForm" method="post">
		<table class="grid">
			<tr>
				<td>机构编号</td>
				<td><input name="orgCode" autocomplete="off" type="text" placeholder="请输入机构编号" class="easyui-validatebox span2" data-options="required:true"></td>
				<td>机构名称</td>
				<td><input name="orgName" autocomplete="off" type="text" placeholder="请输入机构名称" class="easyui-validatebox span2" data-options="required:true"></td>
			</tr>
			<tr>
				<td>联系人</td>
				<td><input name="orgPerson" autocomplete="off" type="text" placeholder="请输入机构联系人" class="easyui-validatebox span2" data-options="required:true"></td>
				<td>联系方式</td>
				<td><input name="orgPhone" autocomplete="off" type="text" placeholder="请输入机构联系方式" class="easyui-validatebox span2" data-options="required:true"></td>
			</tr>
			<tr>
			 	<td>业绩归属人</td>
				<td><input name="orgBoss" autocomplete="off" type="text" placeholder="请输入业绩归属人姓名" class="easyui-validatebox span2" data-options="required:true" /></td>
				<td>菜单图标</td>
				<td><input name="orgIcon" autocomplete="off" type="text" placeholder="请输入菜单图标"   class="easyui-validatebox span2" value="define-depart"></td>
			</tr>
			<tr>
				<td>上级机构</td>
				<td><select id="orgPid" name="orgPid" style="width: 200px; height: 29px;"></select> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#orgPid').combotree('clear');">清空</a></td>
				<td>排序</td>
				<td><input name="orgSort" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" value="0"></td>
			</tr>
			<tr>
				<td>机构学段</td>
				<td><select name="orgStage" style="width: 200px; height: 29px;">
						<option value="1" selected="selected">小学</option>
						<option value="2">初中</option>
						<option value="3">高中</option>
				</select></td>
			</tr>
			<tr>
				<td>电脑配置</td>
				<td><select name="orgComputer" style="width: 200px; height: 29px;">
						<option value="1" selected="selected">人手一台</option>
						<option value="2">多人共用</option>
				</select></td>
			</tr>
			<tr>
				<td>省:</td>
				<td><select name="orgProvince" id="province" style="width: 200px; height: 29px;"><option value="请选择">请选择</option></select></td>
			</tr>
			<tr>
				<td>市:</td>
				<td><select name="orgCity" id="city" style="width: 200px; height: 29px;"><option value="请选择">请选择</option></select></td>
			</tr>
			<tr>
				<td>县区:</td>
				<td><select name="orgCounty" id="town" style="width: 200px; height: 29px;"><option value="请选择">请选择</option></select></td>
			</tr>
			<tr>
				<td>详细地址</td>
				<td colspan="3"><input name="orgAddress" autocomplete="off" placeholder="请输入详细地址" type="text" class="span2" style="width: 300px;" /></td>
			</tr>
			<tr>
				<td>机构封面</td>
				<td>
						<form id='file' class="easyui-form" method="post" enctype="multipart/form-data">
								<!-- 用于图片回显 -->
								<div id="huixian"></div>
								<!-- data-ref的值必须要跟 隐藏域的name属性值一直，不然赋值不成功-->
								<input id="fileUpload" data-ref="orgImg" name="file" class="easyui-filebox" style="width: 300px;" /> 
								<input type="hidden" name="orgImg" value="">
						</form>
				
				</td>
			</tr>
		</table>
	</form>
</div>