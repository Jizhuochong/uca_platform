<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>城市建设档案网上办公系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<link href="${ctx}/resources/css/validform/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${ctx}/resources/css/validform/valid-style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/resources/js/validform/Validform_v5.3.2_min.js"></script>

<script type="text/javascript">
function onload() {
	var redirectUrl = "<%=request.getParameter("redirectUrl")%>";
	
	$('#redirectUrl_input_id').textbox({
		value : redirectUrl
	});
	
	document.getElementById("auto_login_form_id").submit();
}
</script>
</head>

<body onload="onload()">

<div >
	<!-- 自行设定欲登陆用户的用户名和密码 -->
    <form id="auto_login_form_id" action="j_spring_security_check" method="post" style="visibility:hidden">
    	<input type="text" name="j_username" id="j_username" value="test" datatype="*" />
    	<input type="password" name="j_password" id="j_password" value="test" datatype="*" />
    	<input type="text" name="redirectUrl" id="redirectUrl_input_id" />
    </form>
</div>

</body>
</html>