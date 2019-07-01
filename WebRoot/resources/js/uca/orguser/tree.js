$(document).ready(function() {
	$('#tree_data').treegrid({
		url : rfPath + '/orgusertree/tree?pid=0',
		method : 'post',
		rownumbers : true,
		idField : 'id',
		treeField : 'name',
		border : false,
		fitColumns : true,
		fit : true,// 自动大小
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'name',title : '机构名称/用户名'},
		    {field : 'userAccount',title : '用户账号'},
		    {field : 'org',title : '用户单位'},
		    {field : 'roleName',title : '用户角色'},
		    {field : 'operation',title : '操作',formatter : function(value, row, index) {
		    	if(row.ty == '1') {
		    		return "";
		    	}
		    	if(row.ty == '2') {
		    		return "<a href='javascript:toEdit(\""+ row.id + "\");' class='easyui-linkbutton'>修改用户</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove(\""+ row.id+ "\");' class='easyui-linkbutton'>删除用户</a>";
		    	}
			}}
		] ]
	});
});

function selectRoles() {
	var roles = document.getElementsByName("roleIds");
	var objarray = roles.length;
	var checks = "";
	for (var i = 0; i < objarray; i++) {
		if (roles[i].checked == true) {
			checks += roles[i].value + ",";
		}
	}
	$("#ridflag").val(checks);
}

function toAdd(){
	$('#user_dialog').dialog('open').dialog('setTitle','添加用户');
	$('#user_dialog').dialog('center');
	$('#ua').removeAttr("readonly");
    $('#formName_user').form('clear');
    // 在保存操作时,如果主键名称不是"id"的话,需要默认给主键名称赋默认值,
    // 例如该例子,主键名称为"userId",必须赋默认值为"0",否则保存出错:400 Bad Request,
    // 如果主键名称是"id",则无需处理,这个应该是jquery easyui的默认配置.
    $("#uidflag").val("0");
}

function checkAccount() {
	var ua = $("#ua").val();
	$.ajax({
	    url: rfPath+'/umuser/checkAccount',
	    type: 'POST',
	    dataType: 'json',
	    data: {"uaccount":ua},
	    success: function(data){
	    	if(data.success) {
	    		parent.$.messager.alert('提示', data.msg);
	    		$("#ua").val("");
	    		$("#ua").focus();
	    		return;
	    	}
	    }
	});
}

function toEdit(id){
	var row = $('#tree_data').treegrid('find',id);
	if (row){
		if(row.ty == "1") {
			parent.$.messager.alert('提示', "您选择的是机构，不能修改，请重新选择要修改的用户数据！");
			return;
		}
		if(row.orgId != 22){
			toUserEdit(row);
		}else{
			toUserForJsdwEdit(row);
		}
		
	} else {
		parent.$.messager.alert('提示', "请选择要修改的数据！");
	}
}

function toUserEdit(row){
	$('#user_dialog').dialog('open').dialog('setTitle','修改用户');
	$('#user_dialog').dialog('center');
	$('#ua').attr("readonly","readonly");
	$('#formName_user').form('load',row);
	var rls = row.roles;
	if(rls != null) {
		var roles = document.getElementsByName("roleIds");
		var objarray = roles.length;
		// 下面两种方式都可以
		/* for (var i = 0; i < objarray; i++) {
			if(rls.indexOf(roles[i].value)>-1) {
				roles[i].checked = true;
			}
		} */
		var array = rls.split(",");
		for (var i = 0; i < objarray; i++) {
			if($.inArray(roles[i].value, array) > -1) {
				roles[i].checked = true;
			}
		}
	}
}

function toUserForJsdwEdit(row){
	var dataObj = null;
	dataObj = {id : row.userId};
	$.ajax({
	    url: rfPath + '/umuser/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: dataObj,
	    success: function(data){
//	    	$('#form_edit').form("clear");
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$("#uidJsdw").val(obj.userId);
	    		$("#user_jsdw_dialog input[name=userAccount]").val(obj.userAccount);
	    		$('#ua_jsdw').attr("readonly","readonly");
	    		$("#activeSt").combobox('setValue',obj.userStatus);
	    		$("#user_jsdw_dialog input[name=userPassword]").val(obj.userPassword);
	    		$("#user_jsdw_dialog input[name=phoneNum]").val(obj.phoneNum);
	    		$("#user_jsdw_dialog input[name=telephone]").val(obj.telephone);
	    		$("#user_jsdw_dialog input[name=userName]").val(obj.userName);
	    		$("#sex").combobox('setValue',obj.sex);
	    		$("#user_jsdw_dialog input[name=email]").val(obj.email);
	    		$("#user_jsdw_dialog input[name=devOrg]").val(obj.devOrg);
	    		$("#user_jsdw_dialog input[name=devOrgAddress]").val(obj.devOrgAddress);
	    		$("#user_jsdw_dialog input[name=remark]").val(obj.remark);
	    		$('#user_jsdw_dialog').dialog('open').dialog('setTitle','修改用户');
	    		$('#user_jsdw_dialog').dialog('center');
	    		
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function saveGrid(){
//    $('#formName_user').form('submit',{
//        url: rfPath+'/umuser/saveGrid',
//        onSubmit: function(){
//            return $(this).form('validate');
//        },
//        success: function(result){
//            var dataObj = eval('('+result+')');
//            if (dataObj.success){
//            	$('#user_dialog').dialog('close');
//                $('#tree_data').treegrid('reload');
//            } else {
//            	parent.$.messager.alert('提示', dataObj.msg);
//            }
//        }
//    });
    
    var options = {
    		type : 'post',
    		dataType : 'json',
    		url:rfPath+'/umuser/saveGrid',
    		success : function(data) {
    			if(data.success){
    	    		$('#user_dialog').dialog('close');
    	    		$('#tree_data').treegrid('reload');
    	    	}else{
    	    		parent.$.messager.alert('提示', data.msg);
    	    	}
    		}
    	};
    	parent.$.messager.confirm('Confirm','是否确定保存？',function(r){
          	if (r){
          		if($("#formName_user").form('validate')) {
          			$("#formName_user").ajaxSubmit(options);
          		}
          	}
     	});
}

function saveJsdwGrid(){
	var options = {
		type : 'post',
		dataType : 'json',
		url:rfPath+'/umuser/saveGridForJsdw',
		success : function(data) {
			if(data.success){
	    		$('#user_jsdw_dialog').dialog('close');
	    		$('#tree_data').treegrid('reload');
	    	}else{
	    		parent.$.messager.alert('提示', data.msg);
	    	}
		}
	};
	parent.$.messager.confirm('Confirm','是否确定保存？',function(r){
      	if (r){
      		if($("#formName_user_jsdw").form('validate')) {
      			$("#formName_user_jsdw").ajaxSubmit(options);
      		}
      	}
 	});
}

function toRemove(id){
	var row = $('#tree_data').treegrid('find',id);
	if (row){
		if(row.ty == "1") {
			parent.$.messager.alert('提示', "您选择的是机构，不能删除，请重新选择要删除的用户数据！");
			return;
		}
		var userId = id.split('_');
		parent.$.messager.confirm('确认','是否确认要删除选择的用户?',function(r){
			if (r){
				$.post(rfPath+'/umuser/delete',{id:userId[1]},function(result){
				    if (result.success){
				        $('#tree_data').treegrid('reload');
				    } else {
				    	parent.$.messager.alert('提示', result.msg);
				    }
				},'json');
			}
		});
	} else {
		parent.$.messager.alert('提示', "请选择要删除的数据！");
	}
}
