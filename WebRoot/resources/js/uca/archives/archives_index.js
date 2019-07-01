$(document).ready(function(){ 
	$('#list_data').datagrid({  
//	    width: 600,  
//	    height: 'auto',  
	    border: false,  
	    fitColumns:true,
	    singleSelect:true,
	    collapsible:false,//是否可折叠的  
	    fit: true,//自动大小  
	    url:rfPath+'/archives/list?type='+$("#dType").val(),  
//	    remoteSort:false,   //禁止自适应宽度、可以水平滚动
	    idField:'id',  
	    pagination:true,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#select_bar',
	    columns:[[
	             {field:'projectName',title:'工程名称'},
	             {field:'archivesNum',title:'档号'},
	             {field:'uploadTime',title:'上传时间'},
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
	             {field:'userName',title:'经办人'},
	             {field:'instruction',title:'审核批示', formatter: function(value,row,index){
	            	 return '<textarea readonly="true">'+row.instruction+'</textarea>';
	             }},
	             {field:'operation',title:'操作', 
	    			   formatter: function(value,row,index){
	    				   var op = "";
	    				   switch(row.checkStatus){
	    				   	case 0:
	    				   		op += "<a href='javascript:getObjByParam("+row.archivesId+","+$("#dType").val()+");'>编辑</a>";
	    				   		op += row.fileUrl != null?"&nbsp;/&nbsp;<a href='"+rfPath + "/download_file/dbp?p="+row.fileUrl+"&fn="+decodeURI(row.sourceFileName)+"&contentTypeForView=text/xml"+"' target='_blank'>查看</a>":"";
		            	 		break;
		            	 	case 2:
		            	 		op += "<a href='javascript:getObjByParam("+row.archivesId+","+$("#dType").val()+");'>重新上传</a>";
		            	 		op += row.fileUrl != null?"&nbsp;/&nbsp;<a href='"+rfPath + "/download_file/dbp?p="+row.fileUrl+"&fn="+decodeURI(row.sourceFileName)+"&contentTypeForView=text/xml"+"' target='_blank'>查看</a>":"";
		            	 		op += (row.fileUrlOther!=null&&row.fileUrlOther!="")?"&nbsp;/&nbsp;<a href='"+rfPath + "/download_file/dbp?p="+row.fileUrlOther+"&fn="+decodeURI(row.sourceFileNameOther)+"' target='_blank'>下载经办人协助修改文件</a>":"";
		            	 		break;
		            	 	case 1:
		            	 	case 3:
		            	 		op += "<a href='"+(row.fileUrl != null?rfPath + "/download_file/dbp?p="+row.fileUrl+"&fn="+decodeURI(row.sourceFileName):"###")+"' target='_blank'>下载</a>";
		            	 		op += row.fileUrl != null?"&nbsp;/&nbsp;<a href='"+rfPath + "/download_file/dbp?p="+row.fileUrl+"&fn="+decodeURI(row.sourceFileName)+"&contentTypeForView=text/xml"+"' target='_blank'>查看</a>":"";
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

function getObjByParam(objId, tp){
	var dataObj = null;
	if(objId != ''){
		dataObj = {id : objId,type : tp};
	}else{
		dataObj = {type : tp};
	}
	$.ajax({
	    url: rfPath + '/archives/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: dataObj,
	    success: function(data){
//	    	$('#form_edit').form("clear");
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#archivesId').val(obj.archivesId == null || obj.archivesId == 0?"":obj.archivesId);
	    		$('#checkStatus').val(obj.checkStatus);
	    		$('#type').val(obj.type);
	    		//if(obj.type == 1)
	    			$('#projectName').val(obj.projectName);
	    		$('#fileDown').attr("href",obj.fileUrl != null?rfPath + "/download_file/dbp?p="+obj.fileUrl+"&fn="+decodeURI(obj.sourceFileName):"");
	    		$('#fileDown').text(obj.sourceFileName != null?obj.sourceFileName:"");
	    		$("#sourceFileName").val(decodeURI(obj.sourceFileName));
	    		$('#fileUrl').val(obj.fileUrl != null?obj.fileUrl:"");
	    		$('#file').val("");
//	    		if(obj.type == 1){
	    			$('#orgId').combobox('setValue', obj.orgId != null?obj.orgId:-2);
		    		setHandingPerson(obj.orgId, obj.handingPersonId != null?obj.handingPersonId:-1);
//	    		}else{
//	    			setHandingPerson(-1,obj.handingPersonId != null?obj.handingPersonId:-1);
//	    		}
	    		$('#edit_dialog').dialog('open').dialog('setTitle',(tp==1?'工程':'声像')+'档案维护');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}


function commit(prm){
	var fileItemV = $("#fileUrl").val();
	if(fileItemV == ""){
		parent.$.messager.alert('提示', "请上传电子档案！");
		return false;
	}
	if($("#dType").val() == 2) {
		var i = fileItemV.indexOf(".zip");
	    if (i == -1) {
	        i = fileItemV.indexOf(".rar");
	    }
		if (i == -1) {
			parent.$.messager.alert('提示', "声像档案文件只能是zip或rar格式！");
			return false;
		}
	}
	
	if(prm == 1 && $('#handingPersonId').combobox('getValue') == ""){
		parent.$.messager.alert('提示', "请选择经办人！");
		return false;
	}
	
	/*if(prm == 1 && $("#dType").val() == 1 && $('#projectName').val() == ""){
		parent.$.messager.alert('提示', "请填写工程名称！");
		return false;
	}*/
	if(prm == 1 && $('#projectName').val() == ""){
		parent.$.messager.alert('提示', "请填写工程名称！");
		return false;
	}
	$("#type").val($("#dType").val());
	$("#checkStatus").val(prm);
	
	/*var options = {
		type : 'post',
		dataType : 'json',
		url:rfPath+'/archives/save',
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
 	});*/
	parent.$.messager.confirm('Confirm','是否确定'+(prm==0?'保存':'提交审核')+'？',function(r){
      	/*if (r){
      		$('#form_edit').form('submit', {
      			 url: rfPath+'/archives/save',
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
      	}*/
      	if (r){
      		var dataObj = {archivesId : $("#archivesId").val(),checkStatus:$("#checkStatus").val(),fileUrl : fileItemV,sourceFileName:$("#sourceFileName").val(),handingPersonId:$("#handingPersonId").combobox('getValue'),type:$("#type").val(),projectName:$("#projectName").val()};
      		$.ajax({
      		    url: rfPath + '/archives/save',
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

$(function(){
//	$('#handingPersonId').combobox('reload', rfPath + '/ucasecurity/getHandingPersonList');
	$('#orgId').combobox({
		onSelect: function(record){
			setHandingPerson($('#orgId').combobox('getValue'), -1);
		}
	});
});

function setHandingPerson(n,p){
	if(n != null && n != -2){
		//请求经办人列表
		$('#handingPersonId').combobox('reload', rfPath + '/ucasecurity/getHandingPersonList?orgId='+n);
		$('#handingPersonId').combobox({
			onLoadSuccess : function(){
				$('#handingPersonId').combobox('select',p);
			}
		});
	}else{
		$('#handingPersonId').combobox('reload', rfPath + '/ucasecurity/getHandingPersonList?orgId=-2');
		$('#handingPersonId').combobox({
			onLoadSuccess : function(){
				$('#handingPersonId').combobox('select',-1);
			}
		});
	}
}

function ajaxFileUpload(){
	var fileItemV = $("#file").val();
	if(fileItemV == ""){
		parent.$.messager.alert('提示', "请选择文件");
		return false;
	}
	if($("#dType").val() == 2) {
		var i = fileItemV.indexOf(".zip");
	    if (i == -1) {
	        i = fileItemV.indexOf(".rar");
	    }
		if (i == -1) {
			parent.$.messager.alert('提示', "声像档案文件只能是zip或rar格式！");
			return false;
		}
	}else{
		if(fileItemV.indexOf(".xml") == -1){
			parent.$.messager.alert('提示', "工程档案文件只能是xml格式！");
			return false;
		}
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
		    $("#projectName").val(data.fileName.substring(0,data.fileName.length-4));
//		    $("#loading").hide();
			/*if (data.success){
				parent.$.messager.alert('提示', data.msg);
	    		$('#list_data').datagrid('reload');
	    		$('#import_dialog').dialog('close');
			} else {
				parent.$.messager.alert('提示', "批量导入失败："+data.msg+"！");
			}*/
		},
		error : function(msg){
			$("#loading").hide();
			parent.$.messager.alert('提示', "上传档案失败，请重试！");
		}
	};
	$("#form_edit").ajaxSubmit(options);
    /*$("#loading").show();
    $.ajaxFileUpload(
        {
           url:rfPath + '/upload_file/upload',
           secureuri:false,
           fileElementId:'file',
           dataType: 'json',
           success: function (data, status){
        	   alert(data);
			   $("#fileUrl").val(decodeURI(data.fileUrl));
			   $("#fileDown").attr("href",rfPath + "/download_file/dbp?p=" + decodeURI(data.fileUrl) + "&fn="+decodeURI(data.fileName));
			   $("#fileDown").text(decodeURI(data.fileName));
			   $('#file').val("");
			   $("#sourceFileName").val(decodeURI(data.fileName));
			   $("#loading").hide();
            },
            error: function (data, status, e){
            	$("#loading").hide();
                parent.$.messager.alert('提示', e);
            }
        }
    );
    return false;*/
}