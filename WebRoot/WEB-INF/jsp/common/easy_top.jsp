<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
		<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">var rfPath = '${ctx}';</script>
		<script type="text/javascript">
			//全局的ajax访问，处理ajax清求时sesion超时
	        $.ajaxSetup({ 
	            contentType:"application/x-www-form-urlencoded;charset=utf-8", 
	            complete:function(XMLHttpRequest,textStatus){
                         if(textStatus=="parsererror"){
                        	 window.top.location.reload("${ctx}/toErmLogin");
                         }
	            }
	        });
		</script>