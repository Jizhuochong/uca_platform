$(document).ready(function() {
	$('#list_data').datagrid({
		title : '声像档案状态更新进度列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/scheduleUpdate/listSound',
		method : 'post',
		idField : 'archivesId',
		pagination : true,// 分页控件
		columns : [ [
 			{field : 'projectName',title:'工程名称'},
		    {field : 'archivesId',title : '工程档案电子编号',hidden:false},
 			{field : 'orderNum',title : '顺序号'},
 			{field : 'checkTime',title : '审核时间'},
 			{field : 'checkStatus',title : '审核状态', formatter: function(value,row,index){
 	           	 var rtn = "";
 	        	 switch(row.checkStatus){
 	        	 	case 3:
 	        	 		rtn = "审核通过";
 	        	 		break;
 	        	 	default:
 	        	 		rtn = "";
 	        	 		break;
 	        	 }
 	        	 return rtn;
 	        }},
 	        {field : 'copyStatus',title : '档案复制进度状态', formatter: function(value,row,index){
 	           	 var rtn = "";
 	        	 switch(row.copyStatus){
 	        	 	case 0:
 	        	 		rtn = "尚未办理完成";
 	        	 		break;
 	        	 	case 1:
 	        	 		rtn = "可来馆取件";
 	        	 		break;
 	        	 	default:
 	        	 		rtn = "";
 	        	 		break;
 	        	 }
 	        	 return rtn;
 	        }},
 	        {field : 'queryStatus',title : '档案预约状态', formatter: function(value,row,index){
 	           	 var rtn = "";
 	        	 switch(row.queryStatus){
 	        	 	case 1:
 	        	 		rtn = "已预约";
 	        	 		break;
 	        	 	case 2:
 	        	 		rtn = "可预约";
 	        	 		break;
 	        	 	default:
 	        	 		rtn = "";
 	        	 		break;
 	        	 }
 	        	 return rtn;
 	        }},
 	        {field : 'instruction',title : '反馈信息'},
 			{field : 'userName',title : '上传人'},
 			{field : 'operation',title : '操作',formatter : function(value, row, index) {
 				var op = "";
 				switch(row.copyStatus){
 				   	case 0:
 				   		op += "<a href='javascript:copyFinish("+ row.archivesId + ");' class='easyui-linkbutton'>档案复制办理完成</a>" +
 				   				"&nbsp;/&nbsp;<a href='javascript:toEditOrderNum("+ row.archivesId+ ",\""+row.orderNum+"\");' class='easyui-linkbutton'>修改调卷单编号</a>";
 	         	 		break;
 	         	 	case 1:
 	         	 		op += "<a href='javascript:toEditOrderNum("+ row.archivesId+ ",\""+row.orderNum+"\");' class='easyui-linkbutton'>修改调卷单编号</a>";
 	         	 		break;
 	         	 	default:
 	         	 		break;
 			   	}
 			   	return op;
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

function copyFinish(archivesId) {
	parent.$.messager.confirm('Confirm','是否确定档案复制办理完成？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/scheduleUpdate/copyFinish',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	archivesId : archivesId
      		    },
      		    success: function(data){
      		    	if(data.success) {
      		    		parent.$.messager.alert('提示', data.msg);
      		    		$('#list_data').datagrid('reload');
      		    	} else {
      		    		parent.$.messager.alert('提示', data.msg);
      		    	}
      		    },
      		    error: function(){
      		    	parent.$.messager.alert('提示', "档案复制办理完成失败，请重试！");
      		    }
      		});
      	}
 	});
}

function toEditOrderNum(archivesId, orderNum){
	$("#archives_id_jsid").val(archivesId);
	$("#order_num_jsid").val(orderNum);
	$('#edit_dialog').dialog('open').dialog('setTitle','修改调卷单编号');
}

function editOrderNum() {
	var pattern = /^\[\d{4}\]\d{4}$/;
	var archives_id_jsid = $("#archives_id_jsid").val(), order_num_jsid = $("#order_num_jsid").val();

	if(archives_id_jsid == null || archives_id_jsid.length == 0 || archives_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择修改调卷单编号的数据记录！");
		return;
	}
	
	if(order_num_jsid == null || order_num_jsid.length == 0 || order_num_jsid == ""){
		parent.$.messager.alert('提示', "请输入调卷单编号！");
		return;
	}
	if(order_num_jsid.length > 13) {
		parent.$.messager.alert('提示', "调卷单编号长度不能多于13个字符！");
		return;
	}
	if(!pattern.test(order_num_jsid)) {
		parent.$.messager.alert('提示', "调卷单编号格式非法，参考：[2015]0001格式！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定修改调卷单编号？',function(r){
	  	if (r){
			$.ajax({
			    url: rfPath + '/scheduleUpdate/editOrderNum',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	archivesId : archives_id_jsid,
			    	orderNum : order_num_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#edit_dialog').dialog('close');
			    	} else {
			    		parent.$.messager.alert('提示', data.msg);
			    	}
			    },
			    error: function(){
			    	parent.$.messager.alert('提示', "修改调卷单编号失败，请重试！");
			    }
			});
	  	}
	});
}
