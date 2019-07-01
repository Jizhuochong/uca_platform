$(document).ready(function() {
	$('#list_data').datagrid({
		title : '学分库列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/credit/list',
		method : 'post',
		idField : 'creditId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'epName',title : '试卷名称'},
			{field : 'userName',title : '答题人'},
	        {field : 'results',title : '试卷成绩'},
	        {field : 'credits',title : '学分数'}
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
		epName : $("#epName_search_jsid").val(),
		userName : $("#userName_search_jsid").val()
	});
}

function clearForm() {
	$('#epName_search_jsid').val('');
	$('#userName_search_jsid').val('');
}
