<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
	<script src="${ctx}/resources/js/maintain/role/role_list.js" type="text/javascript"></script>
	</head>
	<body>
		<div>
		    <div class="ct">
		    	<div class="sh">
		    		<form action="<c:url value="/role/listRoles" />" method="post" id="queryForm">
			    	<table>
			    		<tr>
			    			<td>角色名称：</td>
			    			<td><input name="roleName" id="roleName" value="${roleName }"/>&nbsp;&nbsp;</td>
			    			<td>角色编码：</td>
			    			<td><input name="roleCode" id="roleCode" value="${roleCode }"/>&nbsp;&nbsp;</td>
			    			<td>
			    				<input type="button" class="bn3" value="查询" onclick="queryTable()"/>&nbsp;&nbsp;
			    				<input type="button" class="bn3" value="添加" onclick="toAdd()"/>&nbsp;&nbsp;
			    				<!-- <input type="button" class="bn3" value="删除" onclick="batchDel()"/> -->
			    			</td>
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
							<th><input type="checkbox" class="check_all"/></th>
							<th>角色名称</th>
							<th>角色编码</th>
							<th>角色状态</th>
							<th>角色描述</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
		    </div>
	</body>
</html>