$(document).ready(function() {
	$('#list_data').datagrid({
		title : '办理竣工档案登记详细表列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/detailedForm/list?registerId='+$("#registerId_jsid").val(),
		method : 'post',
		idField : 'id',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'projectPurpose',title : '工程项目用途'},
			{field : 'categoryStructure',title : '结构种类'},
			{field : 'floors',title : '层数地上/地下'},
			{field : 'coversArea',title : '占地面积(平方米)'},
			{field : 'constructionArea',title : '建筑面积(平方米)'},
			{field : 'buildings',title : '栋楼'},
			{field : 'totalInvestment',title : '总投资(元)'},
			{field : 'projectContent',title : '工程内容'},
			{field : 'projectQuantity',title : '数量'},
			{field : 'diameterSection',title : '管径(断面)'},
			{field : 'diameterLength',title : '长度(米)'},
			{field : 'createUserName',title : '创建人'},
			{field : 'createTime',title : '创建日期'},
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


function toAdd(){
	$('#formName_A').form("clear");
	$("#addButton").show();
	$("#editButton").hide();
	$('#A_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var registerId_jsid = $("#registerId_jsid").val(), registerType_jsid = $("#registerType_jsid").val(), 
		projectPurpose_jsid = null, categoryStructure_jsid = null, 		floors_jsid = null, coversArea_jsid = null, 
		constructionArea_jsid = null, buildings_jsid = null, projectContent_jsid = null, projectQuantity_jsid = null, 
		totalInvestment_jsid = null, diameterSection_jsid = null, diameterLength_jsid = null;
	if(registerId_jsid == null || registerId_jsid.length == 0 || registerId_jsid == ""){
		parent.$.messager.alert('提示', "请选择竣工档案登记表！");
		return;
	}
	if(registerType_jsid == null || registerType_jsid.length == 0 || registerType_jsid == ""){
		parent.$.messager.alert('提示', "请选择竣工档案登记表！");
		return;
	}
	if(registerType_jsid == 1) {
		projectPurpose_jsid = $("#projectPurpose_jsid").val(), categoryStructure_jsid = $("#categoryStructure_jsid").val(), 
		floors_jsid = $("#floors_jsid").val(), coversArea_jsid = $("#coversArea_jsid").val(), 
		constructionArea_jsid = $('#constructionArea_jsid').numberbox('getValue'), buildings_jsid = $("#buildings_jsid").val(),
		totalInvestment_jsid = $('#totalInvestment_jsid').numberbox('getValue');
		if(projectPurpose_jsid == null || projectPurpose_jsid.length == 0 || projectPurpose_jsid == ""){
			parent.$.messager.alert('提示', "请输入工程项目用途！");
			return;
		}
		if(categoryStructure_jsid == null || categoryStructure_jsid.length == 0 || categoryStructure_jsid == ""){
			parent.$.messager.alert('提示', "请输入结构种类！");
			return;
		}
		if(floors_jsid == null || floors_jsid.length == 0 || floors_jsid == ""){
			parent.$.messager.alert('提示', "请输入层数地上/地下！");
			return;
		}
		if(coversArea_jsid == null || coversArea_jsid.length == 0 || coversArea_jsid == ""){
			//parent.$.messager.alert('提示', "请输入占地面积（平方米）！");
			coversArea_jsid = 0.00;
			//return;
		}
		if(constructionArea_jsid == null || constructionArea_jsid.length == 0 || constructionArea_jsid == ""){
			parent.$.messager.alert('提示', "请输入建筑面积（平方米）！");
			return;
		}
		if(buildings_jsid == null || buildings_jsid.length == 0 || buildings_jsid == ""){
			parent.$.messager.alert('提示', "请输入栋楼！");
			return;
		}
		if(totalInvestment_jsid == null || totalInvestment_jsid.length == 0 || totalInvestment_jsid == ""){
			parent.$.messager.alert('提示', "请输入总投资(元)！");
			return;
		}
	}
	if(registerType_jsid == 2) {
		projectContent_jsid = $("#projectContent_jsid").val(), projectQuantity_jsid = $('#projectQuantity_jsid').numberbox('getValue'), 
		totalInvestment_jsid = $('#totalInvestment_jsid').numberbox('getValue');
		if(projectContent_jsid == null || projectContent_jsid.length == 0 || projectContent_jsid == ""){
			parent.$.messager.alert('提示', "请输入工程内容！");
			return;
		}
		if(projectQuantity_jsid == null || projectQuantity_jsid.length == 0 || projectQuantity_jsid == ""){
			parent.$.messager.alert('提示', "请输入数量！");
			return;
		}
		if(totalInvestment_jsid == null || totalInvestment_jsid.length == 0 || totalInvestment_jsid == ""){
			parent.$.messager.alert('提示', "请输入总投资(元)！");
			return;
		}
	}
	if(registerType_jsid == 3) {
		diameterSection_jsid = $("#diameterSection_jsid").val(), 
		diameterLength_jsid = $('#diameterLength_jsid').numberbox('getValue');
		if(diameterSection_jsid == null || diameterSection_jsid.length == 0 || diameterSection_jsid == ""){
			parent.$.messager.alert('提示', "请输入管径（断面）！");
			return;
		}
		if(diameterLength_jsid == null || diameterLength_jsid.length == 0 || diameterLength_jsid == ""){
			parent.$.messager.alert('提示', "请输入长度（米）！");
			return;
		}
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/detailedForm/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	registerId : registerId_jsid,
			    	registerType : registerType_jsid,
			    	projectPurpose : projectPurpose_jsid,
			    	categoryStructure : categoryStructure_jsid,
			    	floors : floors_jsid,
			    	coversArea : coversArea_jsid,
			    	constructionArea : constructionArea_jsid,
			    	buildings : buildings_jsid,
			    	projectContent : projectContent_jsid,
			    	projectQuantity : projectQuantity_jsid,
			    	totalInvestment : totalInvestment_jsid,
			    	diameterSection : diameterSection_jsid,
			    	diameterLength : diameterLength_jsid
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

function toEdit(id){
	$.ajax({
	    url: rfPath + '/detailedForm/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : id
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#id_jsid').val(obj.id);
	    		if(obj.registerType == 1) {
	    			$('#projectPurpose_jsid').val(obj.projectPurpose);
	    			$('#categoryStructure_jsid').val(obj.categoryStructure);
		    		$('#floors_jsid').val(obj.floors);
		    		$('#coversArea_jsid').val(obj.coversArea);
		    		$('#constructionArea_jsid').numberbox('setValue', obj.constructionArea);
		    		$('#buildings_jsid').val(obj.buildings);
		    		$('#totalInvestment_jsid').numberbox('setValue', obj.totalInvestment);
	    		}
	    		if(obj.registerType == 2) {
	    			$('#projectContent_jsid').val(obj.projectContent);
	    			$('#projectQuantity_jsid').numberbox('setValue', obj.projectQuantity);
	    			$('#totalInvestment_jsid').numberbox('setValue', obj.totalInvestment);
	    		}
	    		if(obj.registerType == 3) {
	    			$('#diameterSection_jsid').val(obj.diameterSection);
	    			$('#diameterLength_jsid').numberbox('setValue', obj.diameterLength);
	    		}
	    		
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
	var id_jsid = $("#id_jsid").val(), registerType_jsid = $("#registerType_jsid").val(), 
		projectPurpose_jsid = null, categoryStructure_jsid = null, 		floors_jsid = null, coversArea_jsid = null, 
		constructionArea_jsid = null, buildings_jsid = null, projectContent_jsid = null, projectQuantity_jsid = null, 
		totalInvestment_jsid = null, diameterSection_jsid = null, diameterLength_jsid = null;
	if(id_jsid == null || id_jsid.length == 0 || id_jsid == ""){
		parent.$.messager.alert('提示', "请选择竣工档案登记表！");
		return;
	}
	if(registerType_jsid == null || registerType_jsid.length == 0 || registerType_jsid == ""){
		parent.$.messager.alert('提示', "请选择竣工档案登记表！");
		return;
	}
	if(registerType_jsid == 1) {
		projectPurpose_jsid = $("#projectPurpose_jsid").val(), categoryStructure_jsid = $("#categoryStructure_jsid").val(), 
		floors_jsid = $("#floors_jsid").val(), coversArea_jsid = $("#coversArea_jsid").val(), 
		constructionArea_jsid = $('#constructionArea_jsid').numberbox('getValue'), buildings_jsid = $("#buildings_jsid").val(),
		totalInvestment_jsid = $('#totalInvestment_jsid').numberbox('getValue');
		if(projectPurpose_jsid == null || projectPurpose_jsid.length == 0 || projectPurpose_jsid == ""){
			parent.$.messager.alert('提示', "请输入工程项目用途！");
			return;
		}
		if(categoryStructure_jsid == null || categoryStructure_jsid.length == 0 || categoryStructure_jsid == ""){
			parent.$.messager.alert('提示', "请输入结构种类！");
			return;
		}
		if(floors_jsid == null || floors_jsid.length == 0 || floors_jsid == ""){
			parent.$.messager.alert('提示', "请输入层数地上/地下！");
			return;
		}
		if(coversArea_jsid == null || coversArea_jsid.length == 0 || coversArea_jsid == ""){
			//parent.$.messager.alert('提示', "请输入占地面积（平方米）！");
			coversArea_jsid = 0.00;
			//return;
		}
		if(constructionArea_jsid == null || constructionArea_jsid.length == 0 || constructionArea_jsid == ""){
			parent.$.messager.alert('提示', "请输入建筑面积（平方米）！");
			return;
		}
		if(buildings_jsid == null || buildings_jsid.length == 0 || buildings_jsid == ""){
			parent.$.messager.alert('提示', "请输入栋楼！");
			return;
		}
		if(totalInvestment_jsid == null || totalInvestment_jsid.length == 0 || totalInvestment_jsid == ""){
			parent.$.messager.alert('提示', "请输入总投资(元)！");
			return;
		}
	}
	if(registerType_jsid == 2) {
		projectContent_jsid = $("#projectContent_jsid").val(), projectQuantity_jsid = $('#projectQuantity_jsid').numberbox('getValue'), 
		totalInvestment_jsid = $('#totalInvestment_jsid').numberbox('getValue');
		if(projectContent_jsid == null || projectContent_jsid.length == 0 || projectContent_jsid == ""){
			parent.$.messager.alert('提示', "请输入工程内容！");
			return;
		}
		if(projectQuantity_jsid == null || projectQuantity_jsid.length == 0 || projectQuantity_jsid == ""){
			parent.$.messager.alert('提示', "请输入数量！");
			return;
		}
		if(totalInvestment_jsid == null || totalInvestment_jsid.length == 0 || totalInvestment_jsid == ""){
			parent.$.messager.alert('提示', "请输入总投资(元)！");
			return;
		}
	}
	if(registerType_jsid == 3) {
		diameterSection_jsid = $("#diameterSection_jsid").val(), 
		diameterLength_jsid = $('#diameterLength_jsid').numberbox('getValue');
		if(diameterSection_jsid == null || diameterSection_jsid.length == 0 || diameterSection_jsid == ""){
			parent.$.messager.alert('提示', "请输入管径（断面）！");
			return;
		}
		if(diameterLength_jsid == null || diameterLength_jsid.length == 0 || diameterLength_jsid == ""){
			parent.$.messager.alert('提示', "请输入长度（米）！");
			return;
		}
	}
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/detailedForm/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	id : id_jsid,
			    	projectPurpose : projectPurpose_jsid,
			    	categoryStructure : categoryStructure_jsid,
			    	floors : floors_jsid,
			    	coversArea : coversArea_jsid,
			    	constructionArea : constructionArea_jsid,
			    	buildings : buildings_jsid,
			    	projectContent : projectContent_jsid,
			    	projectQuantity : projectQuantity_jsid,
			    	totalInvestment : totalInvestment_jsid,
			    	diameterSection : diameterSection_jsid,
			    	diameterLength : diameterLength_jsid
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

function toRemove(id){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/detailedForm/delete',
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
