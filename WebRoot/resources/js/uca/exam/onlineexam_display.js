function isSelectQuestion(epId) {
	var flag = false;
	$.ajax({
	    url: rfPath + '/onlineExam/getEqNumberByEpId',
	    type: 'POST',
	    dataType: 'json',
	    async: false,
	    data: {
	    	epId : epId
	    },
	    success: function(data){
	    	for(var i=0; i<data.eqNumberList.length; i++) {
	    		var val = $('#'+data.eqNumberList[i]).val();
	    		if(val == null || val.length == 0 || val == "") {
	    			parent.$.messager.alert('提示', "请作答题号为"+data.eqNumberList[i].split("_")[2]+"的试题！");
	    			flag = false;
	    			return false;
	    		} else {
	    			flag = true;
	    		}
	    	}
	    	return flag;
	    }
	});
	return flag;
};

function multipleSelect(eqId, eqNumber) {
	var result = "";
	$("input[name='multiple_selection_"+eqNumber+"']").each(function() {
		if ($(this).prop("checked")) {
			result += "1|";
		} else {
			result += "0|";
		}
	});
	$("#multiple_selection_"+eqId+"_"+eqNumber).val(result.substring(0,result.length-1));
	$("#eqNumber_select_"+eqNumber).val(result.substring(0,result.length-1));
};

function singleSelect(eqId, eqNumber) {
	var result = "";
	$("input[name='single_selection_"+eqNumber+"']").each(function() {
		if ($(this).prop("checked")) {
			result += "1|";
		} else {
			result += "0|";
		}
	});
	$("#single_selection_"+eqId+"_"+eqNumber).val(result.substring(0,result.length-1));
	$("#eqNumber_select_"+eqNumber).val(result.substring(0,result.length-1));
};

function setWendati(eqId, eqNumber) {
	var result = $("#wendati_"+eqId+"_"+eqNumber).val();
	$("#eqNumber_select_"+eqNumber).val(result);
};
