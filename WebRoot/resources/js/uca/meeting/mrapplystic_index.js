$(document).ready(function(){
	$('#list_data').datagrid({  
//	    width: 600,  
//	    height: 'auto',  
	    border: false,  
	    fitColumns:true,
	    singleSelect:true,
	    collapsible:false,//是否可折叠的  
	    fit: true,//自动大小  
	    url:rfPath+'/mrapplystic/list',  
//	    remoteSort:false,   //禁止自适应宽度、可以水平滚动
	    idField:'id',  
	    pagination:true,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#select_bar',
	    columns:[[
	             {field:'name',title:'会议室名称'},
	             {field:'statisticsTime',title:'时间'},
	             {field:'applyCount',title:'预约次数'}
	      	   ]]
	});  
	//设置分页控件  
	var p = $('#list_data').datagrid('getPager');  
	$(p).pagination({  
	    beforePageText: '第',//页数文本框前显示的汉字  
	    afterPageText: '页    共 {pages} 页',  
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
	});
	$("#period").hide();
	
	$("#bgTime").datebox({
		formatter : formatDate
	});

	$("#endTime").datebox({
		formatter : formatDate
	});

	function formatDate(date) {
		var month = (date.getMonth() + 1);
		var dDate = date.getDate();
		if(month < 10){
			month = "0" + month;
		}
		if(dDate < 10){
			dDate = "0" + dDate;
		}
		return date.getFullYear() + "-" + month
				+ "-" + dDate;
	}
}); 


//搜索
function toSearch() {
	if($('#dType').combobox('getValue') == ''){
		parent.$.messager.alert('提示', "请选择统计周期！");
		return false;
	}
	$('#list_data').datagrid('load', {
		type : $('#dType').combobox('getValue'),
		mrId : $('#dMrId').combobox('getValue'),
		bgTime : $('#bgTime').datebox('getValue'),
	 	endTime : $('#endTime').datebox('getValue')
	});
}