<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>无选项方式问卷调查提交确认</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/survey/survey_result.js"></script>
</head>
<body>
	<div id="p" class="easyui-panel" title="无选项方式问卷调查提交确认" style="width: 100%; height: 100%; padding: 10px;"
		data-options="fit:true,border:false">
		<c:if test="${ucaSurvey == null}">
			<font color="red" size="16px"><b>问卷调查不存在</b></font>
		</c:if>
		<c:if test="${ucaSurvey != null}">
		<form id="formName_obj">
			<input type="hidden" id="surveyId_jsid" value="${ucaSurvey.id }" />
			<table>
				<tr>
                	<td width="120px" align="right">问题：</td>
                	<td>${ucaSurvey.title }</td>
            	</tr>
            	<tr>
                	<td width="120px" align="right">答：</td>
                	<td>
                	<textarea rows="3" cols="60" id="result_jsid"></textarea>
                	</td>
            	</tr>
            	<tr>
                	<td width="120px" align="right">&nbsp;</td>
                	<td>&nbsp;</td>
            	</tr>
            	<tr>
                	<td width="120px" align="right">&nbsp;</td>
                	<td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:commit();">提交问卷调查</a></td>
            	</tr>
			</table>
		</form>
		</c:if>
	</div>
</body>
</html>
