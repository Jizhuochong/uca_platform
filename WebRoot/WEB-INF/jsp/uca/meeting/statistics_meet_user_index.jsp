<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>参会人员统计列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/meeting/statistics_meet_user_index.js"></script>
</head>
<body>
<!-- 搜索 -->
<div id="select_bar">
     <div style="padding: 5px;">
        <form id="fmsearch" method="post" novalidate>
			<span>
				<label>预约时间:</label>
				<input id="bgTime" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
           		<input id="endTime" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
			</span>
			<span>
			  	<input type="button" name="select" onclick="javascript:toSearch();" value="查询" id="select" >
			  	<input type="reset" name="reset" value="重置" id="reset" >
			</span>&nbsp;&nbsp;&nbsp;&nbsp;
		</form>
	</div>
	<div style="padding: 5px;">
		<span><strong><div id="meetUserCount"></div></strong></span>
	</div>
</div>
<table id="list_data" cellspacing="0" cellpadding="0">
</table>
</body>
</html>
