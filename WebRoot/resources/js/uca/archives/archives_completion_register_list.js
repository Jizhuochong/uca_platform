$(document).ready(function() {
	$('#list_data').datagrid({
		title : '办理竣工档案登记表列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/registerForm/list',
		method : 'post',
		idField : 'id',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'type',title : '登记表类型', formatter: function(value,row,index){
	           	 var rtn = "";
	        	 switch(row.type){
	        	 	case 1:
	        	 		rtn = "建筑工程";
	        	 		break;
	        	 	case 2:
	        	 		rtn = "市政公用";
	        	 		break;
	        	 	case 3:
	        	 		rtn = "市政管线";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
			{field : 'archiveWord',title : '档字'},
			{field : 'buildWord',title : '建字'},
			{field : 'buildUnit',title : '建设单位'},
			{field : 'mailingAddress',title : '通讯地址'},
			{field : 'zipCode',title : '邮政编码'},
			{field : 'registerPerson',title : '登记人'},
			{field : 'registerDate',title : '登记日期'},
			{field : 'phone',title : '电话'},
			{field : 'projectName',title : '工程名称'},
			{field : 'projectLocation',title : '工程地点'},
			{field : 'designUnit',title : '设计单位'},
			{field : 'constructionUnit',title : '施工单位'},
			{field : 'measureUnit',title : '测量单位'},
			{field : 'projectType',title : '工程类型'},
			{field : 'startDate',title : '开工日期'},
			{field : 'completionDate',title : '竣工日期'},
			{field : 'totalInvestment',title : '总投资(元)'},
			{field : 'remark',title : '备注'},
			{field : 'archivesManagers',title : '档案馆经办人'},
			{field : 'createUserName',title : '创建人'},
			{field : 'createTime',title : '创建日期'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.id + ");' class='easyui-linkbutton'>修改</a>"+
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.id+ ");' class='easyui-linkbutton'>删除</a>"+
						"&nbsp;/&nbsp;<a href='javascript:toDetailedFormList("+ row.id+"," + row.type + ");' class='easyui-linkbutton'>填报竣工档案详细信息</a>"+
						"&nbsp;/&nbsp;<a href='javascript:toPrinter("+ row.id+ ");' class='easyui-linkbutton'>查看打印竣工档案登记表</a>";
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

function toDetailedFormList(registerId, registerType){
	parent.addTab("","竣工档案登记详细信息表[\""+registerId+"\"]管理","/detailedForm/toList?registerId="+registerId+"&registerType="+registerType,null);
}

function toPrinter(registerId){
	parent.addTab("","竣工档案登记表[\""+registerId+"\"]打印","/registerForm/toPrinter?id="+registerId,null);
}

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
	$('#formName_A').form("clear");
	$("#addButton").show();
	$("#editButton").hide();
	$('#A_dialog').dialog('open').dialog('setTitle','添加');
}

function commit(){
	var type_jsid = $("#type_jsid").combobox('getValue'), 
		archiveWord_jsid = $("#archiveWord_jsid").val(),
		buildWord_jsid = $("#buildWord_jsid").val(), 
		buildUnit_jsid = $("#buildUnit_jsid").val(),
		mailingAddress_jsid = $("#mailingAddress_jsid").val(),
		zipCode_jsid = $("#zipCode_jsid").val(),
		registerPerson_jsid = $("#registerPerson_jsid").val(),
		registerDate_jsid = $('#registerDate_jsid').datebox('getValue'),
		phone_jsid = $("#phone_jsid").val(),
		projectName_jsid = $("#projectName_jsid").val(),
		projectLocation_jsid = $("#projectLocation_jsid").val(),
		designUnit_jsid = $("#designUnit_jsid").val(),
		constructionUnit_jsid = $("#constructionUnit_jsid").val(),
		measureUnit_jsid = $("#measureUnit_jsid").val(), 
		projectType_jsid = $("#projectType_jsid").val(), 
		startDate_jsid = $('#startDate_jsid').datebox('getValue'),
		completionDate_jsid = $('#completionDate_jsid').datebox('getValue'),
		totalInvestment_jsid = $('#totalInvestment_jsid').numberbox('getValue'),
		remark_jsid = $("#remark_jsid").val(),
		archivesManagers_jsid = $("#archivesManagers_jsid").val();
	if(type_jsid == null || type_jsid.length == 0 || type_jsid == ""){
		parent.$.messager.alert('提示', "请选择登记表类型！");
		return;
	}
	if(archiveWord_jsid == null || archiveWord_jsid.length == 0 || archiveWord_jsid == ""){
		parent.$.messager.alert('提示', "请输入档字！");
		return;
	}
	if(type_jsid == 1) {
		if(buildWord_jsid == null || buildWord_jsid.length == 0 || buildWord_jsid == ""){
			parent.$.messager.alert('提示', "请输入建字！");
			return;
		}
	}
	if(buildUnit_jsid == null || buildUnit_jsid.length == 0 || buildUnit_jsid == ""){
		parent.$.messager.alert('提示', "请输入建设单位！");
		return;
	}
	if(mailingAddress_jsid == null || mailingAddress_jsid.length == 0 || mailingAddress_jsid == ""){
		parent.$.messager.alert('提示', "请输入通讯地址！");
		return;
	}
	if(zipCode_jsid == null || zipCode_jsid.length == 0 || zipCode_jsid == ""){
		parent.$.messager.alert('提示', "请输入邮政编码！");
		return;
	}
	if(registerPerson_jsid == null || registerPerson_jsid.length == 0 || registerPerson_jsid == ""){
		parent.$.messager.alert('提示', "请输入登记人！");
		return;
	}
	if(registerDate_jsid == null || registerDate_jsid.length == 0 || registerDate_jsid == ""){
		parent.$.messager.alert('提示', "请输入登记日期！");
		return;
	}
	if(phone_jsid == null || phone_jsid.length == 0 || phone_jsid == ""){
		parent.$.messager.alert('提示', "请输入电话！");
		return;
	}
	if(projectName_jsid == null || projectName_jsid.length == 0 || projectName_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程名称！");
		return;
	}
	if(projectLocation_jsid == null || projectLocation_jsid.length == 0 || projectLocation_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程地点！");
		return;
	}
	if(designUnit_jsid == null || designUnit_jsid.length == 0 || designUnit_jsid == ""){
		parent.$.messager.alert('提示', "请输入设计单位！");
		return;
	}
	/*if(constructionUnit_jsid == null || constructionUnit_jsid.length == 0 || constructionUnit_jsid == ""){
		parent.$.messager.alert('提示', "请输入施工单位！");
		return;
	}*/
	if(type_jsid == 2 || type_jsid == 3) {
		/*if(measureUnit_jsid == null || measureUnit_jsid.length == 0 || measureUnit_jsid == ""){
			parent.$.messager.alert('提示', "请输入测量单位！");
			return;
		}*/
		if(projectType_jsid == null || projectType_jsid.length == 0 || projectType_jsid == ""){
			parent.$.messager.alert('提示', "请输入工程类型！");
			return;
		}
	}
	if(startDate_jsid == null || startDate_jsid.length == 0 || startDate_jsid == ""){
		parent.$.messager.alert('提示', "请输入开工日期！");
		return;
	}
	if(completionDate_jsid == null || completionDate_jsid.length == 0 || completionDate_jsid == ""){
		parent.$.messager.alert('提示', "请输入竣工日期！");
		return;
	}
	if(type_jsid == 3) {
		if(totalInvestment_jsid == null || totalInvestment_jsid.length == 0 || totalInvestment_jsid == ""){
			parent.$.messager.alert('提示', "请输入总投资(元)！");
			return;
		}
	}
	if(archivesManagers_jsid == null || archivesManagers_jsid.length == 0 || archivesManagers_jsid == ""){
		parent.$.messager.alert('提示', "请输入档案馆经办人！");
		return;
	}
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/registerForm/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	type : type_jsid,
			    	archiveWord : archiveWord_jsid,
			    	buildWord : buildWord_jsid,
			    	buildUnit : buildUnit_jsid,
			    	mailingAddress : mailingAddress_jsid,
			    	zipCode : zipCode_jsid,
			    	registerPerson : registerPerson_jsid,
			    	registerDate : registerDate_jsid,
			    	phone : phone_jsid,
			    	projectName : projectName_jsid,
			    	projectLocation : projectLocation_jsid,
			    	designUnit : designUnit_jsid,
			    	constructionUnit : constructionUnit_jsid,
			    	measureUnit : measureUnit_jsid,
			    	projectType : projectType_jsid,
			    	startDate : startDate_jsid,
			    	completionDate : completionDate_jsid,
			    	totalInvestment : totalInvestment_jsid,
			    	remark : remark_jsid,
			    	archivesManagers : archivesManagers_jsid
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
	    url: rfPath + '/registerForm/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : id
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#id_jsid').val(obj.id);
	    		$('#type_jsid').combobox('setValue', obj.type);
	    		$('#archiveWord_jsid').val(obj.archiveWord);
	    		if(obj.type == 1) {
	    			$("#buildWord_jsid").val(obj.buildWord);
	    		}
	    		$('#buildUnit_jsid').val(obj.buildUnit);
	    		$('#mailingAddress_jsid').val(obj.mailingAddress);
	    		$('#zipCode_jsid').val(obj.zipCode);
	    		$('#registerPerson_jsid').val(obj.registerPerson);
	    		$('#registerDate_jsid').datebox('setValue', obj.registerDate);
	    		$('#phone_jsid').val(obj.phone);
	    		$('#projectName_jsid').val(obj.projectName);
	    		$('#projectLocation_jsid').val(obj.projectLocation);
	    		$('#designUnit_jsid').val(obj.designUnit);
	    		$('#constructionUnit_jsid').val(obj.constructionUnit);
	    		if(obj.type == 2) {
	    			$("#measureUnit_jsid").val(obj.measureUnit);
	    			$('#projectType_jsid').val(obj.projectType);
	    		}
	    		$('#startDate_jsid').datebox('setValue', obj.startDate);
	    		$('#completionDate_jsid').datebox('setValue', obj.completionDate);
	    		if(obj.type == 3) {
	    			$('#totalInvestment_jsid').numberbox('setValue', obj.totalInvestment);
	    			
	    		}
	    		$('#remark_jsid').val(obj.remark);
	    		$('#archivesManagers_jsid').val(obj.archivesManagers);
	    		
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
	var id_jsid = $("#id_jsid").val(), type_jsid = $("#type_jsid").combobox('getValue'), 
		archiveWord_jsid = $("#archiveWord_jsid").val(),
		buildWord_jsid = $("#buildWord_jsid").val(), 
		buildUnit_jsid = $("#buildUnit_jsid").val(),
		mailingAddress_jsid = $("#mailingAddress_jsid").val(),
		zipCode_jsid = $("#zipCode_jsid").val(),
		registerPerson_jsid = $("#registerPerson_jsid").val(),
		registerDate_jsid = $('#registerDate_jsid').datebox('getValue'),
		phone_jsid = $("#phone_jsid").val(),
		projectName_jsid = $("#projectName_jsid").val(),
		projectLocation_jsid = $("#projectLocation_jsid").val(),
		designUnit_jsid = $("#designUnit_jsid").val(),
		constructionUnit_jsid = $("#constructionUnit_jsid").val(),
		measureUnit_jsid = $("#measureUnit_jsid").val(), 
		projectType_jsid = $("#projectType_jsid").val(), 
		startDate_jsid = $('#startDate_jsid').datebox('getValue'),
		completionDate_jsid = $('#completionDate_jsid').datebox('getValue'),
		totalInvestment_jsid = $('#totalInvestment_jsid').numberbox('getValue'),
		remark_jsid = $("#remark_jsid").val(),
		archivesManagers_jsid = $("#archivesManagers_jsid").val();
	if(id_jsid == null || id_jsid.length == 0 || id_jsid == ""){
		parent.$.messager.alert('提示', "请选择要修改的数据！");
		return;
	}
	if(type_jsid == null || type_jsid.length == 0 || type_jsid == ""){
		parent.$.messager.alert('提示', "请选择登记表类型！");
		return;
	}
	if(archiveWord_jsid == null || archiveWord_jsid.length == 0 || archiveWord_jsid == ""){
		parent.$.messager.alert('提示', "请输入档字！");
		return;
	}
	if(type_jsid == 1) {
		if(buildWord_jsid == null || buildWord_jsid.length == 0 || buildWord_jsid == ""){
			parent.$.messager.alert('提示', "请输入建字！");
			return;
		}
	}
	if(buildUnit_jsid == null || buildUnit_jsid.length == 0 || buildUnit_jsid == ""){
		parent.$.messager.alert('提示', "请输入建设单位！");
		return;
	}
	if(mailingAddress_jsid == null || mailingAddress_jsid.length == 0 || mailingAddress_jsid == ""){
		parent.$.messager.alert('提示', "请输入通讯地址！");
		return;
	}
	if(zipCode_jsid == null || zipCode_jsid.length == 0 || zipCode_jsid == ""){
		parent.$.messager.alert('提示', "请输入邮政编码！");
		return;
	}
	if(registerPerson_jsid == null || registerPerson_jsid.length == 0 || registerPerson_jsid == ""){
		parent.$.messager.alert('提示', "请输入登记人！");
		return;
	}
	if(registerDate_jsid == null || registerDate_jsid.length == 0 || registerDate_jsid == ""){
		parent.$.messager.alert('提示', "请输入登记日期！");
		return;
	}
	if(phone_jsid == null || phone_jsid.length == 0 || phone_jsid == ""){
		parent.$.messager.alert('提示', "请输入电话！");
		return;
	}
	if(projectName_jsid == null || projectName_jsid.length == 0 || projectName_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程名称！");
		return;
	}
	if(projectLocation_jsid == null || projectLocation_jsid.length == 0 || projectLocation_jsid == ""){
		parent.$.messager.alert('提示', "请输入工程地点！");
		return;
	}
	if(designUnit_jsid == null || designUnit_jsid.length == 0 || designUnit_jsid == ""){
		parent.$.messager.alert('提示', "请输入设计单位！");
		return;
	}
	/*if(constructionUnit_jsid == null || constructionUnit_jsid.length == 0 || constructionUnit_jsid == ""){
		parent.$.messager.alert('提示', "请输入施工单位！");
		return;
	}*/
	if(type_jsid == 2 || type_jsid == 3) {
		/*if(measureUnit_jsid == null || measureUnit_jsid.length == 0 || measureUnit_jsid == ""){
			parent.$.messager.alert('提示', "请输入测量单位！");
			return;
		}*/
		if(projectType_jsid == null || projectType_jsid.length == 0 || projectType_jsid == ""){
			parent.$.messager.alert('提示', "请输入工程类型！");
			return;
		}
	}
	if(startDate_jsid == null || startDate_jsid.length == 0 || startDate_jsid == ""){
		parent.$.messager.alert('提示', "请输入开工日期！");
		return;
	}
	if(completionDate_jsid == null || completionDate_jsid.length == 0 || completionDate_jsid == ""){
		parent.$.messager.alert('提示', "请输入竣工日期！");
		return;
	}
	if(type_jsid == 3) {
		if(totalInvestment_jsid == null || totalInvestment_jsid.length == 0 || totalInvestment_jsid == ""){
			parent.$.messager.alert('提示', "请输入总投资(元)！");
			return;
		}
	}
	if(archivesManagers_jsid == null || archivesManagers_jsid.length == 0 || archivesManagers_jsid == ""){
		parent.$.messager.alert('提示', "请输入档案馆经办人！");
		return;
	}
	parent.$.messager.confirm('Confirm','是否确定修改？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/registerForm/edit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	id : id_jsid,
			    	type : type_jsid,
			    	archiveWord : archiveWord_jsid,
			    	buildWord : buildWord_jsid,
			    	buildUnit : buildUnit_jsid,
			    	mailingAddress : mailingAddress_jsid,
			    	zipCode : zipCode_jsid,
			    	registerPerson : registerPerson_jsid,
			    	registerDate : registerDate_jsid,
			    	phone : phone_jsid,
			    	projectName : projectName_jsid,
			    	projectLocation : projectLocation_jsid,
			    	designUnit : designUnit_jsid,
			    	constructionUnit : constructionUnit_jsid,
			    	measureUnit : measureUnit_jsid,
			    	projectType : projectType_jsid,
			    	startDate : startDate_jsid,
			    	completionDate : completionDate_jsid,
			    	totalInvestment : totalInvestment_jsid,
			    	remark : remark_jsid,
			    	archivesManagers : archivesManagers_jsid
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
      		    url: rfPath + '/registerForm/delete',
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
