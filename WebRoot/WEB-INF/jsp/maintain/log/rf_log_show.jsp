<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
	<body>
		<div class="mn mt1">
		    <div class="ct">
				<div class="sh">
					<div class="mt3">
						<span>用户账户：</span><input type="text" value="${log.actionUserName }" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>用户&nbsp;&nbsp;ID：</span><input type="text" value="${log.actionUserId }" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>访问时间：</span><input type="text" value="<fmt:formatDate value="${log.actionTime}" pattern ="yyyy-MM-dd HH:mm:ss" />" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>访问耗时：</span><input type="text" value="${log.actionLogSpentMilliseconds }（毫秒）" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>访问&nbsp;&nbsp;IP：</span><input type="text" value="${log.actionUserIp }" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>访问模块：</span><input type="text" value="${log.actionClass }" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>执行方法：</span><input type="text" value="${log.actionMethod }" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>执行参数：</span><input type="text" value="${log.actionParameters }" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>执行结果：</span><input type="text" value="${log.actionResult }" size="60" readonly="true"/>
					</div>
					<div class="mt3">
						<span>结果描述：</span><textarea rows="5" cols="60">${log.actionResultDescription }</textarea>
					</div>
					<div class="mt3">
						<span>日志描述：</span><textarea rows="5" cols="60">${log.actionLogDescription }"</textarea>
					</div>
					<div class="mt3">
						<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);">&nbsp;&nbsp;
					</div>
				</div>
			</div>
		</div>
	</body>
</html>