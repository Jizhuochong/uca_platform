function commit(){
	var surveyId_jsid = $("#surveyId_jsid").val(), result_jsid = $("#result_jsid").val();
	
	if(surveyId_jsid == null || surveyId_jsid.length == 0 || surveyId_jsid == ""){
		parent.$.messager.alert('提示', "请选择问卷调查！");
		return;
	}
	
	if(result_jsid == null || result_jsid.length == 0 || result_jsid == ""){
		parent.$.messager.alert('提示', "请输入问卷调查答复！");
		return false;
	}

	parent.$.messager.confirm('Confirm','是否确定提交？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/surveyresult/save',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	surveyId : surveyId_jsid,
			    	result : result_jsid
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
