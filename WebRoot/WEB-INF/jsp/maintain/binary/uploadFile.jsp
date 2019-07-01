<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<script type="text/javascript">
		function validate() {
			var fileItemV = $("#fileItem").val();
		    if (fileItemV=="")
		    {
		        alert("请选择需要上传的附件");
		        return false;
		    }
	        //var i = fileItemV.indexOf(".apk");
	        //if (i == -1) {
	        //    alert("上传文件只能是apk格式");
	        //   return false;
	        //}
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
		        <a href="<c:url value="/binary/listBinarys" />">文件管理</a> &gt;
		        上传文件
		    	<span class="ico01">您好，欢迎<sec:authentication property="name"/>来到CAPINFO_DEMO SYSTEM！
				<a href="<c:url value="/j_spring_security_logout"/>" target="_self">[安全退出]</a>
				</span>
		    </h2>
		</div> --%>
		<div class="mn mt1">
		    <div class="ct">
				<form action='<c:url value="/fileupload/upload" />' method="post" enctype="multipart/form-data" onsubmit="return validate();">
				<div class="sh">
					<div class="mt3">
						<span>文件：</span><input type="file" name="fileItem" id="fileItem" size="60"/>
					</div>
					<div class="mt3">
						<span>描述：</span><textarea name="description" rows="6" cols="70"></textarea>
					</div>
					<div class="mt3">
						<input type="submit" class="bn3" value="上传">&nbsp;&nbsp;
						<input type="reset" class="bn3" value="重置">&nbsp;&nbsp;
						<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);">
					</div>
				</div>
				</form>
			</div>
		</div>
	</body>
</html>