<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
<%@ taglib prefix="uca" uri="http://www.uca.com/tags"%>
	<script src="${ctx}/resources/js/maintain/user/user_list.js" type="text/javascript"></script>
	</head>
	<body>
		<div>
		    <div class="ct">
		    	<div class="sh">
		    		<form action="<c:url value="/user/listUsers" />" method="post" id="queryForm">
		    		<input type="text" style="display:none"/>
			    	<table>
			    		<tr>
			    			<td>用户姓名：</td>
			    			<td><input id="userName" name="userName" value="${userName }"/>&nbsp;&nbsp;</td>
			    			<td>
			    				<c:if test="${uca:isHaveResource('/user/listUsers',sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.userId)}">
			    					<input type="button" class="bn3" value="查询" onclick="queryTable()"/>&nbsp;&nbsp;
			    				</c:if>
			    				<uca:hres url="/user/toAdd" userid="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.userId }">
								      <input type="button" class="bn3" value="添加" onclick="toAdd()"/>&nbsp;&nbsp;
								</uca:hres>
								<c:if test="${uca:isHaveResource('/user/batchDelete',sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.userId)}">
									<input type="button" class="bn3" value="删除" onclick="batchDel()"/>&nbsp;&nbsp;
								</c:if>
			    				<a href="<c:url value="/userValidator/validator" />">添加验证用户</a>
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
							<th>用户姓名</th>
							<th>登录账户</th>
							<th>用户状态</th>
							<th>注册时间</th>
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