<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
		<title>User Edit Page</title>
		<style type="text/css">  
		.error{  
		    color: red;   
		}  
		</style>
	<body>  
	    <div class="mn mt1">
			<div class="ct">
				<sf:form method="post" modelAttribute="user">
				<sf:hidden path="userId"/>
				<div class="sh">
					<div class="mt3">
						<span>登录账户：</span>
						<sf:input path="userAccount" size="30"/>&nbsp;
						<small>登录帐号必须以字母下划线开头，长度必须在5-20个字符之间，可由字母数字下划线组成</small><br />
						<sf:errors path="userAccount" cssClass="error"/>
					</div>
					<div class="mt3">
						<span>登录密码：</span>
						<sf:password path="userPassword" size="30"/>&nbsp;
						<small>登录密码长度必须在6-20个字符之间</small><br />
						<sf:errors path="userPassword" cssClass="error"/>
					</div>
					<div class="mt3">
						<span>用户名称：</span>
						<sf:input path="userName" size="30"/>&nbsp;
						<small>用户名称长度必须在1-5个字符之间</small><br />
						<sf:errors path="userName" cssClass="error"/>
					</div>
					<div class="mt3">
						<span>用户状态：</span>
						<sf:radiobutton path="userStatus" value="1"/>有效&nbsp;&nbsp;
						<sf:radiobutton path="userStatus" value="0"/>无效
					</div>
					<div class="mt3">
						<span>角色分配：</span>
						<c:forEach items="${roleList }" var="role" varStatus="status">
							<sf:checkbox path="roleIds" value="${role.roleId }" label="${role.roleName }" />
						</c:forEach>
					</div>
					<div class="mt3">
						<input type="submit" class="bn3" value="保存">&nbsp;&nbsp;
						<input type="reset" class="bn3" value="重置">&nbsp;&nbsp;
						<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);">
					</div>
				</div>
				</sf:form>
			</div>
		</div>
	</body>
</html>