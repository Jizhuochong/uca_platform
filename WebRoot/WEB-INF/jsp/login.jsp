<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>城市建设档案网上办公系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<link href="${ctx}/resources/css/validform/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${ctx}/resources/css/validform/valid-style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/validform/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
	<!--
    if (window != window.top){
        window.top.location.reload("${ctx}/toLogin");
    }
	//-->
	</script>
<style type="text/css">
.Validform_checktip{margin-left:0;}
.info{
	border:1px solid #ccc; 
	padding:2px 20px 2px 5px; 
	color:#666; 
	position:absolute;
	display:none;
	line-height:20px;
	background-color:#fff;
	width: 120px;
}
.dec {
    bottom: -8px;
    display: block;
    height: 8px;
    overflow: hidden;
    position: absolute;
    left: 10px;
    width: 17px;
}
.dec s {
    font-family: simsun;
    font-size: 16px;
    height: 19px;
    left: 0;
    line-height: 21px;
    position: absolute;
    text-decoration: none;
    top: -9px;
    width: 17px;
}
.dec .dec1 {
    color: #ccc;
}
.dec .dec2 {
    color: #fff;
    top: -10px;
}

table, td, tr, th {
    border: 0 none;
    border-collapse: collapse;
    font-size: 12px;
}
.main{padding-bottom:50px; padding-top:36px;}
.a_font{font-size: 15px}
</style>
</head>
<body class="login_bg">
<div class="login">
  <div class="logo">城市建设档案网上办公系统</div>
  <div class="main">
    <form class="rfValidForm_login" action="j_spring_security_check" method="post" onsubmit="return checkSubmit();">
    <table>
        <tr>
            <td>
            <p><label>用户名</label></p>
            <div><input type="text" class="login_name" name="j_username" id="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" /></div><!-- datatype="*" nullmsg="请输入用户名！" errormsg="请输入用户名！" -->
            </td>
            <td><div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div></td>
        </tr>
        <tr>
            <td>
            <p><label>密码</label></p>
			<div><input type="password" class="login_psw" name="j_password" id="j_password" /></div><!-- datatype="*" nullmsg="请输入密码！" errormsg="请输入密码！" -->
            </td>
            <td><div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div></td>
        </tr>
     <tr>
            <td>
            <p><label>验证码</label></p>
			<div><input type="text" class="login_yzm" name='j_captcha' id="j_captcha" /><!-- datatype="*" nullmsg="请输入验证码！" errormsg="请输入验证码！" --><a href="javascript:refreshCaptcha()"><img id="captchaImg" src="<c:url value="/jcaptcha.jpg"/>" /></a></div>
			</td>
            <td><div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div></td>
        </tr>
         <tr>
         	<td colspan="2">
         		<div>
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
				</div>
				</td>
			</tr>
            <tr>
                <td colspan="2">
                    <div class="mt15"><input type="submit" class="login_btn" value="" /></div>
                </td>
            </tr>
            <tr>
            	<td colspan="2" align="right">
            		<div class="mt15"><a href="<c:url value="/ucaRegist/toRegist"/>" class="a_font">立即注册</a></div>
            	</td>
            </tr>
        </table>
    </form>
    <script type="text/javascript">
		/* $(function(){
			$(".rfValidForm_login").Validform({
				tiptype:function(msg,o,cssctl){
					if(!o.obj.is("form")){
						var objtip=o.obj.parents("td").next().find(".Validform_checktip");
						cssctl(objtip,o.type);
						objtip.text(msg);
						var infoObj=o.obj.parents("td").next().find(".info");
						if(o.type==2){
							infoObj.fadeOut(200);
						}else{
							if(infoObj.is(":visible")){return;}
							var left=o.obj.offset().left,
								top=o.obj.offset().top;
							infoObj.css({
								left:left-650,
								top:top-45
							}).show().animate({
								top:top-175
							},200);
						}
					}	
				}
			});
		}); */
		function refreshCaptcha() {
			$("#errorMsg").hide();
			$('#captchaImg').hide().attr(
				'src',
				'<c:url value="/jcaptcha.jpg"/>' + '?' + Math
				.floor(Math.random() * 100)).fadeIn();
		}
		function checkSubmit() {
			var j_username = $("#j_username").val(), j_password = $("#j_password").val(), 
				j_captcha = $("#j_captcha").val();
			if(j_username == null || j_username.length == 0 || j_username == ""){
				$.messager.alert('提示', "请输入用户名！");
				return false;
			}
			if(j_password == null || j_password.length == 0 || j_password == ""){
				$.messager.alert('提示', "请输入密码！");
				return false;
			}
			if(j_captcha == null || j_captcha.length == 0 || j_captcha == ""){
				$.messager.alert('提示', "请输入验证码！");
				return false;
			}
			return true;
		};
	</script>
  </div>
  <div align="center">Powered by &copy;</div>
