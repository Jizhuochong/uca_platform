$(document).ready(function() {
	$('#list_data').datagrid({
		title : '预约工作量办理统计列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/wStatistics/list',
		method : 'post',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'projectName',title : '工程名称'},
		    {field : 'devOrg',title : '预约单位名称'},
			{field : 'applyTime',title : '申请时间'},
			{field : 'appointmentTime',title : '预约来馆日期'},
			{field : 'userName',title : '审核人姓名'},
			{field : 'checkTime',title : '审核时间'}
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
