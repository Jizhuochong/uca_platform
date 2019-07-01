$(document).ready(function() {
	$('#list_data').datagrid({
		title : '机构用户列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/umuser/list',
		method : 'post',
		idField : 'userId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'userId',title : '编号'},
			{field : 'userAccount',title : '帐号'},
			{field : 'userName',title : '姓名'},
			{field : 'orgName',title : '机构(用户单位)'},
			{field : 'roleName',title : '用户角色'},
			{field : 'statusStr',title : '状态'},
			{field : 'createtimeStr',title : '创建日期'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.userId + ");' class='easyui-linkbutton'>修改</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.userId+ ");' class='easyui-linkbutton'>删除</a>";
			}}
		] ]
	});
	//设置分页控件  
	var pager = $('#list_data').datagrid('getPager');
	$(pager).pagination({
		beforePageText: '第',//页数文本框前显示的汉字  
	    afterPageText: '页    共 {pages} 页',  
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
    
    $("#sd").datebox({
    	formatter: formatDate
    });
    
    $("#ed").datebox({
    	formatter: formatDate
    });
    
    function formatDate(date) {
		return date.getFullYear() + "-" + (date.getMonth() +1) + "-" + date.getDate();
	}
});

function toSearch(){
	$('#list_data').datagrid('load',{
		uaccount:$("#uaccount").val(),
		uname:$("#uname").val(),
		orgId : $("#org_id_select_jsid").combobox('getValue'),
		sd:$('#sd').datebox('getValue'),
        ed:$('#ed').datebox('getValue')
	});
}

function clearForm() {
	$('#uaccount').val('');
	$('#uname').val('');
	$("#org_id_select_jsid").combobox('clear');
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}

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

function toEdit(){
	var row = $('#list_data').datagrid('getSelected');
	if (row){
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
	} else {
		parent.$.messager.alert('提示', "请选择要修改的数据！");
	}
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
//                $('#list_data').datagrid('reload');
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
    	    		$('#list_data').treegrid('reload');
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

function toRemove(){
	var row = $('#list_data').datagrid('getSelected');
	if (row){
		parent.$.messager.confirm('确认','是否确认要删除选择的用户?',function(r){
			if (r){
				$.post(rfPath+'/umuser/delete',{id:row.userId},function(result){
				    if (result.success){
				        $('#list_data').datagrid('reload');
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
