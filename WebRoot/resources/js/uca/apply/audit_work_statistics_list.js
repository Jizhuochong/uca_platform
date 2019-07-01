$(document).ready(function() {
	$('#list_data').datagrid({
		title : '审核工作量办理列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/auditWorkStatistics/list',
		method : 'post',
		idField : 'workId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'projectName',title : '工程名称'},
			{field : 'userName',title : '办理人员姓名'},
			{field : 'picNum',title : '相片数量（张）'},
			{field : 'picSize',title : '相片容量（GB）'},
			{field : 'movMinute',title : '视频时间（分钟）'},
			{field : 'movSize',title : '视频容量（GB）'},
			{field : 'createTime',title : '创建日期'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.workId + ");' class='easyui-linkbutton'>修改</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.workId+ ");' class='easyui-linkbutton'>删除</a>";
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
		projectName : $("#projectName_jsid").val(),
		sd : $('#sd').datebox('getValue'),
	 	ed : $('#ed').datebox('getValue')
	});
}

function clearForm() {
	$('#projectName_jsid').val('');
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}

function toAdd(){
	$('#formName_A').form("clear");
	$("#addButton").show();
	$("#editButton").hide();
	$('#A_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var project_name_jsid = $("#project_name_jsid").val(), 
		pic_num_jsid = $("#pic_num_jsid").val(), pic_size_jsid = $("#pic_size_jsid").val(), 
		mov_minute_jsid = $("#mov_minute_jsid").val(), mov_size_jsid = $("#mov_size_jsid").val();
	if(project_name_jsid == null || project_name_jsid.length == 0 || project_name_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程名称！");
		return;
	}
	if(pic_num_jsid == null || pic_num_jsid.length == 0 || pic_num_jsid == ""){
		parent.$.messager.alert('提示', "请输入相片数量（张）！");
		return;
	}
	if(pic_size_jsid == null || pic_size_jsid.length == 0 || pic_size_jsid == ""){
		parent.$.messager.alert('提示', "请输入相片容量（GB）！");
		return;
	}
	if(mov_minute_jsid == null || mov_minute_jsid.length == 0 || mov_minute_jsid == ""){
		parent.$.messager.alert('提示', "请输入视频时间（分钟）！");
		return;
	}
	if(mov_size_jsid == null || mov_size_jsid.length == 0 || mov_size_jsid == ""){
		parent.$.messager.alert('提示', "请输入视频容量（GB）！");
		return;
	}
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/auditWorkStatistics/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	projectName : project_name_jsid,
			    	picNum : pic_num_jsid,
			    	picSize : pic_size_jsid,
			    	movMinute : mov_minute_jsid,
			    	movSize : mov_size_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#A_dialog').dialog('close');
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

function toEdit(workId){
	$.ajax({
	    url: rfPath + '/auditWorkStatistics/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : workId
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#work_id_jsid').val(obj.workId);
	    		$('#project_name_jsid').val(obj.projectName);
	    		$('#pic_num_jsid').val(obj.picNum);
	    		$('#pic_size_jsid').val(obj.picSize);
	    		$('#mov_minute_jsid').val(obj.movMinute);
	    		$('#mov_size_jsid').val(obj.movSize);
	    		
	    		$("#addButton").hide();
	    		$("#editButton").show();
	    		$('#A_dialog').dialog('open').dialog('setTitle','编辑');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function edit(){
	var work_id_jsid = $("#work_id_jsid").val(), project_name_jsid = $("#project_name_jsid").val(), 
		pic_num_jsid = $("#pic_num_jsid").val(), pic_size_jsid = $("#pic_size_jsid").val(), 
		mov_minute_jsid = $("#mov_minute_jsid").val(), mov_size_jsid = $("#mov_size_jsid").val();
	if(work_id_jsid == null || work_id_jsid.length == 0 || work_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	if(project_name_jsid == null || project_name_jsid.length == 0 || project_name_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程名称！");
		return;
	}
	if(pic_num_jsid == null || pic_num_jsid.length == 0 || pic_num_jsid == ""){
		parent.$.messager.alert('提示', "请输入相片数量（张）！");
		return;
	}
	if(pic_size_jsid == null || pic_size_jsid.length == 0 || pic_size_jsid == ""){
		parent.$.messager.alert('提示', "请输入相片容量（GB）！");
		return;
	}
	if(mov_minute_jsid == null || mov_minute_jsid.length == 0 || mov_minute_jsid == ""){
		parent.$.messager.alert('提示', "请输入视频时间（分钟）！");
		return;
	}
	if(mov_size_jsid == null || mov_size_jsid.length == 0 || mov_size_jsid == ""){
		parent.$.messager.alert('提示', "请输入视频容量（GB）！");
		return;
	}
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/auditWorkStatistics/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	workId : work_id_jsid,
			    	projectName : project_name_jsid,
			    	picNum : pic_num_jsid,
			    	picSize : pic_size_jsid,
			    	movMinute : mov_minute_jsid,
			    	movSize : mov_size_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#A_dialog').dialog('close');
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

function toRemove(workId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/auditWorkStatistics/delete',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	workId : workId
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
