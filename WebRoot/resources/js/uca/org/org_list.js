$(document).ready(function() {
	$('#list_data').datagrid({
		title : '机构列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/org/list',
		method : 'post',
		idField : 'orgId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'orgName',title : '机构名称'},
			{field : 'orgCode',title : '机构编码'},
			{field : 'description',title : '机构描述'},
			{field : 'createTime',title : '创建日期'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.orgId + ");' class='easyui-linkbutton'>修改</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.orgId+ ");' class='easyui-linkbutton'>删除</a>";
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
		orgCode : $("#org_code_jsid").val(),
		sd : $('#sd').datebox('getValue'),
	 	ed : $('#ed').datebox('getValue')
	});
}

function clearForm() {
	$('#org_code_jsid').val('');
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}

function toAdd(){
	$('#formName_org').form("clear");
	$("#addButton").show();
	$("#editButton").hide();
	$('#org_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var orgName_jsid = $("#orgName_jsid").val(), orgCode_jsid = $("#orgCode_jsid").val(), 
			sort_jsid = $('#sort_jsid').numberbox('getValue'),description_jsid = $("#description_jsid").val(),type_jsid = $('#type_jsid').val();
	
	if(orgName_jsid == null || orgName_jsid.length == 0 || orgName_jsid == ""){
		parent.$.messager.alert('提示', "请输入机构名称！");
		return;
	}
	
	if(orgCode_jsid == null || orgCode_jsid.length == 0 || orgCode_jsid == ""){
		parent.$.messager.alert('提示', "请输入机构编码！");
		return;
	}
	
	if(sort_jsid == null || sort_jsid.length == 0 || sort_jsid == ""){
		parent.$.messager.alert('提示', "请输入排序！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/org/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	orgName : orgName_jsid,
				    orgCode : orgCode_jsid,
				    sort : sort_jsid,
				    description : description_jsid,
				    type : type_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#org_dialog').dialog('close');
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

function toEdit(orgId){
	$.ajax({
	    url: rfPath + '/org/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : orgId
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#orgId_jsid').val(obj.orgId);
	    		$('#orgName_jsid').val(obj.orgName);
	    		$('#orgCode_jsid').val(obj.orgCode);
	    		$('#sort_jsid').numberbox('setValue', obj.sort);
	    		$('#description_jsid').val(obj.description);
	    		$('#type_jsid').val(obj.type);
	    		$("#addButton").hide();
	    		$("#editButton").show();
	    		$('#org_dialog').dialog('open').dialog('setTitle','编辑');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function edit(){
	var orgId_jsid = $("#orgId_jsid").val(), orgName_jsid = $("#orgName_jsid").val(), 
			orgCode_jsid = $("#orgCode_jsid").val(), sort_jsid = $('#sort_jsid').numberbox('getValue'),
			description_jsid = $("#description_jsid").val(),type_jsid = $('#type_jsid').val();
	
	if(orgId_jsid == null || orgId_jsid.length == 0 || orgId_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(orgName_jsid == null || orgName_jsid.length == 0 || orgName_jsid == ""){
		parent.$.messager.alert('提示', "请输入机构名称！");
		return;
	}
	
	if(orgCode_jsid == null || orgCode_jsid.length == 0 || orgCode_jsid == ""){
		parent.$.messager.alert('提示', "请输入机构编码！");
		return;
	}
	
	if(sort_jsid == null || sort_jsid.length == 0 || sort_jsid == ""){
		parent.$.messager.alert('提示', "请输入排序！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/org/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	orgId : orgId_jsid,
				    orgName : orgName_jsid,
				    orgCode : orgCode_jsid,
				    sort : sort_jsid,
				    description : description_jsid,
				    type: type_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#org_dialog').dialog('close');
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

function toRemove(orgId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/org/delete',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	id : orgId
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
