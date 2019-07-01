<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<script type="text/javascript">
		function validate() {
			if ($("#roleName").val()=="")
		    {
		        alert("请输入角色名称！");
		        return false;
		    }
			if ($("#roleCode").val()=="")
		    {
		        alert("请输入角色编码！");
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
		        <a href="<c:url value="/role/listRoles" />">角色管理</a> &gt;
				角色编辑
		    	<span class="ico01">您好，欢迎<sec:authentication property="name"/>来到CAPINFO_DEMO SYSTEM！
				<a href="<c:url value="/j_spring_security_logout"/>" target="_self">[安全退出]</a>
				</span>
		    </h2>
		</div> --%>
		<div class="mn mt1">
		    <div class="ct">
				<form action="<c:url value="/role/save" />" method="post" onsubmit="return validate();">
				<input type="hidden" name="roleId" id="roleId" value="${role.roleId }"/>
				<div class="sh">
					<div class="mt3">
						<span>角色名称：</span><input type="text" name="roleName" id="roleName" value="${role.roleName }" size="60"/>
					</div>
					<div class="mt3">
						<span>角色编码：</span><input type="text" name="roleCode" id="roleCode" onchange="javascript:checkRCode();" value="${role.roleCode }" size="60"/>
					</div>
					<div class="mt3">
						<span>角色状态：</span><input type="radio" name="roleStatus" id="roleStatus" value="1" <c:if test="${role.roleStatus eq 1}">checked</c:if>/>有效&nbsp;&nbsp;&nbsp;
						<input type="radio" name="roleStatus" id="roleStatus" value="0" <c:if test="${role.roleStatus eq 0}">checked</c:if>/>无效
					</div>
					<div class="mt3">
						<span>角色描述：</span><textarea name="roleRemark" id="roleRemark" rows="3" cols="60">${role.roleRemark}</textarea>
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
		function checkRCode() {
			var rid = $("#roleId").val(), rcode = $("#roleCode").val();
			if(rid == 0) {// 新增的角色
				$.ajax({
				    url: '${ctx}/role/checkRCode',
				    type: 'POST',
				    dataType: 'json',
				    data: {"rcode":rcode},
				    success: function(data){
				    	if(data.success) {
				    		alert(data.msg);
				    		$("#roleCode").val("");
				    		$("#roleCode").focus();
				    		return;
				    	}
				    }
				});
			}
		}
		</script>
	</body>
</html>