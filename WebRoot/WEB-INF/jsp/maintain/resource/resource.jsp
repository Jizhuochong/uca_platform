<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
	<title>资源详情</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/demo.css">
    <script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
	<style type="text/css">
		.resc {padding: 10px; padding-left: 30px;}
	</style>
	</head>
	<body>
	<div class="easyui-panel" title="查看资源" data-options="border:false,fit:true,closable:true,collapsible:true">
   		<p class="resc">资源名称：${resource.resourceName }</p>
      	<p class="resc">父级资源：${resource.resourceParentName }</p>
      	<p class="resc">资源类型：<c:if test="${resource.resourceType eq 1 }">URL</c:if><c:if test="${resource.resourceType eq 0 }">NO URL</c:if></p>
      	<p class="resc">资源等级：${resource.resourceLevel}级</p>  
      	<p class="resc">资源URL：${resource.resourceUrl }</p>  
      	<p class="resc">URL描述：${resource.resourceUrlDirection }</p>  
      	<p class="resc">资源排序：${resource.resourceSort }</p>  
      	<p class="resc">资源状态：<c:if test="${resource.resourceStatus eq 1}">有效</c:if><c:if test="${resource.resourceStatus eq 0}"><font color="red">无效</font></c:if></p>  
      	<p class="resc">资源显示：<c:if test="${resource.resourceShowflag eq 1}">显示</c:if><c:if test="${resource.resourceShowflag eq 0}"><font color="red">不显示</font></c:if></p>  
    	<p class="resc">角色列表：<c:forEach items="${resource.umRoles }" var="uRole" varStatus="urstatus"><c:out value="${uRole.roleName }"></c:out>&nbsp;</c:forEach></p>
    </div>
	</body>
</html>