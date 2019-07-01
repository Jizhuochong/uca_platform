function buildPage(param){
	$("div.holder").jPages({
		containerID : "itemContainer",
		perPage : 10,
		url : rfPath + '/archives_catalog/list',
		params: {"projectName":param},
		fnRender : function(data){
			var rtnStr = "";
			rtnStr += '<td width="12%">'
				+ data.archivesNum
				+ '</td>'
				+ '<td width="20%">'
				+ data.projectName
				+ '</td>'
				+ '<td width="15%">'
				+ data.projectAddress
				+ '</td>'
				+ '<td width="15%">'
				+ data.devOrg
				+ '</td>'
				+ '<td width="15%">'
				+ data.planPerNum
				+ '</td>'
				+ '<td width="20%">'
				+ data.constructionUnit
				+ '</td>'
			return rtnStr;
		}
	});
}

function toSearch(){
	var bcode = $("#bcode").val();
	if(bcode == null || bcode == ""){
		parent.$.messager.alert('提示', "请输入验证码！");
		$("#bcode").focus();
		return false;
	}
	if(!checkCode(bcode)){
		parent.$.messager.alert('提示', "验证码输入错误，请重试！");
		refreshCaptcha();
		$("#bcode").focus();
		return false;
	}
	var projectname = $("#bprojectname").val();
	if(projectname == null || projectname == ""){
		parent.$.messager.alert('提示', "请输入工程名称！");
		refreshCaptcha();
		$("#bprojectname").focus();
		return false;
	}
	buildPage(projectname);
}

function checkCode(bcode){
	var flag = false;
	$.ajax({
	    url: rfPath + '/checkIdentifyingCode',
	    type: 'POST',
	    async: false,
	    dataType: 'json',
	    data: {code:bcode},
	    success: function(data){
	    	if(data.flag == true) {
	    		flag = true;
	    	} else {
	    		flag = false;
	    	}
	    }
	});
	return flag;
}

function refreshCaptcha() {
	$('#captchaImg').hide().attr(
		'src',
		rfPath + '/jcaptcha.jpg' + '?' + Math
		.floor(Math.random() * 100)).fadeIn();
}