</div>
</body>
</html>

<%-- 
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
    <title>首信电子档案网上办公系统</title>
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
<style type="text/css">
td{line-height:38px;}
.Validform_checktip{margin-left:0;}
.info{
	border:1px solid #ccc; 
	padding:2px 20px 2px 5px; 
	color:#666; 
	position:absolute;
	display:none;
	line-height:20px;
	background-color:#fff;
	width: 180px;
}
.dec {
    bottom: -8px;
    display: block;
    height: 8px;
    overflow: hidden;
    position: absolute;
    left: 10px;
    width: 17px;
}
.dec s {
    font-family: simsun;
    font-size: 16px;
    height: 19px;
    left: 0;
    line-height: 21px;
    position: absolute;
    text-decoration: none;
    top: -9px;
    width: 17px;
}
.dec .dec1 {
    color: #ccc;
}
.dec .dec2 {
    color: #fff;
    top: -10px;
}
</style>
</head>
<body>
    <div class="easyui-panel" title="档案网上办公系统登录" data-options="border:false,fit:true" style="padding:100px;">
       	<form class="rfValidForm" action="j_login" method="post">
            <table style="table-layout:fixed;width:420px;">
                <tr>
                    <td style="width:120px;text-align: right;">用&nbsp;&nbsp;户：</td>
                    <td style="width:115px;"><input type='text' size="24" name='j_username' id="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" class="it2" datatype="*" nullmsg="请输入用户名！" errormsg="请输入用户名！"></td>
                    <td><div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div></td>
                </tr>
                <tr>
                    <td style="width:120px;text-align: right;">密&nbsp;&nbsp;码：</td>
                    <td style="width:115px;"><input type="password" size="24" name="j_password" id="j_password" value="" class="it2" datatype="*" nullmsg="请输入密码！" errormsg="请输入密码！"></td>
                    <td><div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div></td>
                </tr>
                <tr>
                    <td style="width:120px;text-align: right;">验证码：</td>
                    <td style="width:115px;"><input type='text' name='j_captcha' id="j_captcha" size='5' class="it2" datatype="*" nullmsg="请输入验证码！" errormsg="请输入验证码！"/></td>
                    <td><div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div></td>
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
				tiptype:function(msg,o,cssctl){
					if(!o.obj.is("form")){
						var objtip=o.obj.parents("td").next().find(".Validform_checktip");
						cssctl(objtip,o.type);
						objtip.text(msg);
						var infoObj=o.obj.parents("td").next().find(".info");
						if(o.type==2){
							infoObj.fadeOut(200);
						}else{
							if(infoObj.is(":visible")){return;}
							var left=o.obj.offset().left,
								top=o.obj.offset().top;
							infoObj.css({
								left:left+82,
								top:top-45
							}).show().animate({
								top:top-35	
							},200);
						}
					}	
				}
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
        <div align="center">Powered by &copy;</div>
    </div>
</body>
</html>
 --%>