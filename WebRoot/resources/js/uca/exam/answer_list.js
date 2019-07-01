$(document).ready(function() {
	$('#list_data').datagrid({
		title : '答题库列表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/answer/list',
		method : 'post',
		idField : 'answerId',
		pagination : true,// 分页控件
		toolbar : '#toolbar',
		columns : [ [
			{field : 'epName',title : '试卷名称'},
			{field : 'eqNumber',title : '题号'},
			{field : 'eqName',title : '试题名称'},
			{field : 'userName',title : '答题人'},
			{field : 'score',title : '答题分数'},
	        {field : 'answerTimeStr',title : '答题时间'},
	        {field : 'status',title : '是否已阅卷', formatter: function(value,row,index){
	           	 var rtn = "";
	        	 switch(row.status){
	        	 	case 1:
	        	 		rtn = "未阅卷";
	        	 		break;
	        	 	case 2:
	        	 		rtn = "已阅卷";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
	        }},
	        {field : 'operation',title : '操作',formatter : function(value, row, index) {
//	        	return "<a href='javascript:toReadOver("+ row.answerId + ");' class='easyui-linkbutton'>阅卷</a>" +
//	        			"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.answerId+ ");' class='easyui-linkbutton'>删除</a>";
	        	
//	        	return "<a href='javascript:toReadOver("+ row.answerId + ");' class='easyui-linkbutton'>阅卷</a>";
	        	
	        	var rtn = "";
	        	 switch(row.status){
	        	 	case 1:
	        	 		rtn = "<a href='javascript:toReadOver("+ row.answerId + ");' class='easyui-linkbutton'>阅卷</a>";
	        	 		break;
	        	 	case 2:
	        	 		rtn = "";
	        	 		break;
	        	 	default:
	        	 		rtn = "";
	        	 		break;
	        	 }
	        	 return rtn;
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

function toSearch() {
	$('#list_data').datagrid('load', {
		epName : $("#epName_search_jsid").val(),
		userName : $("#userName_search_jsid").val()
	});
};

function clearForm() {
	$('#epName_search_jsid').val('');
	$('#userName_search_jsid').val('');
};

function toReadOver(answerId){
	$.ajax({
	    url: rfPath + '/answer/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	answerId : answerId
	    },
	    success: function(data){
	    	if(data.success) {
	    		$('#answerId_jsid').val(data.answerId);
	    		$('#answers_jsid').html(data.answers);
	    		
//	    		$('#answerUserId_jsid').html(data.answerUserId);
	    		$('#answerUser_jsid').html(data.answerUser);
	    		
//	    		$('#epId_jsid').html(data.eqId);
	    		$('#epName_jsid').html(data.epName);
	    		
//	    		$('#eqId_jsid').html(data.eqId);
//	    		$('#eqNumber_jsid').html(data.eqNumber);
	    		$('#eqName_jsid').html(data.eqName);
	    		$('#eqScore_jsid').html(data.eqScore);
	    		$('#eqType_jsid').val(data.eqType);
	    		if(data.eqType != 4) {
	    			$('#eqOptions_jsid').html(data.eqOptions);
		    		$('#eqAnswer_jsid').html(data.eqAnswer);
		    		$('#opan_tr_jsid').show();
		    		$('#score_tr_jsid').hide();
	    		} else {
	    			$('#opan_tr_jsid').hide();
	    			$('#score_tr_jsid').show();
	    		}
	    		
	    		$('#obj_dialog').dialog('open').dialog('setTitle','阅卷');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
};

function commitScore(){
	var answerId_jsid = $("#answerId_jsid").val(),eqType_jsid = $("#eqType_jsid").val(),
			score_jsid = $("#score_jsid").val();
		
	if(answerId_jsid == null || answerId_jsid.length == 0 || answerId_jsid == ""){
		parent.$.messager.alert('提示', "请选择要打分的答题记录！");
		return;
	}
	
	if(eqType_jsid == 4) {
		if(score_jsid == null || score_jsid.length == 0 || score_jsid == ""){
			parent.$.messager.alert('提示', "请输入打分分数！");
			return;
		}
	} else {
		score_jsid = "0";
	}
	
	parent.$.messager.confirm('Confirm','是否确定打分？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/answer/commitScore',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	answerId : answerId_jsid,
			    	score : score_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#obj_dialog').dialog('close');
			    	} else {
			    		parent.$.messager.alert('提示', data.msg);
			    	}
			    },
			    error: function(){
			    	parent.$.messager.alert('提示', "打分失败，请重试！");
			    }
			});
      	}
 	});
};
