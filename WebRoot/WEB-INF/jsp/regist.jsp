<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<title>用户注册</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<link href="${ctx}/resources/css/validform/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${ctx}/resources/css/validform/valid-style.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.cRed{ color:#ff0000;}
</style>
<script type="text/javascript" src="${ctx}/resources/js/validform/Validform_v5.3.2_min.js"></script>
</head>
<body>
	<div class="tips" align="center">
		<p>欢迎您，亲爱的朋友</p>
		<p>为了您能够正常发布信息，您首先需要注册，带*星号部分为必须填写的项。</p>
	</div>
	<div class="forms pt50 w800">
		<form class="rfValidForm" action="<c:url value="/ucaRegist/regist" />"
			method="post">
			<input name="un" id="un" type="hidden" value="" />
			<table class="registtab">
				<tr>
					<td><label class="w100">账号：<span
							class="cRed">*</span></label></td>
					<td><input type="text" name="userAccount"
						class="input_text w200"
						ajaxurl="<c:url value="/ucaRegist/regValidAccount" />" datatype="un4-20"
						nullmsg="请输入您的用户名！" errormsg="用户名不符合要求，重新输入！" /><span
						class="cRed">(必须含小写字母和数字,字母开头,长度4-20位)</span></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">密 码：<span class="cRed">*</span></label></td>
					<td><input type="password" oncopy="return false" onpaste="return false" name="userPassword"
						class="input_text w200" datatype="pwd8-16" nullmsg="请输入您的密码！"
						errormsg="密码不符合要求，重新输入！" /> <span class="cRed">(密码至少包含字母和数字,长度8-16位)</span></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">确认密码：<span class="cRed">*</span></label></td>
					<td><input type="password" oncopy="return false" onpaste="return false" name="repassword"
						class="input_text w200" recheck="userPassword" datatype="pwd8-16"
						nullmsg="请输入您的确认密码！" errormsg="两次输入的密码不一致！" /></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">手机号码：<span class="cRed">*</span></label></td>
					<td><input type="text" name="phoneNum"
						class="input_text w200" datatype="m"
						nullmsg="请输入您的手机号码！"
						errormsg="手机号码不符合要求，重新输入！" /></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">联系电话：</label></td>
					<td><input type="text" name="telephone"
						class="input_text w200" datatype="/^\\s*$|n8-8" errormsg="固定电话必须是8位数字！" />
					</td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">姓名：<span class="cRed">*</span></label></td>
					<td><input type="text" name="userName"
						class="input_text w200" datatype="zh1-6" nullmsg="请输入您的真实姓名！"
						errormsg="真实姓名不符合要求，重新输入！" /><span class="cRed">(必须为中文,长度1-6位)</span></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">性 别：<span class="cRed">&nbsp;</span></label></td>
					<td><span class="radio"> <input type="radio"
							name="sex" value="1" checked="checked" /> 男
					</span><span class="radio"> <input type="radio" name="sex"
							value="0" /> 女
					</span></td>
					<td align="left">&nbsp;</td>
				</tr>
				<tr>
					<td><label class="w100">电子邮件：<span class="cRed">*</span></label></td>
					<td><input type="text" name="email" class="input_text w200"
						datatype="e" ajaxurl="<c:url value="/ucaRegist/regValidEmail" />"
						nullmsg="请输入您的电子邮件！" errormsg="电子邮件不符合要求，重新输入！" /></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">建设单位名称：<span class="cRed">*</span></label></td>
					<td><input type="text" name="devOrg" class="input_text w200" nullmsg="请输入您的建设单位名称！" 
						datatype="*1-100" errormsg="联系地址最多输入100个字！" /></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">单位地址：</label></td>
					<td><input type="text" name="devOrgAddress" class="input_text w200" 
						datatype="/^\\s*$|/*1-100" errormsg="单位地址最多输入100个字！" /></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">备注：</label></td>
					<td align="left"><textarea name="remark" cols="50" rows="4" 
						datatype="/^\\s*$|s1-250" errormsg="个人简介字数不能超过250！"></textarea>
					</td>
					<td align="left"><span class="cRed Validform_checktip"></td>
				</tr>
				<tr>
					<td><label class="w100">验证码：</label></td>
					<td><input type="text" class="input_text w100"
						name="code" id="code" datatype="*" ajaxurl="<c:url value="/ucaRegist/checkIdentifyingCode" />"
						nullmsg="请输入验证码！" errormsg="请输入验证码！" /> <span class="yzm"><img
							id="captchaImg" src="<c:url value="/jcaptcha.jpg"/>" />&nbsp;<a
							href="javascript:refreshCaptcha()">看不清楚换一张</a></span></td>
					<td align="left"><span class="cRed Validform_checktip"></span></td>
				</tr>
				<tr>
					<td><label class="w100">&nbsp;</label></td>
					<td><font color="red"> <span id="errorMsg"> <c:if
									test="${sessionScope.errorCode != null}">
									<spring:message code="${sessionScope.errorCode}" />
									<%
									    request.getSession().removeAttribute("errorCode");
									%>
								</c:if> <c:if test="${sessionScope.error != null}">
				${sessionScope.error}
				<%
				    request.getSession().removeAttribute("error");
				%>
								</c:if> <c:if test="${param.error == '1'}">
									<spring:message code="loginPage.verifyCodeFailure" />
								</c:if>
						</span>
					</font></td>
					<td align="left">&nbsp;</td>
				</tr>
				<tr>
					<td><label class="w100">&nbsp;</label></td>
					<td><input type="submit"
						onmouseover="javascript:this.style='background:url(${ctx}/resources/images/btn_a.gif) no-repeat;color:#ffffff;font-weight:bold;'"
						onmouseout="javascript:this.style='background:url(${ctx}/resources/images/btn_b.gif) no-repeat;'"
						class="input_btn01" value="提交" />
						<input type="reset"
						onmouseover="javascript:this.style='background:url${ctx}/resources/images/btn_a.gif) no-repeat;color:#ffffff;font-weight:bold;'"
						onmouseout="javascript:this.style='background:url(${ctx}/resources/images/btn_b.gif) no-repeat;'"
						class="input_btn01" value="重置" /> 
						<input type="button"
						onclick="javascript:registCancel();"
						onmouseover="javascript:this.style='background:url(${ctx}/resources/images/btn_a.gif) no-repeat;color:#ffffff;font-weight:bold;'"
						onmouseout="javascript:this.style='background:url(${ctx}/resources/images/btn_b.gif) no-repeat;'"
						class="input_btn01" value="取消" /></td>
					<td align="left">&nbsp;</td>
				</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
	function registCancel() {
		window.location.href = '${ctx}/';
	}
</script>
<script type="text/javascript">
	$(function() {
		$(".rfValidForm")
				.Validform(
						{
							tiptype : 2,
							postonce : true,
							datatype : {
								"un4-20" : /^[a-z]{1}[a-z0-9]{3,19}$/,
								"zh1-6" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/,
								"pwd8-16" : /^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9\\!\\@\\#\\$%\\^\\*\\_\\?]{8,16}$/,
								"s1-300" : /^[\u4E00-\u9FA5\uf900-\ufa2d\w\W\.\s]{1,300}$/,
								"*1-100":/^[\w\W]{1,100}$/
							}
						});
	});

	function refreshCaptcha() {
		$('#captchaImg').hide().attr(
				'src',
				'<c:url value="/jcaptcha.jpg"/>' + '?'
						+ Math.floor(Math.random() * 100)).fadeIn();
	}
</script>
</body>
</html>