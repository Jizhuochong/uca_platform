$(document).ready(function() {
	$('#list_data').datagrid({
		title : '试卷库列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/exampaper/list',
		method : 'post',
		idField : 'epId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'epId',title : '试卷编号'},
			{field : 'epName',title : '试卷名称'},
			{field : 'examTime',title : '考试时限(单位：分钟)'},
			{field : 'status',title : '试卷状态', formatter: function(value,row,index){
	           	 var rtn = "";
	        	 switch(row.status){
	        	 	case 1:
	        	 		rtn = "未发布";
	        	 		break;
	        	 	case 2:
	        	 		rtn = "发布";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
	        {field : 'passScore',title : '规定分数'},
	        {field : 'period',title : '学时'},
	        {field : 'createTime',title : '创建时间'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				var op = "";
				switch(row.status){
					case 1:
				   		op += "<a href='javascript:toEdit("+ row.epId + ");' class='easyui-linkbutton'>修改</a>" +
				   			"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.epId+ ");' class='easyui-linkbutton'>删除</a>" +
				   			"&nbsp;/&nbsp;<a href='javascript:toPublishExamPaper("+ row.epId+ ");' class='easyui-linkbutton'>发布试卷</a>" +
				   			"&nbsp;/&nbsp;<a href='javascript:toExamQuestionMng("+ row.epId + ", \"" + row.epName + "\");' class='easyui-linkbutton'>试题库管理</a>";
					break;
					case 2:
						op += "<a href='javascript:toEdit("+ row.epId + ");' class='easyui-linkbutton'>修改</a>" +
							"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.epId+ ");' class='easyui-linkbutton'>删除</a>" +
							"&nbsp;/&nbsp;<a href='javascript:toExamQuestionMng("+ row.epId + ", \"" + row.epName + "\");' class='easyui-linkbutton'>试题库管理</a>";
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
	
	$("#sd").datebox({
		formatter : formatDate
	});

	$("#ed").datebox({
		formatter : formatDate
	});

	function formatDate(date) {
		return date.getFullYear() + "-" + (date.getMonth() + 1)
				+ "-" + date.getDate();
	}
});

function toSearch() {
	$('#list_data').datagrid('load', {
		epName : $("#epName_search_jsid").val(),
		sd : $('#sd').datebox('getValue'),
	 	ed : $('#ed').datebox('getValue')
	});
}

function clearForm() {
	$('#epName_search_jsid').val('');
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}

function toAdd(){
	$('#formName_obj').form("clear");
	$("#addButton").show();
	$("#editButton").hide();
	$('#obj_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var epName_jsid = $("#epName_jsid").val(), examTime_jsid = $('#examTime_jsid').numberbox('getValue'),
		passScore_jsid = $('#passScore_jsid').numberbox('getValue'), period_jsid = $('#period_jsid').numberbox('getValue');
	
	if(epName_jsid == null || epName_jsid.length == 0 || epName_jsid == ""){
		parent.$.messager.alert('提示', "请输入试卷名称！");
		return;
	}
	
	if(examTime_jsid == null || examTime_jsid.length == 0 || examTime_jsid == ""){
		parent.$.messager.alert('提示', "请输入考试时限！");
		return;
	}
	
	if(passScore_jsid == null || passScore_jsid.length == 0 || passScore_jsid == ""){
		parent.$.messager.alert('提示', "请输入规定分数！");
		return;
	}
	
	if(period_jsid == null || period_jsid.length == 0 || period_jsid == ""){
		parent.$.messager.alert('提示', "请输入学时！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/exampaper/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	epName : epName_jsid,
			    	examTime : examTime_jsid,
			    	passScore : passScore_jsid,
			    	period : period_jsid
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
			    	parent.$.messager.alert('提示', "添加失败，请重试！");
			    }
			});
      	}
 	});
};

function toEdit(epId){
	$.ajax({
	    url: rfPath + '/exampaper/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : epId
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#ep_id_jsid').val(obj.epId);
	    		$('#epName_jsid').val(obj.epName);
	    		$('#examTime_jsid').numberbox('setValue', obj.examTime);
	    		$('#passScore_jsid').numberbox('setValue', obj.passScore);
	    		$('#period_jsid').numberbox('setValue', obj.period);
	    		
	    		$("#addButton").hide();
	    		$("#editButton").show();
	    		$('#obj_dialog').dialog('open').dialog('setTitle','编辑');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function edit(){
	var ep_id_jsid = $("#ep_id_jsid").val(), epName_jsid = $("#epName_jsid").val(), 
		examTime_jsid = $('#examTime_jsid').numberbox('getValue'), passScore_jsid = $('#passScore_jsid').numberbox('getValue'), 
		period_jsid = $('#period_jsid').numberbox('getValue');
	
	if(ep_id_jsid == null || ep_id_jsid.length == 0 || ep_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(epName_jsid == null || epName_jsid.length == 0 || epName_jsid == ""){
		parent.$.messager.alert('提示', "请输入试卷名称！");
		return;
	}
	
	if(examTime_jsid == null || examTime_jsid.length == 0 || examTime_jsid == ""){
		parent.$.messager.alert('提示', "请输入考试时限！");
		return;
	}
	
	if(passScore_jsid == null || passScore_jsid.length == 0 || passScore_jsid == ""){
		parent.$.messager.alert('提示', "请输入规定分数！");
		return;
	}
	
	if(period_jsid == null || period_jsid.length == 0 || period_jsid == ""){
		parent.$.messager.alert('提示', "请输入学时！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/exampaper/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	epId : ep_id_jsid,
			    	epName : epName_jsid,
			    	examTime : examTime_jsid,
			    	passScore : passScore_jsid,
			    	period : period_jsid
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
			    	parent.$.messager.alert('提示', "修改失败，请重试！");
			    }
			});
      	}
 	});
};

function toRemove(epId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/exampaper/delete',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	epId : epId
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
      		    	parent.$.messager.alert('提示', "删除失败，请重试！");
      		    }
      		});
      	}
 	});
};

function toPublishExamPaper(epId){
	parent.$.messager.confirm('Confirm','是否确定发布试卷？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/exampaper/publish',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	epId : epId
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
      		    	parent.$.messager.alert('提示', "发布失败，请重试！");
      		    }
      		});
      	}
 	});
};

function toExamQuestionMng(epId, epName){
	parent.addTab("","试卷["+epName+"]-试题库管理","/examquestion/toList?epId="+epId,null);
}
