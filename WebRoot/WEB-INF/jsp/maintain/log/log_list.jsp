<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
	<body>
		<div class="mn mt1">
		    <%-- <div class="ct">
		    	<div class="sh">
		    		<form action="<c:url value="/log/listLogs" />" method="post" id="queryForm">
			    	<table>
			    		<tr>
			    		</tr>
			    	</table>
			    	</form>
			    </div>
		    </div> --%>
		    
		    <div class="cx">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab1">
			    <tr>
					<th width="90">用户账户</th>
					<th width="90">访问IP</th>
					<th width="90">访问时间</th>
					<th width="90">执行结果</th>
					<th width="250">用户行为</th>
					<th width="50">耗时（毫秒）</th>
					<th width="50">操作</th>
				</tr>
				<c:forEach items="${list }" var="log" varStatus="status">
				<tr align="center">
					<td>
						${log.actionUserName }
					</td>
					<td>
						${log.actionUserIp }
					</td>
					<td>
						<fmt:formatDate value="${log.actionTime}" pattern ="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						${log.actionResult }
					</td>
					<td>
						<textarea rows="3" cols="50" readonly="true">${log.actionLogDescription }</textarea>
					</td>
					<td>
						${log.actionLogSpentMilliseconds }
					</td>
					<td>
						<a href="<c:url value="/log/show/${log.xh }" />">查看</a>
					</td>
				</tr>
				</c:forEach>
				<tr align="center">
					<td colspan="7">
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
		    </div>
		</div>
		<form method="post" action="" id="pageForm"></form>
		<script type="text/javascript">
		function gotoPage(pageNo){
			$("#pageForm").attr("action",'/log/listLogs?pageNo=' + pageNo);
			$("#pageForm").submit();
		}
		</script>
	</body>
</html>