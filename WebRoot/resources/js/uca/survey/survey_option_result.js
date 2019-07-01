function multipleSelect() {
	var result = "";
	$("input[name='multiple_selection']").each(function() {
		if ($(this).prop("checked")) {
			result += $(this).val()+",";
		}
	});
	$("#surveyOptionIds_jsid").val(result.substring(0,result.length-1));
};

function commit(){
	var surveyId_jsid = $("#surveyId_jsid").val(), surveyOptionIds_jsid = $("#surveyOptionIds_jsid").val();
	
	if(surveyId_jsid == null || surveyId_jsid.length == 0 || surveyId_jsid == ""){
		parent.$.messager.alert('提示', "请选择问卷调查！");
		return;
	}
	
	if(surveyOptionIds_jsid == null || surveyOptionIds_jsid.length == 0 || surveyOptionIds_jsid == ""){
		parent.$.messager.alert('提示', "请选择至少一个问卷调查选项！");
		return false;
	}

	parent.$.messager.confirm('Confirm','是否确定提交？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/surveyoptionresult/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	surveyId : surveyId_jsid,
			    	surveyOptionIds : surveyOptionIds_jsid
			    },
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		window.location.href = rfPath+'/survey/toCommitList';
			    	} else {
			    		parent.$.messager.alert('提示', data.msg);
			    	}
			    },
			    error: function(){
			    	parent.$.messager.alert('提示', "提交失败，请重试！");
			    }
			});
      	}
 	});
};
