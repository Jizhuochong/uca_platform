<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
<div class="main">
  <div class="box clearfix">
    <div class="tit21"> <span>用户注册</span> </div>
    <div class="jump mt30" align="center">
    <c:choose>
    	<c:when test="${success == true}">
   		<div class="info"><i> 恭喜您！${msg }！</i></div>
		<%-- <div class="mt60"><img src="${ctx}/resources/images/tzjt.gif" /></div> --%>
		<div class="mt10">请进入邮箱并按提示激活用户账户。<input type="hidden" value="${email }" id="email"/></div>
	 	<div><a href="<c:url value="${registBackurl}"/>" class="cRed01" id="js_login_mail">立即登录邮箱</a></div>
    	</c:when>
    	<c:otherwise>
    	<c:choose>
    		<c:when test="${flag == 2}">
    		<div class="info"><i> ${msg}</i></div>
			<%-- <div class="mt60"><img src="${ctx}/resources/images/tzjt.gif" /></div> --%>
			<div class="mt10"><span class="cRed01" id="seconds">5</span>秒后自动跳转到注册页面</div>
		 	<div><a href="<c:url value="${registBackurl}"/>" class="cRed01">点击跳转注册页面</a></div>
    		</c:when>
    		<c:otherwise>
    		<div class="info"><i> ${msg}</i></div>
			<%-- <div class="mt60"><img src="${ctx}/resources/images/tzjt.gif" /></div> --%>
			<div class="mt10"><span class="cRed01" id="seconds">5</span>秒后自动跳转到登录页面</div>
		 	<div><a href="<c:url value="${registBackurl}"/>" class="cRed01">点击跳转登录页面</a></div>
    		</c:otherwise>
    	</c:choose>
    	</c:otherwise>
    </c:choose>
    </div>
  </div>
</div>
</div>
<script language="javascript" type="text/javascript">
	var i = 5;
	var intervalid;
	if(document.getElementById("seconds") != undefined){
		intervalid = setInterval("fun()", 1000);
	}
    
    function fun() {
        if (i == 0) {
            window.location.href = "<c:url value="${registBackurl}"/>";
            clearInterval(intervalid);
        }
        document.getElementById("seconds").innerHTML = i;
        i--;
    }

    var hash = {
	'qq.com': 'http://mail.qq.com',
	'gmail.com': 'http://mail.google.com',
	'sina.com': 'http://mail.sina.com.cn',
	'163.com': 'http://mail.163.com',
	'126.com': 'http://mail.126.com',
	'yeah.net': 'http://www.yeah.net/',
	'sohu.com': 'http://mail.sohu.com/',
	'tom.com': 'http://mail.tom.com/',
	'sogou.com': 'http://mail.sogou.com/',
	'139.com': 'http://mail.10086.cn/',
	'hotmail.com': 'http://www.hotmail.com',
	'live.com': 'http://login.live.com/',
	'live.cn': 'http://login.live.cn/',
	'live.com.cn': 'http://login.live.com.cn',
	'189.com': 'http://webmail16.189.cn/webmail/',
	'yahoo.com.cn': 'http://mail.cn.yahoo.com/',
	'yahoo.cn': 'http://mail.cn.yahoo.com/',
	'eyou.com': 'http://www.eyou.com/',
	'21cn.com': 'http://mail.21cn.com/',
	'188.com': 'http://www.188.com/',
	'foxmail.com': 'http://www.foxmail.com',
	'outlook.com': 'http://www.outlook.com'
	}
    
 	//点击登录邮箱
    var _mail = $("#email").val().split('@')[1];//获取邮箱域
    for (var j in hash){
        if(j == _mail){
            $("#js_login_mail").attr("href", hash[_mail]);//替换登陆链接
            break;
        }
    }
</script>
<%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
