<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>在线答题试卷信息展示</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/exam/onlineexam_display.js"></script>
</head>
<body>
	<div id="p" class="easyui-panel" title="${objPo.epName }" style="width: 100%; height: 100%; padding: 10px;"
		data-options="fit:true,border:false">
		<form id="formName_obj" action="<c:url value="/onlineExam/commitExam" />" method="post" 
			onsubmit="return isSelectQuestion(${objPo.epId });">
			<input type="hidden" name="epId" value="${objPo.epId }" />
			<c:forEach items="${objPo.ucaExamQuestionVos }" var="examQuestionVo">
			<table>
				<tr>
					<td><input type="hidden" id="eqNumber_select_${examQuestionVo.eqNumber }" /></td>
				</tr>
				<tr>
					<td>${examQuestionVo.eqNumber }、${examQuestionVo.name }</td>
				</tr>
				<tr>
					<td>
					<c:if test="${examQuestionVo.type != 4}">
						<c:if test="${examQuestionVo.type == 1}">
							<input type="hidden" id="multiple_selection_${examQuestionVo.eqId }_${examQuestionVo.eqNumber }" 
								name="multiple_selection_${examQuestionVo.eqId }_${examQuestionVo.eqNumber }" />
						</c:if>
						<c:if test="${examQuestionVo.type == 2}">
							<input type="hidden" id="single_selection_${examQuestionVo.eqId }_${examQuestionVo.eqNumber }" 
								name="single_selection_${examQuestionVo.eqId }_${examQuestionVo.eqNumber }" />
						</c:if>
						<c:forEach items="${examQuestionVo.optionList }" var="option">
							<c:if test="${examQuestionVo.type == 1}">
								<input type="checkbox" name="multiple_selection_${examQuestionVo.eqNumber }" 
									onclick="javascript:multipleSelect(${examQuestionVo.eqId }, ${examQuestionVo.eqNumber });" />&nbsp;${option }<br />
							</c:if>
							<c:if test="${examQuestionVo.type == 2}">
								<input type="radio" name="single_selection_${examQuestionVo.eqNumber }" 
									onclick="javascript:singleSelect(${examQuestionVo.eqId }, ${examQuestionVo.eqNumber });" />&nbsp;${option }<br />
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${examQuestionVo.type == 4}">
						<textarea rows="2" cols="60" id="wendati_${examQuestionVo.eqId }_${examQuestionVo.eqNumber }" 
								name="wendati_${examQuestionVo.eqId }_${examQuestionVo.eqNumber }" 
								onchange="javascript:setWendati(${examQuestionVo.eqId }, ${examQuestionVo.eqNumber });"></textarea>
					</c:if>
					</td>
				</tr>
			</table>
			</c:forEach>
			<p>&nbsp;</p>
			<input type="submit" class="easyui-linkbutton" value="提交试卷" />
		</form>
	</div>
</body>
</html>
