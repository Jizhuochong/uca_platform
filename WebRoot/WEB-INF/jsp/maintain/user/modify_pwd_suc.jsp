<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
	<title>修改密码成功</title>
</head>
<body>
	<div align="center">
		<div style="margin: 150px 0;"></div>
		<div class="easyui-panel" title="修改密码成功" style="width: 450px">
			<div style="padding: 40px 20px 40px 20px">
				<c:if test="${requestScope.message != null}">
					<span><font color="red">${requestScope.message }</font></span>
					<!-- <p>&nbsp;</p>
					<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);"> -->
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>


<!-- 旧版本 -->
<%-- 
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
	</head>
	<body>
		<div class="mn mt1">
		    <div class="ct">
					<c:if test="${requestScope.message != null}">
						<div class="mt3">
							<span><font color="red">${requestScope.message }</font></span>
							<input type="button" class="bn4" value="返回" onclick="javascript:history.go(-1);">
						</div>
					</c:if>
				</div>
				</form>
			</div>
		</div>
	</body>
</html>
 --%>