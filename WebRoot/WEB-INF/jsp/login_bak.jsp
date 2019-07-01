<!-- jquery-easyui: easyui-panel版登录界面 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
    <title>首信电子档案管理系统</title>
    <link href="${ctx}/resources/css/validform/style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${ctx}/resources/css/validform/valid-style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}/resources/js/validform/Validform_v5.3.2_min.js"></script>
	<script type="text/javascript">
	<!--
    if (window != window.top){
        window.top.location.reload("${ctx}/toLogin");
    }
	//-->
	</script>
</head>
<body>
    <div class="easyui-panel" title="档案管理系统登录" data-options="border:false,fit:true" style="padding:100px;">
       	<form class="rfValidForm" action="j_login" method="post">
            <table style="table-layout:fixed;width:420px;">
                <tr>
                    <td style="width:120px;text-align: right;">用&nbsp;&nbsp;户：</td>
                    <td style="width:115px;"><input type='text' size="24" name='j_username' id="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" class="it2" datatype="*" sucmsg="已经输入用户名！" nullmsg="请输入用户名！" errormsg="请输入用户名！"></td>
                    <td><div class="Validform_checktip"></div></td>
                </tr>
                <tr>
                    <td style="width:120px;text-align: right;">密&nbsp;&nbsp;码：</td>
                    <td style="width:115px;"><input type="password" size="24" name="j_password" id="j_password" value="" class="it2" datatype="*" sucmsg="已经输入密码！" nullmsg="请输入密码！" errormsg="请输入密码！"></td>
                    <td><div class="Validform_checktip"></div></td>
                </tr>
                <tr>
                    <td style="width:120px;text-align: right;">验证码：</td>
                    <td style="width:115px;"><input type='text' name='j_captcha' id="j_captcha" size='5' class="it2" datatype="*" sucmsg="已经输入验证码！" nullmsg="请输入验证码！" errormsg="请输入验证码！"/></td>
                    <td><div class="Validform_checktip"></div></td>
                </tr>
                <tr>
                    <td style="width:120px;text-align: right;"></td>
                    <td colspan="2"><img id="captchaImg" src="<c:url value="/jcaptcha.jpg"/>" />&nbsp;&nbsp;<a href="javascript:refreshCaptcha()">看不清楚换一张</a></td>
                </tr>
                <tr>
                	<td style="width:120px;text-align: right;"></td>
                	<td colspan="2">
		                <font color="red">
							<span id="errorMsg">
							<c:if test="${param.error == 'true'}">
								${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
								<%-- <spring:message code="loginPage.authenticationFailure" /> --%>
							</c:if>
							<c:if test="${param.error == '1'}">
								<spring:message code="loginPage.verifyCodeFailure" />
							</c:if>
							</span>
						</font>
					</td>
				</tr>
                <tr>
                    <td style="width:120px;text-align: right;"></td>
                    <td colspan="2" style="padding:10px 0 18px 0;">
                        <input type="submit" class="bn3" value="提交">
						<input type="reset" class="bn4" value="重置">
                    </td>
                </tr>
            </table>
        </form>
        <script type="text/javascript">
		$(function(){
			$(".rfValidForm").Validform({
				tiptype:2
			});
		});
		function refreshCaptcha() {
			$('#captchaImg').hide().attr(
				'src',
				'<c:url value="/jcaptcha.jpg"/>' + '?' + Math
				.floor(Math.random() * 100)).fadeIn();
		}
		</script>
        <div>&nbsp;</div>
        <div align="center">Powered by 产品研发中心&copy;首都信息发展股份有限公司</div>
    </div>
</body>
</html>


<!-- css: 普通版登录界面 -->
<%-- 
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<title>Login_Page</title>
   <script type="text/javascript">
	<!--
    if (window != window.top){
        window.top.location.reload("${ctx}/toLogin");
    }
	//-->
	</script>
		<link href="${ctx}/resources/css/validform/style.css" rel="stylesheet" type="text/css" media="all" />
		<link href="${ctx}/resources/css/validform/valid-style.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${ctx}/resources/js/validform/Validform_v5.3.2_min.js"></script>
	</head>
	<body>
		<div class="hr ow mt1">
	    	<div class="fl">档案管理系统登录</div>
		</div>
		<div class="mn0 mt1">
			<div class="ct">
		        <form class="rfValidForm" action="j_login" method="post">
		            <table width="100%" style="table-layout:fixed;">
		                <tr>
		                    <td style="width:70px;">用&nbsp;&nbsp;户：</td>
		                    <td style="width:105px;"><input type='text' size="24" name='j_username' id="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" class="it2" datatype="*" sucmsg="已经输入用户名！" nullmsg="请输入用户名！" errormsg="请输入用户名！"></td>
		                    <td><div class="Validform_checktip"></div></td>
		                </tr>
		                <tr>
		                    <td style="width:70px;">密&nbsp;&nbsp;码：</td>
		                    <td style="width:105px;"><input type="password" size="24" name="j_password" id="j_password" value="" class="it2" datatype="*" sucmsg="已经输入密码！" nullmsg="请输入密码！" errormsg="请输入密码！"></td>
		                    <td><div class="Validform_checktip"></div></td>
		                </tr>
		                <tr>
		                    <td style="width:70px;">验证码：</td>
		                    <td style="width:105px;"><input type='text' name='j_captcha' id="j_captcha" size='5' class="it2" datatype="*" sucmsg="已经输入验证码！" nullmsg="请输入验证码！" errormsg="请输入验证码！"/></td>
		                    <td><div class="Validform_checktip"></div></td>
		                </tr>
		                <tr>
		                    <td style="width:70px;"></td>
		                    <td colspan="2"><a href="javascript:refreshCaptcha()">看不清楚换一张</a></td>
		                </tr>
		                <tr>
		                    <td style="width:70px;"></td>
		                    <td colspan="2"><img id="captchaImg" src="<c:url value="/jcaptcha.jpg"/>" /></td>
		                </tr>
		                <tr>
		                	<td style="width:70px;"></td>
		                	<td colspan="2">
				                <font color="red">
									<span id="errorMsg">
									<c:if test="${param.error == 'true'}">
										${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
									</c:if>
									<c:if test="${param.error == '1'}">
										<spring:message code="loginPage.verifyCodeFailure" />
									</c:if>
									</span>
								</font>
							</td>
						</tr>
		                <tr>
		                    <td style="width:70px;"></td>
		                    <td colspan="2" style="padding:10px 0 18px 0;">
		                        <input type="submit" class="bn3" value="提交">
								<input type="reset" class="bn4" value="重置">
		                    </td>
		                </tr>
		            </table>
		        </form>
		    </div>
		</div>

	<script type="text/javascript">
		$(function(){
			$(".rfValidForm").Validform({
				tiptype:2
			});
		});
		function refreshCaptcha() {
			$('#captchaImg').hide().attr(
				'src',
				'<c:url value="/jcaptcha.jpg"/>' + '?' + Math
				.floor(Math.random() * 100)).fadeIn();
		}
	</script>
<%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
 --%>

 
<!-- jquery-easyui: easyui-window版登录界面 -->
 <%-- 
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
    <title>档案管理系统</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/demo.css">
    <link href="${ctx}/resources/css/validform/style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${ctx}/resources/css/validform/valid-style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/validform/Validform_v5.3.2_min.js"></script>
	<script type="text/javascript">
	<!--
    if (window != window.top){
        window.top.location.reload("${ctx}/toLogin");
    }
	//-->
	</script>
</head>
<body>
<div align="center" style="padding-top:10px;">
    <div class="easyui-panel" data-options="border:false" style="position:relative;width:1000px;height:450px;overflow:auto;">
        <div id="w" class="easyui-window" data-options="title:'档案管理系统登录',collapsible:false,minimizable:false,maximizable:false,closable:false,resizable:false,draggable:false,inline:true" style="width:600px;height:330px;padding:10px">
            <form class="rfValidForm" action="j_login" method="post">
	            <table width="100%" style="table-layout:fixed;">
	                <tr>
	                    <td style="width:150px;text-align: right;">用&nbsp;&nbsp;户：</td>
	                    <td style="width:115px;"><input type='text' size="24" name='j_username' id="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" class="it2" datatype="*" sucmsg="已经输入用户名！" nullmsg="请输入用户名！" errormsg="请输入用户名！"></td>
	                    <td><div class="Validform_checktip"></div></td>
	                </tr>
	                <tr>
	                    <td style="width:150px;text-align: right;">密&nbsp;&nbsp;码：</td>
	                    <td style="width:115px;"><input type="password" size="24" name="j_password" id="j_password" value="" class="it2" datatype="*" sucmsg="已经输入密码！" nullmsg="请输入密码！" errormsg="请输入密码！"></td>
	                    <td><div class="Validform_checktip"></div></td>
	                </tr>
	                <tr>
	                    <td style="width:150px;text-align: right;">验证码：</td>
	                    <td style="width:115px;"><input type='text' name='j_captcha' id="j_captcha" size='5' class="it2" datatype="*" sucmsg="已经输入验证码！" nullmsg="请输入验证码！" errormsg="请输入验证码！"/></td>
	                    <td><div class="Validform_checktip"></div></td>
	                </tr>
	                <tr>
	                    <td style="width:150px;text-align: right;"></td>
	                    <td colspan="2"><img id="captchaImg" src="<c:url value="/jcaptcha.jpg"/>" />&nbsp;&nbsp;<a href="javascript:refreshCaptcha()">看不清楚换一张</a></td>
	                </tr>
	                <tr>
	                	<td style="width:150px;text-align: right;"></td>
	                	<td colspan="2">
			                <font color="red">
								<span id="errorMsg">
								<c:if test="${param.error == 'true'}">
									${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
								</c:if>
								<c:if test="${param.error == '1'}">
									<spring:message code="loginPage.verifyCodeFailure" />
								</c:if>
								</span>
							</font>
						</td>
					</tr>
	                <tr>
	                    <td style="width:150px;text-align: right;"></td>
	                    <td colspan="2" style="padding:10px 0 18px 0;">
	                        <input type="submit" class="bn3" value="提交">
							<input type="reset" class="bn4" value="重置">
	                    </td>
	                </tr>
	            </table>
	        </form>
        </div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$(".rfValidForm").Validform({
			tiptype:2
		});
	});
	function refreshCaptcha() {
		$('#captchaImg').hide().attr(
			'src',
			'<c:url value="/jcaptcha.jpg"/>' + '?' + Math
			.floor(Math.random() * 100)).fadeIn();
	}
</script>
<%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
 --%>
 
 