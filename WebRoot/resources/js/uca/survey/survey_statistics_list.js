$(document).ready(function() {
	$('#list_data').datagrid({
		title : '问卷调查统计列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/survey/statisticslist',
		method : 'post',
		idField : 'id',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'title',title : '问卷调查标题'},
			{field : 'type',title : '问卷调查分类', formatter: function(value,row,index){
	           	 var rtn = "";
	        	 switch(row.type){
	        	 	case 1:
	        	 		rtn = "有选择项方式";
	        	 		break;
	        	 	case 2:
	        	 		rtn = "无选择项方式";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				var op = "";
				switch(row.type){
					case 1:
				   		op += "<a href='javascript:toStatisticsSurveyOptionResult("+ row.id + ", \"" + row.title + "\");' class='easyui-linkbutton'>问卷调查选项结果统计</a>";
					break;
					case 2:
						op += "<a href='javascript:toStatisticsSurveyResult("+ row.id + ", \"" + row.title + "\");' class='easyui-linkbutton'>问卷调查结果统计</a>";
						break;
					default:
						break;
				}
				return op;
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

function toStatisticsSurveyOptionResult(id, title){
	parent.addTab("","问卷调查选项结果统计["+title+"]","/surveyoptionresult/statistics?surveyId="+id,null);
}

function toStatisticsSurveyResult(id, title){
	parent.addTab("","问卷调查结果统计["+title+"]","/surveyresult/statistics?surveyId="+id,null);
}
