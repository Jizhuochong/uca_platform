$(document).ready(function() {
	$('#list_data').datagrid({
		title : '声像档案预约审核列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/applyAuditing/list',
		method : 'post',
		idField : 'aqId',
		pagination : true,// 分页控件
		columns : [ [
		    {field : 'projectName',title:'工程名称'},
			{field : 'orderNum',title : '顺序号'},
			{field : 'applyTime',title : '预约时间'},
			{field : 'checkStatus',title : '审核状态', formatter: function(value,row,index){
	           	 var rtn = "";
	        	 switch(row.checkStatus){
	        	 	case 1:
	        	 		rtn = "待审核";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toAuditingPass("+ row.aqId + ",\""+row.applyTime+"\");' class='easyui-linkbutton'>审核通过</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toAuditingUnPass("+ row.aqId+ ");' class='easyui-linkbutton'>审核不通过</a>";
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

function toAuditingPass(aqId, applyTime){
	$("#pass_aq_id_jsid").val(aqId);
	$("#auditing_pass_txt_jsid").val(applyTime);
	$('#pass_dialog').dialog('open').dialog('setTitle','审核通过');
}

function auditingPass() {
	var pass_aq_id_jsid = $("#pass_aq_id_jsid").val(), auditing_pass_txt_jsid = $("#auditing_pass_txt_jsid").val();

	if(pass_aq_id_jsid == null || pass_aq_id_jsid.length == 0 || pass_aq_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择审核通过的数据记录！");
		return;
	}
	
	if(auditing_pass_txt_jsid == null || auditing_pass_txt_jsid.length == 0 || auditing_pass_txt_jsid == ""){
		parent.$.messager.alert('提示', "请输入来馆查询时间！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定审核通过？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/applyAuditing/pass',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	aqId : pass_aq_id_jsid,
      		    	passTxt : auditing_pass_txt_jsid
      		    },
      		    success: function(data){
      		    	if(data.success) {
      		    		parent.$.messager.alert('提示', data.msg);
      		    		$('#list_data').datagrid('reload');
      		    		$('#pass_dialog').dialog('close');
      		    	} else {
      		    		parent.$.messager.alert('提示', data.msg);
      		    	}
      		    },
      		    error: function(){
      		    	parent.$.messager.alert('提示', "审核通过失败，请重试！");
      		    }
      		});
      	}
 	});
}

function toAuditingUnPass(aqId){
	$("#unpass_aq_id_jsid").val(aqId);
	$('#unpass_dialog').dialog('open').dialog('setTitle','审核不通过');
}

function auditingUnPass() {
	var unpass_aq_id_jsid = $("#unpass_aq_id_jsid").val(), auditing_unpass_txt_jsid = $("#auditing_unpass_txt_jsid").val();

	if(unpass_aq_id_jsid == null || unpass_aq_id_jsid.length == 0 || unpass_aq_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择审核不通过的数据记录！");
		return;
	}
	
	if(auditing_unpass_txt_jsid == null || auditing_unpass_txt_jsid.length == 0 || auditing_unpass_txt_jsid == ""){
		parent.$.messager.alert('提示', "请输入审核不通过原因！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定审核不通过？',function(r){
	  	if (r){
			$.ajax({
			    url: rfPath + '/applyAuditing/unpass',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	aqId : unpass_aq_id_jsid,
			    	unPassTxt : auditing_unpass_txt_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#unpass_dialog').dialog('close');
			    	} else {
			    		parent.$.messager.alert('提示', data.msg);
			    	}
			    },
			    error: function(){
			    	parent.$.messager.alert('提示', "审核不通过失败，请重试！");
			    }
			});
	  	}
	});
}
