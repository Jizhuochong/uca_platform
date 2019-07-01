$(document).ready(function() {
	$('#list_data').datagrid({
		title : '在线答题试卷列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/onlineExam/list',
		method : 'post',
		idField : 'epId',
		pagination : true,// 分页控件
		columns : [ [
			{field : 'epId',title : '试卷编号'},
			{field : 'epName',title : '试卷名称'},
			{field : 'examTime',title : '考试时限(单位：分钟)'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "&nbsp;<a href='javascript:toStartExam("+ row.epId + ", \"" + row.epName + "\");' class='easyui-linkbutton'>开始答题</a>&nbsp;";
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

function toStartExam(epId, epName){
	parent.addTab("","试卷["+epName+"]-在线答题开始","/onlineExam/start?epId="+epId,null);
};
