$(document).ready(function() {
	$('#list_data').datagrid({
		title : '试题分类列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/examquestionclassify/list',
		method : 'post',
		idField : 'id',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'id',title : '分类编号'},
			{field : 'name',title : '分类名称'},
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
});

function toSearch() {
	$('#list_data').datagrid('load', {
		name : $("#name_search_jsid").val()
	});
}

function clearForm() {
	$('#name_search_jsid').val('');
}

function toAdd(){
	$('#formName_obj').form("clear");
	$("#addButton").show();
	$("#editButton").hide();
	$('#obj_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var name_jsid = $("#name_jsid").val();
	
	if(name_jsid == null || name_jsid.length == 0 || name_jsid == ""){
		parent.$.messager.alert('提示', "请输入分类名称！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/examquestionclassify/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	name : name_jsid
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
	    url: rfPath + '/examquestionclassify/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : id
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#id_jsid').val(obj.id);
	    		$('#name_jsid').val(obj.name);
	    		
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
	var id_jsid = $("#id_jsid").val(), name_jsid = $("#name_jsid").val();
		
	if(id_jsid == null || id_jsid.length == 0 || id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(name_jsid == null || name_jsid.length == 0 || name_jsid == ""){
		parent.$.messager.alert('提示', "请输入分类名称！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/examquestionclassify/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	id : id_jsid,
		    		name : name_jsid
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
      		    url: rfPath + '/examquestionclassify/delete',
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
