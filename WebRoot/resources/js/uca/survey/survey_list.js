$(document).ready(function() {
	$('#list_data').datagrid({
		title : '问卷调查列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/survey/list',
		method : 'post',
		idField : 'id',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'id',title : '问卷调查编号'},
			{field : 'title',title : '问卷调查标题'},
			{field : 'type',title : '问卷调查分类', formatter: function(value,row,index){
	           	 var rtn = "";
	        	 switch(row.type){
	        	 	case 1:
	        	 		rtn = "有选择项方式";
	        	 		break;
	        	 	case 2:
	        	 		rtn = "无选择项方式";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
	        {field : 'createTime',title : '创建时间'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				var op = "";
				switch(row.type){
					case 1:
				   		op += "<a href='javascript:toEdit("+ row.id + ");' class='easyui-linkbutton'>修改</a>" +
				   			"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.id+ ");' class='easyui-linkbutton'>删除</a>" +
				   			"&nbsp;/&nbsp;<a href='javascript:toSurveyOptionMng("+ row.id + ", \"" + row.title + "\");' class='easyui-linkbutton'>问卷调查选项管理</a>";
					break;
					case 2:
						op += "<a href='javascript:toEdit("+ row.id + ");' class='easyui-linkbutton'>修改</a>" +
							"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.id+ ");' class='easyui-linkbutton'>删除</a>";
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
		type : $("#type_search_jsid").combobox('getValue'),
		sd : $('#sd').datebox('getValue'),
	 	ed : $('#ed').datebox('getValue')
	});
}

function clearForm() {
	$("#type_search_jsid").combobox('clear');
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}

function toAdd(){
	$('#formName_obj').form("clear");
	$("#type_tr_jsid").show();
	$("#addButton").show();
	$("#editButton").hide();
	$('#obj_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var title_jsid = $("#title_jsid").val(), type_jsid = $("#type_jsid").combobox('getValue');
	
	if(title_jsid == null || title_jsid.length == 0 || title_jsid == ""){
		parent.$.messager.alert('提示', "请输入问卷调查标题！");
		return;
	}
	
	if(type_jsid == null || type_jsid.length == 0 || type_jsid == ""){
		parent.$.messager.alert('提示', "请选择问卷调查分类！");
		return;
	}

	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/survey/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	title : title_jsid,
			    	type : type_jsid
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
	    url: rfPath + '/survey/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : id
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#id_jsid').val(obj.id);
	    		$('#title_jsid').val(obj.title);
	    		$('#type_jsid').combobox('setValue', obj.type);
	    		
	    		$("#type_tr_jsid").hide();
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
	var id_jsid = $("#id_jsid").val(), title_jsid = $("#title_jsid").val();
	
	if(id_jsid == null || id_jsid.length == 0 || id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(title_jsid == null || title_jsid.length == 0 || title_jsid == ""){
		parent.$.messager.alert('提示', "请输入问卷调查标题！");
		return;
	}

	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/survey/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	id : id_jsid,
			    	title : title_jsid
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
      		    url: rfPath + '/survey/delete',
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

function toSurveyOptionMng(id, title){
	parent.addTab("","问卷调查选项管理["+title+"]","/surveyoption/toList?surveyId="+id,null);
}
