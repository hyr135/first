<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!-- 省市区三级联动 -->
<script type="text/javascript" src="${staticPath }/static/js/area.js"></script>
<script type="text/javascript" src="${staticPath }/static/js/select.js"></script>

<script type="text/javascript">

    $(function() {
        $('#orgPid').combotree({
            url : '${path}/org/tree?flag=false',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value :'${org.orgPid}'
        });
        
        $('#orgEditForm').form({
            url : '${path }/org/edit',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                parent.$.modalDialog.handler.dialog('close');
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为organization.jsp页面预定义好了
                    layer.msg(result.msg);
                }else{
                    layer.msg(result.msg);
                }
            }
        });
        
    });
</script>
<div style="padding: 3px;">
    <form id="orgEditForm" method="post">
    <table class="grid">
			<tr>
				<td>机构编号</td>
				<td>
					<input name="id" type="hidden"  value="${org.id}">
                    <input name="orgCode" type="text" value="${org.orgCode}" autocomplete="off" placeholder="请输入机构编号" class="span2" data-options="required:true"/>
				</td>
				<td>机构名称</td>
				<td><input name="orgName" type="text" value="${org.orgName}" autocomplete="off" placeholder="请输入机构名称" class="easyui-validatebox span2" data-options="required:true"/></td>
			</tr>
			<tr>
				<td>联系人</td>
				<td><input name="orgPerson" type="text" value="${org.orgPerson}" placeholder="请输入机构联系人" autocomplete="off" class="easyui-validatebox span2" data-options="required:true"/></td>
				<td>联系方式</td>
				<td><input name="orgPhone"  type="text" value="${org.orgPhone}" placeholder="请输入机构联系方式" autocomplete="off" class="easyui-validatebox span2" data-options="required:true"/></td>
			</tr>
			<tr>
				<td>业绩归属人</td>
				<td><input name="orgBoss" type="text" value="${org.orgBoss}" placeholder="请输入业绩归属人姓名" autocomplete="off" class="easyui-validatebox span2" data-options="required:true" /></td>
				<td>菜单图标</td>
				<td><input name="orgIcon" value="${org.orgIcon}" autocomplete="off" type="text" placeholder="请输入菜单图标"  class="easyui-validatebox span2"></td>
			</tr>
			<tr>
				<td>上级机构</td>
				<td><select id="orgPid" name="orgPid" style="width: 200px; height: 29px;"></select>
					<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#orgPid').combotree('clear');">清空</a>
				</td>
				<td>排序</td>
				<td><input name="orgSort" value="${org.orgSort}" class="easyui-numberspinner" required="required" data-options="editable:false"/></td>
			</tr>
			<tr>
				<td>机构学段</td>
				<td><select name="orgStage" style="width: 200px; height: 29px;">
						<option value="1" <c:if test="${org.orgStage eq '1'}">selected</c:if>>小学</option>
						<option value="2" <c:if test="${org.orgStage eq '2'}">selected</c:if>>初中</option>
						<option value="3" <c:if test="${org.orgStage eq '3'}">selected</c:if>>高中</option>
				</select></td>
			</tr>
			<tr>
				<td>电脑配置</td>
				<td><select name="orgComputer" style="width: 200px; height: 29px;">
						<option value="1" <c:if test="${org.orgComputer eq '1'}">selected</c:if>>人手一台</option>
						<option value="2" <c:if test="${org.orgComputer eq '2'}">selected</c:if>>多人共用</option>
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
				<td colspan="3"><input name="orgAddress" type="text" value="${org.orgAddress}" autocomplete="off" placeholder="请输入详细地址" class="span2" style="width: 300px;" /></td>
			</tr>
			<tr>
				<td>机构封面</td>
				<td>
					<form id='file' class="easyui-form" method="post" enctype="multipart/form-data">
						<!-- 用于图片回显 -->
						<div id="huixian">
							<img src="${path }/file/download?fileName=${org.orgImg}" width="130px" height="130px"/>
						</div>
						<!-- data-ref的值必须要跟 隐藏域的name属性值一直，不然赋值不成功-->
						<input id="fileUpload" data-ref="orgImg" name="file" class="easyui-filebox" style="width: 300px;" /> 
						<input type="hidden" name="orgImg" value="">
					</form>
				</td>
			</tr>
		</table>
    </form>
</div>
<script type="text/javascript">
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
         
         //上传
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