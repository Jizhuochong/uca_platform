$(document).ready(function(){
	$('#list_data').datagrid({  
//	    width: 600,  
//	    height: 'auto',  
	    border: false,  
	    fitColumns:true,
	    singleSelect:true,
	    collapsible:false,//是否可折叠的  
	    fit: true,//自动大小  
	    url:rfPath+'/meeting/list',  
//	    remoteSort:false,   //禁止自适应宽度、可以水平滚动
	    idField:'id',  
	    pagination:true,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#select_bar',
	    columns:[[
	             {field:'name',title:'会议室名称'},
	             {field:'address',title:'会议室地址'},
	             {field:'peopleCount',title:'可容纳人数'},
	             {field:'status',title:'可用状态', formatter: function(value,row,index){
	            	 var rtn = "";
            		 switch(row.status){
	            	 	case 1:
	            	 		rtn = "可用";
	            	 		break;
	            	 	case 2:
	            	 		rtn = "不可用";
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
    					   op += "<a href='javascript:getObjByParam("+row.mrId+");'>编辑</a>";
    					   op += "&nbsp;/&nbsp;<a href='javascript:toRemove("+row.mrId+");'>删除</a>";
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
		dName:$("#fmsearch #dName").val(),
		dStatus:$("#fmsearch #dStatus").combobox('getValue')
	});
}

function getObjByParam(objId){
	var dataObj = null;
	if(objId != ''){
		dataObj = {id : objId};
	}
	$.ajax({
	    url: rfPath + '/meeting/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: dataObj,
	    success: function(data){
//	    	$('#form_edit').form("clear");
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#mrId').val(obj.mrId == null || obj.mrId == 0?"":obj.mrId);
	    		$('#name').val(obj.name != null?obj.name:"");
	    		$('#address').val(obj.address != null?obj.address:"");
	    		$('#peopleCount').val(obj.peopleCount != null?obj.peopleCount:"");
	    		$('#status').combobox('select',obj.status);
	    		$('#edit_dialog').dialog('open').dialog('setTitle','会议室维护');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

var r = /^[0-9]*[1-9][0-9]*$/
function commit(){
	if($('#name').val() == ""){
		parent.$.messager.alert('提示', "请输入会议室名称！");
		return false;
	}
	if($('#address').val() == ""){
		parent.$.messager.alert('提示', "请输入会议室地址！");
		return false;
	}
	if($('#peopleCount').val() == ""){
		parent.$.messager.alert('提示', "请输入可容纳人数！");
		return false;
	}
	
	if(!r.test($('#peopleCount').val())){
		parent.$.messager.alert('提示', "可容纳人数必须输入正整数！");
		return false;
	}
	var options = {
		type : 'post',
		dataType : 'json',
		url:rfPath+'/meeting/save',
		success : function(data) {
			if(data.success){
	    		$('#edit_dialog').dialog('close');
	    		parent.$.messager.alert('提示', data.msg);
	    	}else{
	    		parent.$.messager.alert('提示', data.msg);
	    	}
			parent.updateTab();
		}
	};
	parent.$.messager.confirm('Confirm','是否确定保存？',function(r){
      	if (r){
      		$("#form_edit").ajaxSubmit(options);
      	}
 	});
	/*parent.$.messager.confirm('Confirm','是否确定保存？',function(r){
      	if (r){
      		$('#form_edit').form('submit', {
      			 url: rfPath+'/meeting/save',
      			 success:function(data){
      				data = $.parseJSON(data);
      				if(data.success){
			    		$('#edit_dialog').dialog('close');
			    		parent.$.messager.alert('提示', data.msg);
			    	}else{
			    		parent.$.messager.alert('提示', data.msg);
			    	}
      				parent.updateTab();
      			 }
      		});
      	}
 	});*/
}

function toRemove(dId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/meeting/delete',
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