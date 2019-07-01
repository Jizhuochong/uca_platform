<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<%@ include file="/WEB-INF/jsp/common/pagination_pic.jsp"%>
<title>通知退档查询</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_she_back_index.js"></script>
</head>
<body>
<%-- <input type="hidden" value="${dId}"  name="dId" id="dId"/> --%>
<!-- 搜索 -->
<%-- <div id="select_bar" style="height:25px;padding-top:5px">
     <center>
	        <form id="fmsearch" method="post" novalidate>
	            <span>
					<label>调卷单编号:</label>
					<input name="bordernum" id="bordernum" class="easyui-validatebox">
				</span>
				<span>
					<label>验证码:</label>
					<input name="bcode" id="bcode" class="easyui-validatebox">
					<a href="javascript:refreshCaptcha()">
						<img id="captchaImg" src="<c:url value="/jcaptcha.jpg"/>" />
					</a>
				</span>
				<span>
				  	<input type="button" name="select" onclick="javascript:toSearch();" value="查询" id="select" >
				  	<input type="reset" name="reset" value="重置" id="reset" >
				</span>
			</form>
	</center>
</div> --%>
<br>
<br>
<div id="content" class="defaults">
	<table width="680" class="fore-system-datatab">
		<thead>
			<tr>
				<th width="50%" class="head">调卷单编号</th>
				<th width="30%" class="head">建设单位</th>
				<th width="20%" class="head">通知时间</th>
			</tr>
		</thead>
		<tbody id="itemContainer">
		</tbody>
	</table>
</div>
<div class="pc_page">
	<div class="holder">
		跳转到<input type="text" id="topage" value="1" class="pc_inp" />
		<input type="button" id="jump" class="pc_btn"/>
	</div>
</div>
</body>
</html>
