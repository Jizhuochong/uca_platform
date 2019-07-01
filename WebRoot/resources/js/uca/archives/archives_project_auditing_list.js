$(document).ready(function() {
	$('#list_data').datagrid({
		title : '工程档案待审核列表&nbsp;&nbsp;<button onclick="javascript:refreshCurrentPage();">刷新列表</button>',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/archivesAuditing/listProject',
		method : 'post',
		idField : 'archivesId',
		pagination : true,// 分页控件
//		toolbar:'#select_bar',
		columns : [ [
		    {field : 'projectName',title:'工程名称'},
			{field : 'archivesId',title : '工程档案电子编号',hidden:false},
			{field : 'orderNum',title : '调卷单编号'},
			{field : 'updateTime',title : '送审时间'},
			{field : 'checkStatus',title : '审核状态', formatter: function(value,row,index){
	           	 var rtn = "";
//	           	 alert(row.checkStatus);
	        	 switch(row.checkStatus){
	        	 	case 1:
	        	 		rtn = "待审核";
	        	 		break;
	        	 	case 2:
	        	 		rtn = "审核未通过";
	        	 		break;
	        	 	case 3:
	        	 		rtn = "审核通过";
	        	 		break;
	        	 	case 4:
	        	 		rtn = "重新上传";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
			{field : 'userName',title : '上传人'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
//				return "<a href='javascript:auditingPass("+ row.archivesId + ");' class='easyui-linkbutton'>审核通过</a>" +
//						"&nbsp;/&nbsp;<a href='javascript:toAuditingUnPass("+ row.archivesId+ ");' class='easyui-linkbutton'>审核不通过</a>" +
//						"&nbsp;/&nbsp;<a href='"+rfPath+"/download_file/dbp?p="+decodeURI(row.fileUrl)+"&fn="+decodeURI(row.sourceFileName)+"' class='easyui-linkbutton'>下载档案文件</a>" +
//						"&nbsp;/&nbsp;<a href='"+rfPath+"/download_file/dbp?p="+decodeURI(row.fileUrl)+"&fn="+decodeURI(row.sourceFileName)+"&contentTypeForView=text/xml' class='easyui-linkbutton' target='_blank'>查看档案文件</a>";
//				return "<a href='javascript:auditingArchives("+ row.archivesId + ");' class='easyui-linkbutton'>调用“著录软件”进行审核</a>";
				if(row.checkStatus != 1){
					return "调用“著录软件”进行审核("+row.projectName+")";
				}else{
					return "<a href='GyearAmsCheck:id="+row.archivesId+";pname="+row.projectName+";server="+$("#ServerPort_JS").val()+"' class='easyui-linkbutton'>调用“著录软件”进行审核("+row.projectName+")</a>";
				}
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

function refreshCurrentPage() {
	$('#list_data').datagrid('reload');
}

function auditingArchives(archivesId) {
	parent.$.messager.alert('提示', archivesId);
}

function auditingPass(archivesId) {
	parent.$.messager.confirm('Confirm','是否确定审核通过？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/archivesAuditing/pass',
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
      		    	parent.$.messager.alert('提示', "审核通过失败，请重试！");
      		    }
      		});
      	}
 	});
}

function toAuditingUnPass(archivesId){
	$("#archives_id_jsid").val(archivesId);
	$('#auditing_dialog').dialog('open').dialog('setTitle','审核不通过');
}

function auditingUnPass() {
	var archives_id_jsid = $("#archives_id_jsid").val(), auditing_unpass_txt_jsid = $("#auditing_unpass_txt_jsid").val();

	if(archives_id_jsid == null || archives_id_jsid.length == 0 || archives_id_jsid == ""){
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
			    url: rfPath + '/archivesAuditing/unpass',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	archivesId : archives_id_jsid,
			    	unPassTxt : auditing_unpass_txt_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#auditing_dialog').dialog('close');
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
