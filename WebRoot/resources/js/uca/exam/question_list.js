$(document).ready(function() {
	$('#list_data').datagrid({
		title : '考题库列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/question/list',
		method : 'post',
		idField : 'questionId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'questionId',title : '考题编号'},
			{field : 'name',title : '考题名称'},
			{field : 'classifyName',title : '考题分类'},
			{field : 'type',title : '考题类型', formatter: function(value,row,index){
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
//			{field : 'options',title : '考题选项'},
//			{field : 'answer',title : '考题答案'},
	        {field : 'createTime',title : '创建时间'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.questionId + ");' class='easyui-linkbutton'>修改</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.questionId+ ");' class='easyui-linkbutton'>删除</a>";
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
	var classify_jsid = $("#classify_jsid").combobox('getValue'), type_jsid = $("#type_jsid").combobox('getValue'), 
			name_jsid = $("#name_jsid").val(), options_jsid = $("#options_jsid").val(), 
			answer_jsid = $("#answer_jsid").val();
	
	if(classify_jsid == null || classify_jsid.length == 0 || classify_jsid == "" || classify_jsid == 0){
		parent.$.messager.alert('提示', "请选择考题分类！");
		return;
	}
	if(type_jsid == null || type_jsid.length == 0 || type_jsid == "" || type_jsid == 0){
		parent.$.messager.alert('提示', "请选择考题类型！");
		return;
	}
	
	if(name_jsid == null || name_jsid.length == 0 || name_jsid == ""){
		parent.$.messager.alert('提示', "请输入考题名称！");
		return;
	}
	
	if(type_jsid != 4) {
		if(options_jsid == null || options_jsid.length == 0 || options_jsid == ""){
			parent.$.messager.alert('提示', "请输入考题选项！");
			return;
		}
		
		if(answer_jsid == null || answer_jsid.length == 0 || answer_jsid == ""){
			parent.$.messager.alert('提示', "请输入考题答案！");
			return;
		}
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/question/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	classifyId : classify_jsid,
			    	type : type_jsid,
			    	name : name_jsid,
			    	options : options_jsid,
			    	answer : answer_jsid
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

function toEdit(questionId){
	$.ajax({
	    url: rfPath + '/question/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : questionId
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#question_id_jsid').val(obj.questionId);
	    		$('#classify_jsid').combobox('setValue', obj.classifyId);
	    		$('#classify_jsid').combobox('setText', obj.classifyName);
	    		$('#type_jsid').combobox('setValue', obj.type);
	    		$('#name_jsid').val(obj.name);
	    		$('#options_jsid').val(obj.options);
	    		$('#answer_jsid').val(obj.answer);
	    		
	    		if(obj.type == 4) {
	    			$('#opan_tr_jsid').hide();
	    		} else {
	    			$('#opan_tr_jsid').show();
	    		}
	    		
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
	var question_id_jsid = $("#question_id_jsid").val(), classify_jsid = $("#classify_jsid").combobox('getValue'), 
			type_jsid = $("#type_jsid").combobox('getValue'), name_jsid = $("#name_jsid").val(), 
			options_jsid = $("#options_jsid").val(), answer_jsid = $("#answer_jsid").val();
	
	if(question_id_jsid == null || question_id_jsid.length == 0 || question_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(classify_jsid == null || classify_jsid.length == 0 || classify_jsid == "" || classify_jsid == 0){
		parent.$.messager.alert('提示', "请选择考题分类！");
		return;
	}
	
	if(type_jsid == null || type_jsid.length == 0 || type_jsid == "" || type_jsid == 0){
		parent.$.messager.alert('提示', "请选择考题类型！");
		return;
	}
	
	if(name_jsid == null || name_jsid.length == 0 || name_jsid == ""){
		parent.$.messager.alert('提示', "请输入考题名称！");
		return;
	}
	
	if(type_jsid != 4) {
		if(options_jsid == null || options_jsid.length == 0 || options_jsid == ""){
			parent.$.messager.alert('提示', "请输入考题选项！");
			return;
		}
		
		if(answer_jsid == null || answer_jsid.length == 0 || answer_jsid == ""){
			parent.$.messager.alert('提示', "请输入考题答案！");
			return;
		}
	}
	
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/question/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	questionId : question_id_jsid,
			    	classifyId : classify_jsid,
				    type : type_jsid,
			    	name : name_jsid,
			    	options : options_jsid,
			    	answer : answer_jsid
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

function toRemove(questionId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/question/delete',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	questionId : questionId
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
