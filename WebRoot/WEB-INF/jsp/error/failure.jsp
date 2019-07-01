<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
    <title>错误页面提示</title>
</head>
<body>
    <div align="center" style="padding-top:180px;padding-bottom:220px;">
    <div class="easyui-panel" title="错误页面提示" style="position:relative;width:520px;height:80px;padding:10px;">
       	<h2>
			<font color="red">
				<span id="errorMsg">
					<spring:message code="1001" />
				</span>
			</font>
		</h2>
    </div>
    </div>
	<div>&nbsp;</div>
<%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
