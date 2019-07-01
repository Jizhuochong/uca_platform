<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<title>Permission Edit Page</title>
		<script type="text/javascript">
		function validate() {
			var permissionName = $("#permissionName").val();
		    if (permissionName=="")
		    {
		        alert("请输入权限名称！");
		        return false;
		    }
		     return true;
		}
		</script>
	</head>
	<body>
		<%-- <div class="hr ow mt1">
	    	<div class="fl">CAPINFO_DEMO SYSTEM</div>
		</div>
		<div class="mn mt1">
			<h2 class="te2">
		        <a href="<c:url value="/" />">首页</a> &gt;
		        <a href="<c:url value="/permission/listPermissions" />">权限管理</a> &gt;
		        权限编辑
		    	<span class="ico01">您好，欢迎<sec:authentication property="name"/>来到CAPINFO_DEMO SYSTEM！
				<a href="<c:url value="/j_spring_security_logout"/>" target="_self">[安全退出]</a>
				</span>
		    </h2>
		</div> --%>
		<div class="mn mt1">
		    <div class="ct">
				<form action="<c:url value="/permission/save" />" method="post" onsubmit="return validate();">
				<input type="hidden" name="permissionId" id="permissionId" value="${permission.permissionId }"/>
				<div class="sh">
					<div class="mt3">
						<span>权限名称：</span><input type="text" name="permissionName" id="permissionName" value="${permission.permissionName }" size="60"/>
					</div>
					<div class="mt3">
						<span>备&nbsp;&nbsp;&nbsp;&nbsp;注：</span><textarea name="permissionRemark" id="permissionRemark" rows="5" cols="50">${permission.permissionRemark }</textarea>
					</div>
					<div class="mt3">
						<span>资源分配：</span>
						<c:forEach items="${resourceList }" var="resource" varStatus="status">
							<input type="checkbox" name="resourceIds" value="${resource.resourceId }" <c:forEach items="${permission.umResources }" var="uResource"><c:if test="${uResource.resourceId eq resource.resourceId }">checked</c:if></c:forEach>/><c:out value="${resource.resourceName }"></c:out>
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
	</body>
</html>