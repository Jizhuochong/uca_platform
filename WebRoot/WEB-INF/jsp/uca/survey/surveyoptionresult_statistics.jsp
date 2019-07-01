<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>有选项方式问卷调查结果统计</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
</head>
<body>
	<div id="p" class="easyui-panel" title="有选项方式问卷调查结果统计" style="width: 100%; height: 100%; padding: 10px;"
		data-options="fit:true,border:false">
		<c:if test="${ucaSurvey == null}">
			<font color="red" size="16px"><b>问卷调查不存在</b></font>
		</c:if>
		<c:if test="${ucaSurvey != null}">
		<table>
			<tr>
               	<td width="120px" align="right">问题：</td>
               	<td>${ucaSurvey.title }</td>
           	</tr>
           	<c:forEach items="${surveyOptionList }" var="surveyOption">
           	<tr>
               	<td width="120px" align="right">选项：</td>
               	<td>${surveyOption.options }&nbsp;&nbsp;|&nbsp;&nbsp;选择总数：${surveyOption.count }</td>
           	</tr>
           	</c:forEach>
		</table>
		</c:if>
	</div>
</body>
</html>
