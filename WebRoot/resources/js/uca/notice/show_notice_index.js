$(document).ready(function(){
	var type = $("#dType").val();
	$('#list_data').datagrid({  
//	    width: 600,  
//	    height: 'auto',  
	    border: false,  
	    fitColumns:true,
	    singleSelect:true,
	    collapsible:false,//是否可折叠的  
	    fit: true,//自动大小  
	    url:rfPath+'/notice/listShowNotice',  
//	    remoteSort:false,   //禁止自适应宽度、可以水平滚动
	    idField:'id',  
	    pagination:true,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#select_bar',
	    columns:[[
	             {field:'title',title:'标题'},
	             {field:'releaseUserName',title:'发布人'},
	             {field:'isRead',title:'阅读状态', formatter: function(value,row,index){
	            	 var rtn = "";
            		 switch(row.isRead){
	            	 	case 1:
	            	 		rtn = "已阅读";
	            	 		break;
	            	 	case 2:
	            	 		rtn = "未阅读";
	            	 		break;
	            	 	default:
	            	 		rtn = "";
	            	 		break;
	            	 }
	            	
	            	 return rtn;
	             }},
	             {field:'releaseTime',title:'发布时间'},
	             {field:'operation',title:'操作', 
	    			   formatter: function(value,row,index){
	    				   var op = "";
    					   op += "<a href='javascript:getObjByParam("+row.noticeId+","+row.noticeUserId+");'>查看</a>";
    					   op += "&nbsp;/&nbsp;<a href='javascript:toRemove("+row.noticeUserId+");'>删除</a>";
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
function toSearch(){
	$('#list_data').datagrid('load',{
		dTitle:$("#fmsearch #dTitle").val(),
		dContent:$("#fmsearch #dContent").val()
	});
}

function getObjByParam(objId,nId){
	var dataObj = null;
	if(objId != ''){
		dataObj = {id : objId,nId: nId};
	}
	$.ajax({
	    url: rfPath + '/notice/getReadObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: dataObj,
	    success: function(data){
//	    	$('#form_edit').form("clear");
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#noticeId').val(obj.noticeId == null || obj.noticeId == 0?"":obj.noticeId);
	    		$('#title').val(obj.title != null?obj.title:"");
	    		$('#content').val(obj.content != null?obj.content:"");
	    		$('#fileDown').attr("href",obj.fileUrl != null&&obj.fileUrl != "null"?rfPath + "/download_file/dbp?p="+obj.fileUrl+"&fn="+decodeURI(obj.sourceFileName):"");
	    		$('#fileDown').text(obj.sourceFileName != null&&obj.sourceFileName != "null"?obj.sourceFileName:"");
	    		$('#releaseStatus').combobox('select',obj.releaseStatus);
	    		$('#releaseObj').val(obj.releaseObj);
	    		$('#contactUser').val(obj.contactUser);
	    		$('#tel').val(obj.tel);
	    		$('#fax').val(obj.fax);
	    		$('#email').val(obj.email);
	    		$('#edit_dialog').dialog('open').dialog('setTitle','通知通告查看');
	    		$('#list_data').datagrid('reload');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function toRemove(dId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/notice/deleteByRead',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	dId : dId
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