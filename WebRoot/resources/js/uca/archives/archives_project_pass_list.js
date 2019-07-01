$(document).ready(function() {
	$('#list_data').datagrid({
		title : '工程档案档号文件重新命名',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/projectpass/list',
		method : 'post',
		idField : 'archivesId',
		pagination : true,// 分页控件
		columns : [ [
			{field : 'projectName',title : '工程名称'},
		    {field : 'archivesNum',title : '工程档案电子编号'},
			{field : 'orderNum',title : '调卷单编号'},
			{field : 'handingPersonName',title : '审核人'},
			{field : 'checkTime',title : '审核时间'},
			{field : 'checkStatus',title : '审核状态', formatter: function(value,row,index){
	           	 var rtn = "";
	        	 switch(row.checkStatus){
	        	 	case 1:
	        	 		rtn = "待审核";
	        	 		break;
	        	 	case 3:
	        	 		rtn = "审核通过";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
			{field : 'userName',title : '申请人'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				var operator = "";
				if(row.renameStatus == 1) {
					operator = "<a href='javascript:toRenameArchives("+ row.archivesId+ ",\""+row.archivesNum+"\");' class='easyui-linkbutton'>上传MIS系统</a>";
				}
				if(row.renameStatus == 2) {
					operator = "<a href='javascript:toRenameArchives("+ row.archivesId+ ",\""+row.archivesNum+"\");' class='easyui-linkbutton'>再次上传MIS系统</a>";
				}
				return operator;
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
});

function toRenameArchives(archivesId, archivesNum){
	$("#archives_id_jsid").val(archivesId);
	$("#archives_num_jsid").val(archivesNum);
	$('#obj_dialog').dialog('open').dialog('setTitle','调整工程档案档号');
}

function renameArchives() {
	var archives_id_jsid = $("#archives_id_jsid").val(), archives_num_jsid = $("#archives_num_jsid").val();

	if(archives_id_jsid == null || archives_id_jsid.length == 0 || archives_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择调整工程档案档号的数据记录！");
		return;
	}
	
	if(archives_num_jsid == null || archives_num_jsid.length == 0 || archives_num_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程档案档号！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定调整工程档案档号？',function(r){
	  	if (r){
			$.ajax({
			    url: rfPath + '/projectpass/renameArchives',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	archivesId : archives_id_jsid,
			    	archivesNum : archives_num_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#obj_dialog').dialog('close');
			    	} else {
			    		parent.$.messager.alert('提示', data.msg);
			    	}
			    },
			    error: function(){
			    	parent.$.messager.alert('提示', "调整工程档案档号失败，请重试！");
			    }
			});
	  	}
	});
}
