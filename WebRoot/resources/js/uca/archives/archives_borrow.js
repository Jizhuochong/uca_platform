$(document).ready(function() {
	$('#list_data').datagrid({
		title : '查询结果',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/archives_borrow/list',
		method : 'post',
		idField : 'id',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
//            {field : 'id',title : '查询借阅单编号ID',hidden:true},
		    {field : 'num',title : '查询借阅单编号'},
		    {field : 'person',title : '查询借阅者姓名'},
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
		num : $("#num_jsid").val(),
		person : $("#person_jsid").val(),
	});
}

function clearForm() {
	$('#num_jsid').val('');
	$('#person_jsid').val('');
}

function toAdd(){
	$('#formName_catalog').form("clear");
	$("#addButton").show();
	$("#editButton").hide();
	$('#catalog_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var num_name_jsid = $("#num_name_jsid").val(), project_preson_jsid = $("#project_preson_jsid").val();
	
	if(num_name_jsid == null || num_name_jsid.length == 0 || num_name_jsid == ""){
		parent.$.messager.alert('提示', "请输入借阅单编号！");
		return;
	}
	
	if(project_preson_jsid == null || project_preson_jsid.length == 0 || project_preson_jsid == ""){
		parent.$.messager.alert('提示', "请输入借阅者姓名！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/archives_borrow/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	num : num_name_jsid,
			    	person : project_preson_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#catalog_dialog').dialog('close');
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

function toEdit(catalogId){
	$.ajax({
	    url: rfPath + '/archives_borrow/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : catalogId
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#id_jsid').val(obj.id);
	    		$('#num_name_jsid').val(obj.num);
	    		$('#project_preson_jsid').val(obj.person);
	    		
	    		$("#addButton").hide();
	    		$("#editButton").show();
	    		$('#catalog_dialog').dialog('open').dialog('setTitle','编辑');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function edit(){
	var id_jsid = $("#id_jsid").val(), 
		num_name_jsid = $("#num_name_jsid").val(), 
		project_preson_jsid = $("#project_preson_jsid").val();
	
	if(id_jsid == null || id_jsid.length == 0 || id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(num_name_jsid == null || num_name_jsid.length == 0 || num_name_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程名称！");
		return;
	}
	
	if(project_preson_jsid == null || project_preson_jsid.length == 0 || project_preson_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程地点！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/archives_borrow/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	id : id_jsid,
			    	num : num_name_jsid,
			    	person : project_preson_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#catalog_dialog').dialog('close');
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

function toRemove(catalogId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/archives_borrow/delete',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	catalogId : catalogId
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

function toImport(){
	$('#materialImport').form("clear");
	$('#import_dialog').dialog('open').dialog('setTitle','批量导入');
}

function importExcel() {
	var fileItemV = $("#fileItem").val();
	if (fileItemV == "") {
		parent.$.messager.alert('提示', "选择批量导入数据文件！");
		return false;
	}
	var i = fileItemV.indexOf(".xlsx");
    if (i == -1) {
        i = fileItemV.indexOf(".xls");
    }
	if (i == -1) {
		parent.$.messager.alert('提示', "批量导入数据文件只能是xls或xlsx格式！");
		return false;
	}
	var options = {
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success){
				parent.$.messager.alert('提示', data.msg);
	    		$('#list_data').datagrid('reload');
	    		$('#import_dialog').dialog('close');
			} else {
				parent.$.messager.alert('提示', "批量导入失败："+data.msg+"！");
			}
		}
	};
	$("#materialImport").ajaxSubmit(options);
}
