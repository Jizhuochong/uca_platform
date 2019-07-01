<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<title>User Edit Page</title>
		<script type="text/javascript">
		function validate() {
			if ($("#userAccount").val()=="")
		    {
		        alert("请输入账户！");
		        return false;
		    }
			if ($("#userPassword").val()=="")
		    {
		        alert("请输入密码！");
		        return false;
		    }
		     return true;
		}
		</script>
	<body>
		<%-- <div class="hr ow mt1">
	    	<div class="fl">CAPINFO_DEMO SYSTEM</div>
		</div>
		<div class="mn mt1">
			<h2 class="te2">
		        <a href="<c:url value="/" />">首页</a> &gt;
		        <a href="<c:url value="/user/listUsers" />">用户管理</a> &gt;
				用户编辑
		    	<span class="ico01">您好，欢迎<sec:authentication property="name"/>来到CAPINFO_DEMO SYSTEM！
				<a href="<c:url value="/j_spring_security_logout"/>" target="_self">[安全退出]</a>
				</span>
		    </h2>
		</div> --%>
		<div class="mn mt1">
		    <div class="ct">
				<form action="<c:url value="/user/save" />" method="post" onsubmit="return validate();">
				<input type="hidden" name="userId" id="userId" value="${user.userId }"/>
				<div class="sh">
					<div class="mt3">
						<span>登录账户：</span><input type="text" name="userAccount" id="userAccount" onchange="javascript:checkAccount();" value="${user.userAccount }" size="60"/>
					</div>
					<div class="mt3">
						<span>登录密码：</span><input type="password" name="userPassword" id="userPassword" value="" size="60"/>
					</div>
					<div class="mt3">
						<span>用户名称：</span><input type="text" name="userName" id="userName" value="${user.userName }" size="60"/>
					</div>
					<div class="mt3">
						<span>用户状态：</span><input type="radio" name="userStatus" id="userStatus" value="1" <c:if test="${user.userStatus eq 1}">checked</c:if>/>有效&nbsp;&nbsp;&nbsp;
						<input type="radio" name="userStatus" id="userStatus" value="0" <c:if test="${user.userStatus eq 0}">checked</c:if>/>无效
					</div>
					<div class="mt3">
						<span>角色分配：</span>
						<c:forEach items="${roleList }" var="role" varStatus="status">
							<input type="checkbox" name="roleIds" value="${role.roleId }" <c:forEach items="${user.umRoles }" var="uRole"><c:if test="${uRole.roleId eq role.roleId }">checked</c:if></c:forEach>/><c:out value="${role.roleName }"></c:out>
						</c:forEach>
					</div>
					<div class="mt3">
						<input type="submit" class="bn3" value="保存">&nbsp;&nbsp;
						<input type="reset" class="bn3" value="重置">&nbsp;&nbsp;
						<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);">
					</div>
				</div>
				</form>
			</div>
		</div>
		<script type="text/javascript">
		function checkAccount() {
			var uid = $("#userId").val(), uaccount = $("#userAccount").val();
			if(uid == 0) {// 新增的用户
				$.ajax({
				    url: '${ctx}/user/checkAccount',
				    type: 'POST',
				    dataType: 'json',
				    data: {"uaccount":uaccount},
				    success: function(data){
				    	if(data.success) {
				    		alert(data.msg);
				    		$("#userAccount").val("");
				    		$("#userAccount").focus();
				    		return;
				    	}
				    }
				});
			}
		}
		</script>
	</body>
</html>