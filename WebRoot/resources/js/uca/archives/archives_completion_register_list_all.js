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
		url : rfPath + '/registerForm/listAll',
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
				return "<a href='javascript:toPrinter("+ row.id+ ");' class='easyui-linkbutton'>查看打印竣工档案登记表</a>";
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

function toPrinter(registerId){
	parent.addTab("","竣工档案登记表[\""+registerId+"\"]打印","/registerForm/toPrinter?id="+registerId,null);
}
