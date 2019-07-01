$(document).ready(function() {
	$('#list_data').datagrid({
		title : '住宅类项目级档案目录列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/catalog/list',
		method : 'post',
		idField : 'catalogId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'archivesNum',title : '规划许可证号'},
		    {field : 'projectName',title : '工程名称'},
			{field : 'devOrg',title : '建设工程地址'},
			{field : 'projectAddress',title : '建设单位'},
			{field : 'planPerNum',title : '设计单位'},
			{field : 'constructionUnit',title : '施工单位'},
			{field : 'createTime',title : '创建日期'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.catalogId + ");' class='easyui-linkbutton'>修改</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.catalogId+ ");' class='easyui-linkbutton'>删除</a>";
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
		archivesNum : $("#archivesNum_jsid").val(),
		projectName : $("#projectName_jsid").val(),
		devOrg : $("#devOrg_jsid").val(),
		projectAddress : $("#projectAddress_jsid").val(),
		planPerNum : $("#planPerNum_jsid").val(),
		sd : $('#sd').datebox('getValue'),
	 	ed : $('#ed').datebox('getValue')
	});
}

function clearForm() {
	$('#archivesNum_jsid').val('');
	$('#projectName_jsid').val('');
	$('#devOrg_jsid').val('');
	$('#projectAddress_jsid').val('');
	$('#planPerNum_jsid').val('');
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}

function toAdd(){
	$('#formName_catalog').form("clear");
	$("#addButton").show();
	$("#editButton").hide();
	$('#catalog_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var archives_num_jsid = $("#archives_num_jsid").val(), project_name_jsid = $("#project_name_jsid").val(), 
		dev_org_jsid = $("#dev_org_jsid").val(), project_address_jsid = $("#project_address_jsid").val(), 
		plan_per_num_jsid = $("#plan_per_num_jsid").val(), design_unit_jsid = $("#design_unit_jsid").val()
		construction_unit_jsid = $("#construction_unit_jsid").val();
	
	if(archives_num_jsid == null || archives_num_jsid.length == 0 || archives_num_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程档案电子编号！");
		return;
	}
	
	if(project_name_jsid == null || project_name_jsid.length == 0 || project_name_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程名称！");
		return;
	}
	
	if(dev_org_jsid == null || dev_org_jsid.length == 0 || dev_org_jsid == ""){
		parent.$.messager.alert('提示', "请输入建设单位！");
		return;
	}
	
	if(project_address_jsid == null || project_address_jsid.length == 0 || project_address_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程地点！");
		return;
	}
	
	if(plan_per_num_jsid == null || plan_per_num_jsid.length == 0 || plan_per_num_jsid == ""){
		parent.$.messager.alert('提示', "请输入规划许可证号！");
		return;
	}
	if(design_unit_jsid == null || design_unit_jsid.length == 0 || design_unit_jsid == ""){
		parent.$.messager.alert('提示', "请输入设计单位！");
		return;
	}
	if(construction_unit_jsid == null || construction_unit_jsid.length == 0 || construction_unit_jsid == ""){
		parent.$.messager.alert('提示', "请输入施工单位！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/catalog/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	archivesNum : archives_num_jsid,
			    	projectName : project_name_jsid,
			    	devOrg : dev_org_jsid,
			    	projectAddress : project_address_jsid,
			    	planPerNum : plan_per_num_jsid,
			    	designUnit : design_unit_jsid,
			    	constructionUnit : construction_unit_jsid
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
	    url: rfPath + '/catalog/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : catalogId
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#catalog_id_jsid').val(obj.catalogId);
	    		$('#archives_num_jsid').val(obj.archivesNum);
	    		$('#project_name_jsid').val(obj.projectName);
	    		$('#dev_org_jsid').val(obj.devOrg);
	    		$('#project_address_jsid').val(obj.projectAddress);
	    		$('#plan_per_num_jsid').val(obj.planPerNum);
	    		$('#design_unit_jsid').val(obj.designUnit);
	    		$('#construction_unit_jsid').val(obj.constructionUnit);
	    		
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
	var catalog_id_jsid = $("#catalog_id_jsid").val(), archives_num_jsid = $("#archives_num_jsid").val(), 
		project_name_jsid = $("#project_name_jsid").val(), dev_org_jsid = $("#dev_org_jsid").val(), 
		project_address_jsid = $("#project_address_jsid").val(), plan_per_num_jsid = $("#plan_per_num_jsid").val(), 
		design_unit_jsid = $("#design_unit_jsid").val(), construction_unit_jsid = $("#construction_unit_jsid").val();
	
	if(catalog_id_jsid == null || catalog_id_jsid.length == 0 || catalog_id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据记录！");
		return;
	}
	
	if(archives_num_jsid == null || archives_num_jsid.length == 0 || archives_num_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程档案电子编号！");
		return;
	}
	
	if(project_name_jsid == null || project_name_jsid.length == 0 || project_name_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程名称！");
		return;
	}
	
	if(dev_org_jsid == null || dev_org_jsid.length == 0 || dev_org_jsid == ""){
		parent.$.messager.alert('提示', "请输入建设单位！");
		return;
	}
	
	if(project_address_jsid == null || project_address_jsid.length == 0 || project_address_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程地点！");
		return;
	}
	
	if(plan_per_num_jsid == null || plan_per_num_jsid.length == 0 || plan_per_num_jsid == ""){
		parent.$.messager.alert('提示', "请输入规划许可证号！");
		return;
	}
	if(design_unit_jsid == null || design_unit_jsid.length == 0 || design_unit_jsid == ""){
		parent.$.messager.alert('提示', "请输入设计单位！");
		return;
	}
	if(construction_unit_jsid == null || construction_unit_jsid.length == 0 || construction_unit_jsid == ""){
		parent.$.messager.alert('提示', "请输入施工单位！");
		return;
	}
	
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/catalog/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	catalogId : catalog_id_jsid,
			    	archivesNum : archives_num_jsid,
			    	projectName : project_name_jsid,
			    	devOrg : dev_org_jsid,
			    	projectAddress : project_address_jsid,
			    	planPerNum : plan_per_num_jsid,
			    	designUnit : design_unit_jsid,
			    	constructionUnit : construction_unit_jsid
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
      		    url: rfPath + '/catalog/delete',
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
