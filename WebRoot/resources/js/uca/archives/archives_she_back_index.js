function buildPage(){
	$("div.holder").jPages({
		containerID : "itemContainer",
		perPage : 10,
		url : rfPath + '/archives_schedule/backList',
		fnRender : function(data){
			var rtnStr = "";
			rtnStr += '<td width="50%">'
				+ data.orderNum
				+ '</td>'
				+ '<td width="30%">'
				+ data.devOrg
				+ '</td>'
				+ '<td width="20%" >'
				+ data.updateTime
				+ '</td>';
			return rtnStr;
		}
	});
}
$(document).ready(function(){buildPage()});

/*function toSearch(){
	if(!checkCode()){
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

function checkCode(){
	var bcode = $("#bcode").val();
	if(bcode == null || bcode == ""){
		parent.$.messager.alert('提示', "请输入验证码！");
		$("#bcode").focus();
		return false;
	}
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
	    },
	});
	return flag;
}

function refreshCaptcha() {
	$('#captchaImg').hide().attr(
		'src',
		rfPath + '/jcaptcha.jpg' + '?' + Math
		.floor(Math.random() * 100)).fadeIn();
}*/