$(document).ready(function(){ 
	$('#list_data').datagrid({  
//	    width: 600,  
//	    height: 'auto',  
	    border: false,  
	    fitColumns:true,
	    singleSelect:true,
	    collapsible:false,//是否可折叠的  
	    fit: true,//自动大小  
	    url:rfPath+'/applySubscribe/applyList?type='+$("#dType").val(),  
//	    remoteSort:false,   //禁止自适应宽度、可以水平滚动
	    idField:'id',  
	    pagination:true,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#select_bar',
	    columns:[[
	             {field:'projectName',title:'工程名称'},
	             {field:'orderNum',title:'顺序号'},
	             {field:'uploadTime',title:'上传时间'},
	             {field:'queryStatus',title:'预约状态', formatter: function(value,row,index){
	            	 var rtn = "";
	            	 switch(row.queryStatus){
	            	 	case 1:
	            	 		rtn = "已预约";
	            	 		break;
	            	 	case 2:
	            	 		rtn = "可预约";
	            	 		break;
	            	 	default:
	            	 		rtn = "";
	            	 		break;
	            	 }
	            	 return rtn;
	             }},
	             {field:'operation',title:'操作', 
	    			   formatter: function(value,row,index){
	    				   var op = "";
	    				   switch(row.queryStatus){
	    				   	case 1:
	    				   		op += "<a href='javascript:commit("+row.archivesId+",2);'>取消预约</a>";
		            	 		break;
		            	 	case 2:
//		            	 		op += "<a href='javascript:commit("+row.archivesId+",1);'>申请预约</a>";
		            	 		op += "<a href='javascript:applyTime("+row.archivesId+");'>申请预约</a>";
		            	 		break;
		            	 	default:
		            	 		break;
	    				   }
	    				   return op;
	    			   }
	              }
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

function applyTime(bId){
	$('#form_edit').form("clear");
	$("#addButton").show();
	$("#bId_jsid").val(bId);
	$('#edit_dialog').dialog('open').dialog('setTitle','申请预约');
}

function applyCommit(){
	var bId = $("#bId_jsid").val(), applyTime_jsid = $("#applyTime_jsid").datebox('getValue');
	if(bId == null || bId.length == 0 || bId == ""){
		parent.$.messager.alert('提示', "请选择要申请预约的数据记录！");
		return;
	}
	if(applyTime_jsid == null || applyTime_jsid.length == 0 || applyTime_jsid == ""){
		parent.$.messager.alert('提示', "请选择预约时间！");
		return;
	}
	var options = {
		url: rfPath + '/applySubscribe/apply',
		type: 'POST',
		dataType: 'json',
		data: {archivesId : bId,queryStatus : 1,applyTime:applyTime_jsid},
		success: function(data){
	    	if(data.success) {
	    		$('#edit_dialog').dialog('close');
	    		parent.$.messager.alert('提示', data.msg);
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    	$('#list_data').datagrid('reload');
	    }
	};
	parent.$.messager.confirm('Confirm','是否确定申请预约？',function(r){
      	if (r){
      		$("#form_edit").ajaxSubmit(options);
      	}
 	});
}

function commit(bId, applyFlag){
	parent.$.messager.confirm('Confirm','是否确定'+(applyFlag==1?'申请预约':'取消预约')+'？',function(r){
      	if (r){
      		var dataObj = {archivesId : bId,queryStatus : applyFlag};
      		$.ajax({
      		    url: rfPath + '/applySubscribe/apply',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: dataObj,
      		    success: function(data){
      		    	if(data.success) {
      		    		parent.$.messager.alert('提示', data.msg);
      		    		parent.updateTab();
      		    	} else {
      		    		parent.$.messager.alert('提示', data.msg);
      		    	}
      		    }
      		});
      	}
 	});
}