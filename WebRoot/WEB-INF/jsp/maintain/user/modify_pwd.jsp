<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
    <title>修改密码</title>
</head>
<body>
    <div class="easyui-panel" data-options="fit:true,border:false">
        <div style="padding:100px 40px 100px 40px">
        <form id="formid" action='<c:url value="/securityU/modifyPwd" />' method="post">
        	<table cellpadding="5">
                <tr>
                    <td>输入旧密码：</td>
                    <td><input class="textbox" type="password" name="oldPwd"></input></td>
                </tr>
                <tr>
                    <td>输入新密码：</td>
                    <td><input class="textbox" type="password" name="newPwd"></input></td>
                </tr>
                <tr>
                    <td>确认新密码：</td>
                    <td><input class="textbox" type="password" name="newPwdConfirm"></input></td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
	                    <c:if test="${requestScope.message != null}">
						<span><font color="red">${requestScope.message }</font></span>
						</c:if>
						<c:if test="${requestScope.message == null}">
						<span><font color="red">&nbsp;</font></span>
						</c:if>
					</td>
                </tr>
                <tr>
                    <td colspan="2">
	                    <div style="text-align:center;padding:5px">
	                    	<input type="submit" class="bn3" value="提交">&nbsp;&nbsp;
							<input type="reset" class="bn4" value="重置">&nbsp;&nbsp;
							<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);">
	        			</div>
                    </td>
                </tr>
            </table>
		</form>
        </div>
    </div>
    <style scoped="scoped">
        .textbox{
            height:20px;
            margin:10px;
            padding:0 2px;
            box-sizing:content-box;
        }
    </style>
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
				<form action='<c:url value="/securityU/modifyPwd" />' method="post">
				<div class="sh">
					<div class="mt3">
						<span>输入旧密码：</span><input type="password" size="24" name="oldPwd" value="" class="it2">
					</div>
					<div class="mt3">
						<span>输入新密码：</span><input type="password" size="24" name="newPwd" value="" class="it2">
					</div>
					<div class="mt3">
						<span>确认新密码：</span><input type="password" size="24" name="newPwdConfirm" value="" class="it2">
					</div>
					<c:if test="${requestScope.message != null}">
						<div class="mt3">
							<span><font color="red">${requestScope.message }</font></span>
						</div>
					</c:if>
					<div class="mt3">
						<input type="submit" class="bn3" value="提交">&nbsp;&nbsp;
						<input type="reset" class="bn4" value="重置">&nbsp;&nbsp;
						<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);">
					</div>
				</div>
				</form>
			</div>
		</div>
	</body>
</html>
 --%>