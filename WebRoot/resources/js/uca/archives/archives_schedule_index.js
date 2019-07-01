function buildPage(param){
	$("div.holder").jPages({
		containerID : "itemContainer",
		perPage : 10,
		url : rfPath + '/archives_schedule/list',
		params: {"orderNum":param},
		fnRender : function(data){
			var rtnStr = "";
			rtnStr += '<td width="50%">'
				+ data.orderNum
				+ '</td>'
				+ '<td width="30%">'
				+ data.uploadTime
				+ '</td>'
				+ '<td width="20%" >'
				+ (data.copyStatus == 1 ? '可来馆取件' : '尚未办理完毕')
				+ '</td>';
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
	var bordernum = $("#bordernum").val();
	if(bordernum == null || bordernum == ""){
		parent.$.messager.alert('提示', "请输入调卷单编号！");
		refreshCaptcha();
		$("#bordernum").focus();
		return false;
	}
	buildPage(bordernum);
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