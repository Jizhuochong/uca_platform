<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>声像档案预约查询列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/apply/apply_query_index.js"></script>
</head>
<body>
<input type="hidden" value="${type}"  name="dType" id="dType"/>
<!-- 搜索 -->
<div id="select_bar" style="height:25px;padding-top:5px">
     <center>
     	<%-- <div style="margin: 0 auto;padding: 2px;">
            <input type="button" onclick="javascript:getObjByParam('',${type});" value="上传档案"、>
        </div> --%>
        <!-- <form id="fmsearch" method="post" novalidate>
            <span>
				<label>标题:</label>
				<input name="bname" id="bname" class="easyui-validatebox">
			</span>
			<span>
				<label>内容:</label>
				<input name="bcontent" id="bcontent" class="easyui-validatebox" >
			</span>
			<span>
			  	<input type="button" name="select" onclick="javascript:toSearch();" value="查询" id="select" >
			  	<input type="reset" name="reset" value="重置" id="reset" >
			</span>
		</form> -->
	</center>
</div>
<table id="list_data" cellspacing="0" cellpadding="0">
</table>
<form  method="post" id="queryForm"></form>
</body>
</html>
