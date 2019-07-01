$(document).ready(function(){
	$('#list_data').datagrid({  
	    border: false,  
	    fitColumns:true,
	    singleSelect:true,
	    collapsible:false,//是否可折叠的  
	    fit: true,//自动大小  
	    url:rfPath+'/mrapplystic/statisticsMeetUser',  
	    pagination:false,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#select_bar',
	    columns:[[
	             {field:'meetingName',title:'会议室名称'},
	             {field:'conferenceName',title:'会议名称'},
	             {field:'meetBeginTime',title:'会议开始时间'},
	             {field:'meetEndTime',title:'会议结束时间'},
	             {field:'meetUserNum',title:'参会人数'}
	      	   ]],
		onLoadSuccess: function (data) {
			$('#meetUserCount').html("参会总人数:"+data.meetUserCount);
	    }
	});
	
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
	$('#list_data').datagrid('load', {
		bgTime : $('#bgTime').datebox('getValue'),
	 	endTime : $('#endTime').datebox('getValue')
	});
}
