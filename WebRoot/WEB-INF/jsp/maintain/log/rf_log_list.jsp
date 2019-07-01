<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
	<script src="${ctx}/resources/js/maintain/log/rf_log_list.js" type="text/javascript"></script>
	<script src="${ctx}/resources/js/DatePicker/WdatePicker.js" type="text/javascript"></script>
	</head>
	<body>
		<div>
		    <div class="ct">
		    	<div class="sh">
		    		<form action="<c:url value="/rflog/listLogs" />" method="post" id="queryForm">
			    	<table>
			    		<tr>
			    			<td>用户帐户：</td>
			    			<td><input name="actionUserName" id="actionUserName" value="${actionUserName}"/>&nbsp;&nbsp;</td>
			    			<td>访问IP：</td>
			    			<td><input name="actionUserIp" id="actionUserIp" value="${actionUserIp}"/>&nbsp;&nbsp;</td>
			    			<td>
			    				<input type="button" class="bn3" value="查询" onclick="queryTable()"/>&nbsp;&nbsp;
			    			</td>
			    		</tr>
			    		<tr>
			    		    <td>访问时间：</td>
			    			<td><input name="actionTime" id="actionTime" value="${actionTime }" onClick="WdatePicker()"/>&nbsp;&nbsp;</td>
			    		</tr>
			    	</table>
			    	</form>
			    </div>
		    </div>
		    <div class="cx">
		    	<table cellpadding="0" cellspacing="0" border="0" 
					id="queryList" class="display">
					<thead>
						<tr>
							<th>用户账户</th>
							<th>访问IP</th>
							<th>访问时间</th>
							<th>执行结果</th>
							<th>用户行为</th>
							<th>耗时（毫秒）</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
		    </div>
		</div>
	</body>
</html>