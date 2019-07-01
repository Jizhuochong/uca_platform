<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
	<script type="text/javascript">
		function toAdd(){
			$("#queryForm").attr("action","<c:url value="/role/toAdd" />");
			$("#queryForm").submit();
		}
	</script>
	<body>
		<%-- <div class="hr ow mt1">
	    	<div class="fl">CAPINFO_DEMO SYSTEM</div>
		</div>
		<div class="mn mt1">
			<h2 class="te2">
		        <a href="<c:url value="/" />">首页</a> &gt;
		        <a href="<c:url value="/role/listRoles" />">角色管理</a> &gt;
				分配权限
		    	<span class="ico01">您好，欢迎<sec:authentication property="name"/>来到CAPINFO_DEMO SYSTEM！
				<a href="<c:url value="/j_spring_security_logout"/>" target="_self">[安全退出]</a>
				</span>
		    </h2>
		</div> --%>
		<div class="mn mt1">
		    <div class="ct">
		    	<div class="sh">
		    		<form action="<c:url value="/role/listRoles" />" method="post" id="queryForm">
			    	<table>
			    		<tr>
			    			<td>角色名称：</td>
			    			<td><input name="roleName" value="${roleName }"/></td>
			    			<td>
			    				<input type="submit" class="bn3" value="查询"/>&nbsp;&nbsp;
			    				<input type="button" class="bn3" value="添加" onclick="toAdd()"/>
			    			</td>
			    		</tr>
			    	</table>
			    	</form>
			    </div>
		    </div>
		    
		    <div class="cx">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab1">
			    <tr>
					<th width="150">角色名称</th>
					<th width="50">角色状态</th>
					<th width="150">角色描述</th>
					<th width="100">操作</th>
				</tr>
				<c:forEach items="${list }" var="role" varStatus="status">
				<tr align="center">
					<td>
						${role.roleName }
					</td>
					<td>
						<c:if test="${role.roleStatus eq 1}">有效</c:if>
						<c:if test="${role.roleStatus eq 0}"><font color="red">无效</font></c:if>
					</td>
					<td>
						<textarea rows="2" cols="35" readonly="true">${role.roleRemark }</textarea>
					</td>
					<td>
						<a href="<c:url value="/role/toAssignPer/${role.roleId }" />">分配权限</a>&nbsp;&nbsp;
						<a href="<c:url value="/role/update/${role.roleId }" />">修改</a>&nbsp;&nbsp;
						<a href="<c:url value="/role/delete/${role.roleId }" />">删除</a>
					</td>
				</tr>
				</c:forEach>
		    </table>
		    </div>
		</div>
	</body>
</html>
