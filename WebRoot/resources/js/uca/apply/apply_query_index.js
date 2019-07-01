$(document).ready(function(){ 
	$('#list_data').datagrid({  
//	    width: 600,  
//	    height: 'auto',  
	    border: false,  
	    fitColumns:true,
	    singleSelect:true,
	    collapsible:false,//是否可折叠的  
	    fit: true,//自动大小  
	    url:rfPath+'/applySubscribe/queryList?type='+$("#dType").val(),  
//	    remoteSort:false,   //禁止自适应宽度、可以水平滚动
	    idField:'id',  
	    pagination:true,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#select_bar',
	    columns:[[
	             {field:'projectName',title:'工程名称'},
	             {field:'orderNum',title:'顺序号'},
	             {field:'applyTime',title:'预约时间'},
	             {field:'checkStatus',title:'审核状态', formatter: function(value,row,index){
	            	 var rtn = "";
	            	 switch(row.checkStatus){
	            	 	case 1:
	            	 		rtn = "待审核";
	            	 		break;
	            	 	case 2:
	            	 		rtn = "审核未通过";
	            	 		break;
	            	 	case 3:
	            	 		rtn = "审核通过";
	            	 		break;
	            	 	default:
	            	 		rtn = "";
	            	 		break;
	            	 }
	            	 return rtn;
	             }},
	             {field:'checkTime',title:'审核时间'},
	             {field:'instruction',title:'审核批示', formatter: function(value,row,index){
	            	 return '<textarea>'+row.instruction+'</textarea>';
	             }}
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
}); 


//搜索
/*function toSearch(){
	$('#list_data').datagrid('load',{
		bname:$("#fmsearch #bname").val(),
		bcontent:$("#fmsearch #bcontent").val(),
	});
}*/