<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
	<script src="${ctx}/resources/js/maintain/permission/permission_list.js" type="text/javascript"></script>
	</head>
	<body>
		<div>
		    <div class="ct">
		    	<div class="sh">
		    		<form action="<c:url value="/permission/listPermissions" />" method="post" id="queryForm">
			    	<table>
			    		<tr>
			    			<td>权限名称：</td>
			    			<td><input name="permissionName" id="permissionName" value="${permissionName }"/>&nbsp;&nbsp;</td>
			    			<td>
			    				<input type="button" class="bn3" value="查询" onclick="queryTable()"/>&nbsp;&nbsp;
			    				<input type="button" class="bn3" value="添加" onclick="toAdd()"/>&nbsp;&nbsp;
			    				<input type="button" class="bn3" value="删除" onclick="batchDel()"/>
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
							<th>权限名称</th>
							<th>备注</th>
							<th>资源列表</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
		    </div>
		    <%-- <div class="cx">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab1">
			    <tr>
					<th width="100">权限名称</th>
					<th width="300">备&nbsp;&nbsp;&nbsp;&nbsp;注</th>
					<th width="250">资源列表</th>
					<th width="50">操&nbsp;&nbsp;&nbsp;&nbsp;作</th>
				</tr>
				<c:forEach items="${list }" var="permission" varStatus="status">
				<tr align="center">
					<td>
						${permission.permissionName }
					</td>
					<td>
						${permission.permissionRemark }
					</td>
					<td>
						<c:forEach items="${permission.umResources }" var="uResource" varStatus="urstatus">
							<c:out value="${uResource.resourceName }"></c:out>&nbsp;
						</c:forEach>
					</td>
					<td>
						<a href="<c:url value="/permission/update/${permission.permissionId }" />">修改</a>&nbsp;
						<a href="javascript:void(0);" onclick="javascript:doDelete(${permission.permissionId })">删除</a>
					</td>
				</tr>
				</c:forEach>
				<tr align="center">
					<td colspan="4">
						<span>
							<a onclick="javascript:gotoPage(1);"
							href="#">首页</a>
							&nbsp;|&nbsp;<a onclick="javascript:gotoPage(${page.prevPageNo });"
							href="#">上一页</a>
							&nbsp;|&nbsp;<a onclick="javascript:gotoPage(${page.nextPageNo });"
							href="#">下一页</a>
							&nbsp;|&nbsp;<a onclick="javascript:gotoPage(${page.totalPageCount });"
							href="#">尾页</a>
						</span>
					</td>
				</tr>
		    </table>
		    </div> --%>
		</div>
	</body>
</html>