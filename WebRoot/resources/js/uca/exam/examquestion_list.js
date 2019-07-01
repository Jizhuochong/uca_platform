$(document).ready(function() {
	$('#list_data').datagrid({
		title : '试题库列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/examquestion/list?epId='+$("#epId_jsid").val(),
		method : 'post',
		idField : 'eqId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'eqNumber',title : '题号'},
			{field : 'name',title : '试题名称'},
			{field : 'classifyName',title : '试题分类'},
			{field : 'type',title : '试题类型', formatter: function(value,row,index){
	           	 var rtn = "";
	        	 switch(row.type){
	        	 	case 1:
	        	 		rtn = "多选题";
	        	 		break;
	        	 	case 2:
	        	 		rtn = "单选题";
	        	 		break;
	        	 	case 3:
	        	 		rtn = "填空题";
	        	 		break;
	        	 	case 4:
	        	 		rtn = "问答题";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
	        {field : 'createTime',title : '创建时间'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.eqId + ");' class='easyui-linkbutton'>修改</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.eqId+ ");' class='easyui-linkbutton'>删除</a>";
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
		name : $("#name_search_jsid").val(),
		classifyId : $("#classify_search_jsid").combobox('getValue'),
		type : $("#type_search_jsid").combobox('getValue'),
		sd : $('#sd').datebox('getValue'),
	 	ed : $('#ed').datebox('getValue')
	});
}

function clearForm() {
	$('#name_search_jsid').val('');
	$("#classify_search_jsid").combobox('clear');
	$("#type_search_jsid").combobox('clear');
//	$("#type_jsid").combobox('loadData', []);
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
	var epId_jsid = $("#epId_jsid").val(), eqNumber_jsid = $('#eqNumber_jsid').numberbox('getValue'), 
		classify_jsid = $("#classify_jsid").combobox('getValue'), 
		type_jsid = $("#type_jsid").combobox('getValue'), questionId_jsid = $("#questionId_jsid").combobox('getValue'), 
		score_jsid = $('#score_jsid').numberbox('getValue');
	
	if(epId_jsid == null || epId_jsid.length == 0 || epId_jsid == ""){
		parent.$.messager.alert('提示', "请选择试卷！");
		return;
	}
	
	if(eqNumber_jsid == null || eqNumber_jsid.length == 0 || eqNumber_jsid == ""){
		parent.$.messager.alert('提示', "请输入试题题号！");
		return;
	}
	
	if(classify_jsid == null || classify_jsid.length == 0 || classify_jsid == "" || classify_jsid == 0){
		parent.$.messager.alert('提示', "请选择试题分类！");
		return;
	}
	
	if(type_jsid == null || type_jsid.length == 0 || type_jsid == "" || type_jsid == 0){
		parent.$.messager.alert('提示', "请选择试题类型！");
		return;
	}
	
	if(questionId_jsid == null || questionId_jsid.length == 0 || questionId_jsid == ""){
		parent.$.messager.alert('提示', "请选择考题！");
		return;
	}
	
	if(score_jsid == null || score_jsid.length == 0 || score_jsid == ""){
		parent.$.messager.alert('提示', "请输入试题分数！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/examquestion/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	epId : epId_jsid,
			    	classifyId : classify_jsid,
			    	eqNumber : eqNumber_jsid,
			    	questionId : questionId_jsid,
			    	score : score_jsid
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

function toEdit(eqId){
	$.ajax({
	    url: rfPath + '/examquestion/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : eqId
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#eq_id_jsid').val(obj.eqId);
	    		$('#eqNumber_jsid').numberbox('setValue', obj.eqNumber);
	    		$('#classify_jsid').combobox('setValue', obj.classifyId);
	    		$('#classify_jsid').combobox('setText', obj.classifyName);
	    		$('#type_jsid').combobox('setValue', obj.type);
	    		$('#questionId_jsid').combobox('setValue', obj.questionId);
	    		$('#questionId_jsid').combobox('setText', obj.name);
	    		$('#score_jsid').numberbox('setValue', obj.score);
	    		
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
	var epId_jsid = $("#epId_jsid").val(), eq_id_jsid = $("#eq_id_jsid").val(), 
		eqNumber_jsid = $('#eqNumber_jsid').numberbox('getValue'), classify_jsid = $("#classify_jsid").combobox('getValue'), 
		type_jsid = $("#type_jsid").combobox('getValue'), questionId_jsid = $("#questionId_jsid").combobox('getValue'), 
		score_jsid = $('#score_jsid').numberbox('getValue');
		
	if(eq_id_jsid == null || eq_id_jsid.length == 0 || eq_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(epId_jsid == null || epId_jsid.length == 0 || epId_jsid == ""){
		parent.$.messager.alert('提示', "请选择试卷！");
		return;
	}
	
	if(eqNumber_jsid == null || eqNumber_jsid.length == 0 || eqNumber_jsid == ""){
		parent.$.messager.alert('提示', "请输入试题题号！");
		return;
	}
	
	if(classify_jsid == null || classify_jsid.length == 0 || classify_jsid == "" || classify_jsid == 0){
		parent.$.messager.alert('提示', "请选择试题分类！");
		return;
	}
	
	if(type_jsid == null || type_jsid.length == 0 || type_jsid == "" || type_jsid == 0){
		parent.$.messager.alert('提示', "请选择试题类型！");
		return;
	}
	
	if(questionId_jsid == null || questionId_jsid.length == 0 || questionId_jsid == ""){
		parent.$.messager.alert('提示', "请选择考题！");
		return;
	}
	
	if(score_jsid == null || score_jsid.length == 0 || score_jsid == ""){
		parent.$.messager.alert('提示', "请输入试题分数！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/examquestion/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	eqId : eq_id_jsid,
			    	epId : epId_jsid,
			    	classifyId : classify_jsid,
			    	eqNumber : eqNumber_jsid,
		    		questionId : questionId_jsid,
		    		score : score_jsid
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

function toRemove(eqId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/examquestion/delete',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	eqId : eqId
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
