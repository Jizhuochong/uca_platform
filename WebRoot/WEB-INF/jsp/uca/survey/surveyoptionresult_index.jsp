<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>有选项方式问卷调查提交确认</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/survey/survey_option_result.js"></script>
</head>
<body>
	<div id="p" class="easyui-panel" title="有选项方式问卷调查提交确认" style="width: 100%; height: 100%; padding: 10px;"
		data-options="fit:true,border:false">
		<form id="formName_obj">
			<input type="hidden" id="surveyId_jsid" value="${ucaSurvey.id }" />
			<table>
				<tr>
					<td><input type="hidden" id="surveyOptionIds_jsid" /></td>
				</tr>
				<tr>
					<td>问题：${ucaSurvey.title }</td>
				</tr>
				<c:forEach items="${surveyOptionList }" var="surveyOption">
				<tr>
					<td>
						<input type="checkbox" name="multiple_selection" value="${surveyOption.id }"
							onclick="javascript:multipleSelect();" />&nbsp;${surveyOption.options }<br />
					</td>
				</tr>
				</c:forEach>
			</table>
			<p>&nbsp;</p>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:commit();">提交问卷调查</a>
		</form>
	</div>
</body>
</html>
