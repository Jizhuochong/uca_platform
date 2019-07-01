$(document).ready(function() {
	$('#list_data').datagrid({
		title : '问卷调查选项列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/surveyoption/list?surveyId='+$("#surveyId_jsid").val(),
		method : 'post',
		idField : 'id',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'id',title : '问卷调查选项编号'},
			{field : 'options',title : '问卷调查选项'},
	        {field : 'createTime',title : '创建时间'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.id + ");' class='easyui-linkbutton'>修改</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.id+ ");' class='easyui-linkbutton'>删除</a>";
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
		sd : $('#sd').datebox('getValue'),
	 	ed : $('#ed').datebox('getValue')
	});
}

function clearForm() {
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
	var surveyId_jsid = $("#surveyId_jsid").val(), options_jsid = $("#options_jsid").val();
	
	if(surveyId_jsid == null || surveyId_jsid.length == 0 || surveyId_jsid == ""){
		parent.$.messager.alert('提示', "请选择问卷调查！");
		return;
	}
	
	if(options_jsid == null || options_jsid.length == 0 || options_jsid == ""){
		parent.$.messager.alert('提示', "请输入问卷调查选项！");
		return;
	}

	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/surveyoption/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	surveyId : surveyId_jsid,
			    	options : options_jsid
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

function toEdit(id){
	$.ajax({
	    url: rfPath + '/surveyoption/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : id
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#id_jsid').val(obj.id);
	    		$('#options_jsid').val(obj.options);
	    		
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
	var surveyId_jsid = $("#surveyId_jsid").val(), id_jsid = $("#id_jsid").val(), options_jsid = $("#options_jsid").val();
		
	if(id_jsid == null || id_jsid.length == 0 || id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(surveyId_jsid == null || surveyId_jsid.length == 0 || surveyId_jsid == ""){
		parent.$.messager.alert('提示', "请选择问卷调查！");
		return;
	}
	
	if(options_jsid == null || options_jsid.length == 0 || options_jsid == ""){
		parent.$.messager.alert('提示', "请输入问卷调查选项！");
		return;
	}

	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/surveyoption/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	id : id_jsid,
			    	surveyId : surveyId_jsid,
		    		options : options_jsid
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

function toRemove(id){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/surveyoption/delete',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	id : id
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
