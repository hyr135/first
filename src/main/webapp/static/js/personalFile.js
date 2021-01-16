$(function() {
	$("#userGender").val(userGender);
	$("#org").val(orgName);
	// 正则表达式 , 去掉特殊符号
	$("#role").val(roleNames.replace(/[^\u4e00-\u9fa5\w]/g, " "));
	$("#userType").val(getUserType(userType));
	$("#userState").val(getUserState(userState));
	debugger
	layui.use([ 'layer', 'form' ], function() {
		var form = layui.form, layer = layui.layer;
		form.on('submit(personFileEdit)', function(data) {
			var url = path + "/user/personFileEdit";
			$.ajax({
				url : url,
				method : 'post',
				data : data.field,
				dataType : 'JSON',
				success : function(data) {
					if (data.success) {
						layer.msg(data.msg, {
							offset : [ '20%', '22%' ],
							icon : 6,
							anim : 6,
							time : 1000
						});
					} else {
						alert(data.msg);
						// 刷新当前窗口
						window.location.reload();
						// 刷新整个页面
						// parent.location.reload();
					}
				}
			});
			return false;
		});
	});
});

// 获取状态
function getUserState(userState) {
	switch (userState) {
	case "1":
		return '启用';
	case "2":
		return '停用';
	}
}
// 获取类型
function getUserType(userType) {
	switch (userType) {
	case "1":
		return '管理员';
	case "2":
		return '普通用户';
	}
}
// 这里更好的方法应该是找到一个可共用的方法
function logN() {
	layer.tips('不可编辑', '#loginName', {
		tips : 2,
		time : 1000
	});
}
function useT() {
	layer.tips('不可编辑', '#userType', {
		tips : 2,
		time : 1000
	});
}
function organ() {
	layer.tips('不可编辑', '#org', {
		tips : 2,
		time : 1000
	});
}
function rol() {
	layer.tips('不可编辑', '#role', {
		tips : 2,
		time : 1000
	});
}
function stat() {
	layer.tips('不可编辑', '#status', {
		tips : 2,
		time : 1000
	});
}