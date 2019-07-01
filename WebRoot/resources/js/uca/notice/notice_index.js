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
	    url:rfPath+'/notice/list?type='+type,  
//	    remoteSort:false,   //禁止自适应宽度、可以水平滚动
	    idField:'id',  
	    pagination:true,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#select_bar',
	    columns:[[
	             {field:type == 1?'title':'sourceFileName',title:type == 1?'标题':'文件名称'},
	             {field:'releaseUserName',title:'发布人'},
	             {field:type == 1?'releaseStatus':'fileFormat',title:type == 1?'发布状态':'格式', formatter: function(value,row,index){
	            	 var rtn = "";
	            	 if(type == 1){
	            		 switch(row.releaseStatus){
		            	 	case 1:
		            	 		rtn = "已发布";
		            	 		break;
		            	 	case 2:
		            	 		rtn = "未发布";
		            	 		break;
		            	 	default:
		            	 		rtn = "";
		            	 		break;
		            	 }
	            	 }else{
	            		 rtn = row.fileFormat;
	            	 }
	            	 return rtn;
	             }},
	             {field:'releaseTime',title:'发布时间'},
	             {field:'operation',title:'操作', 
	    			   formatter: function(value,row,index){
	    				   var op = "";
	    				   if(type == 1){
	    					   op += "<a href='javascript:getObjByParam("+row.noticeId+");'>编辑</a>";
	    					   op += "&nbsp;/&nbsp;<a href='javascript:toRemove("+row.noticeId+");'>删除</a>";
	    				   }else{
	    					   op += "<a href='"+(row.fileUrl != null?rfPath + "/download_file/dbp?p="+row.fileUrl+"&fn="+decodeURI(row.sourceFileName):"###")+"' target='_blank'>下载</a>";
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

$.extend($.fn.validatebox.defaults.rules, {
	phoneOrMobileRex: {
	    validator: function(value){
	    var rex=/^1[3-8]+\d{9}$/;
	    var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	    if(rex.test(value)||rex2.test(value))
	    {
	      return true;
	    }else
	    {
	       return false;
	    }
	      
	    },
	    message: '请输入正确电话或手机格式'
	},
	phoneRex: {
	    validator: function(value){
	    var rex=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	    if(rex.test(value))
	    {
	      return true;
	    }else
	    {
	       return false;
	    }
	      
	    },
	    message: '请输入正确电话格式'
	}
});


//搜索
function toSearch(){
	$('#list_data').datagrid('load',{
		dTitle:$("#fmsearch #dTitle").val(),
		dContent:$("#fmsearch #dContent").val()
	});
}

function getObjByParam(objId){
	var dataObj = null;
	if(objId != ''){
		dataObj = {id : objId};
	}
	$.ajax({
	    url: rfPath + '/notice/getObjById',
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
	    		$("#sourceFileName").val(decodeURI(obj.sourceFileName));
	    		$('#fileUrl').val(obj.fileUrl != null?obj.fileUrl:"");
	    		$('#file').val("");
	    		$('#releaseStatus').combobox('select',obj.releaseStatus);
	    		$('#releaseObj').val(obj.releaseObj);
	    		$('#contactUser').val(obj.contactUser);
	    		$('#tel').val(obj.tel);
	    		$('#fax').val(obj.fax);
	    		$('#email').val(obj.email);

	    		var orgList = data.orgList;
	    		var showContent = "";
	    		for(var i = 0;i < orgList.length;i++){
	    			showContent += '<input type="checkbox" name="releaseOrg" value="'+orgList[i].id+'"'+(orgList[i].checked == true?' checked':'')+'/>'+orgList[i].name+'&nbsp;&nbsp;';
	    		}
	    		$('#orgList').html(showContent);
	    		$('#edit_dialog').dialog('open').dialog('setTitle','通知通告维护');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}


function commit(){
	if($('#title').val() == ""){
		parent.$.messager.alert('提示', "请输入标题！");
		return false;
	}
	if($('#content').val() == ""){
		parent.$.messager.alert('提示', "请输入内容！");
		return false;
	}
	if(!$('#form_edit').form('validate')){
		return false;
	}
	
	/*var options = {
		type : 'post',
		dataType : 'json',
		url:rfPath+'/notice/save',
		success : function(data) {
			if(data.success){
	    		$('#edit_dialog').dialog('close');
	    		parent.$.messager.alert('提示', data.msg);
	    	}else{
	    		parent.$.messager.alert('提示', data.msg);
	    	}
			parent.updateTab();
		}
	};*/
	//var chk_value =[];
	var chk_value = "";
	$('input[name="releaseOrg"]:checked').each(function(){ 
	//chk_value.push($(this).val()); 
		chk_value += $(this).val() + ",";
	});
	if(chk_value.lastIndexOf(",") != -1){
		chk_value = chk_value.substring(0, chk_value.lastIndexOf(","));
	}
	parent.$.messager.confirm('Confirm','是否确定保存？',function(r){
      	if (r){
      		var dataObj = {noticeId : $("#noticeId").val(),title:$("#title").val(),
      				content:$("#content").val(),fileUrl:$("#fileUrl").val(),sourceFileName:$("#sourceFileName").val(),
      				releaseStatus:$("#releaseStatus").combobox('getValue'),fileFormat:$("#fileFormat").val(),
      				fileSize:$("#fileSize").val(),releaseObj:$("#releaseObj").val(),contactUser:$("#contactUser").val(),
      				tel:$("#tel").val(),tel:$("#tel").val(),fax:$("#fax").val(),email:$("#email").val(),
      				releaseOrg:chk_value};
      		$.ajax({
      		    url: rfPath + '/notice/save',
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
      		    	parent.updateTab();
      		    }
      		});
      	}
 	});
}

function ajaxFileUpload(){
	if($('#file').val() == ""){
		parent.$.messager.alert('提示', "请选择文件");
		return false;
	}
	$("#loading").show();
	var options = {
		type : 'post',
		dataType : 'json',
		url:rfPath + '/upload_file/upload',
		success : function(data) {
			$("#loading").hide();
			parent.$.messager.alert('提示', data.msg);
			$("#fileUrl").val(data.fileUrl);
		    $("#fileDown").attr("href",rfPath + "/download_file/dbp?p=" + decodeURI(data.fileUrl) + "&fn="+decodeURI(data.fileName));
		    $("#fileDown").text(data.fileName);
		    $('#file').val("");
		    $("#sourceFileName").val(data.fileName);
		    $("#fileFormat").val(data.fileFormat);
		    $("#fileSize").val(data.fileSize);
		},
		error : function(msg){
			$("#loading").hide();
			parent.$.messager.alert('提示', "上传文件失败，请重试！");
		}
	};
	$("#form_edit").ajaxSubmit(options);
}

function toRemove(dId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/notice/delete',
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