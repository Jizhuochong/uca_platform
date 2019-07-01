<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
	<script src="${ctx}/resources/js/maintain/binary/binary_list.js" type="text/javascript"></script>
	</head>
	<body>
		<div>
		    <div class="ct">
		    	<div class="sh">
		    		<form action="<c:url value="/binary/listBinarys" />" method="post" id="queryForm">
			    	<a href="<c:url value="/fileupload/toUpload" />">上传文件</a>&nbsp;|
			    	<a href="<c:url value="/fileupload/toUploadFile" />">单个上传文件</a>&nbsp;|
			    	<a href="<c:url value="/fileupload/toUploadMutilFile" />">批量上传文件</a>
			    	</form>
			    </div>
		    </div>
		    <div class="cx">
		    	<table cellpadding="0" cellspacing="0" border="0" 
					id="queryList" class="display">
					<thead>
						<tr>
							<th><input type="checkbox"/></th>
							<th>名称</th>
							<th>描述</th>
							<th>类型</th>
							<th>上传时间</th>
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