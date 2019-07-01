<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<!-- declaration on the very top, to avoid turning into 'quirks mode' -->

<!-- jquery-easyui: easyui-layout版系统主页 > easyui-accordion -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>城市建设档案网上办公系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript">
	function refreshContent(url) {
		$('#cpanel').panel('open').panel('refresh', url);
	}

	var maxWindow = 10;
	var defaultTitle = '首页';
	$(function() {
		/**var p = $('body').layout('panel','west').panel({
			onCollapse:function(){}
		});**/
		tabCloseEven();

		$('#main').tabs({
			onSelect : function() {
				var tab = $('#main').tabs('getSelected');
			}
		});

		/**
		$('#leftmenu').accordion({
			onSelect: function(){
				var tab = $('#leftmenu').accordion('getSelected');
				var tname = tab.panel('options').title;
				if(tname == '资源目录'){
				}
			}
		});**/

		var content = '<iframe scrolling="auto" frameborder="0"  src="${ctx}/about" style="width:100%;height:100%;"></iframe>';
		$('#main').tabs('add', {
			title : defaultTitle,
			content : content,
			closable : false
		});
		
		getNotice();
		getMeetNoRead();
	});

	function addTab(id, title, url, jsParameters) {
		var tS = $('#a_' + id).attr("jsParam");
		if(tS) {
			jsParameters = tS;
		}
		var tabslength = $('#main').tabs('tabs').length;
		
		var urlWithJsParameters = '${ctx}' + url;
		
		if(jsParameters) {
			if(JSON.stringify(jsParameters)) {
				urlWithJsParameters = encodeURI(
										'${ctx}' 
										+ url 
										+ '?jsParameters=' 
										+ JSON.stringify(jsParameters)
									);
			}
		}
		
		if (tabslength < maxWindow) {
			if ($('#main').tabs('exists', title)) {
				/* var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ urlWithJsParameters 
					+ '" style="width:100%;height:100%;"></iframe>';
				var tab = $('#main').tabs('getTab', title);
				
				$('#main').tabs('update', {
					tab : tab,
					options : {
						title : title,
						content : content,
						closable : true
					}
				}); */
				$('#main').tabs('select', title);
			} else {
				var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ urlWithJsParameters 
					+ '" style="width:100%;height:100%;"></iframe>';
					
				$('#main').tabs('add', {
					title : title,
					content : content,
					closable : true
				});
			}
		} else {
			alert("您打开窗口太多了，请关闭不用的窗口！");
			return false;
		}

		$(".tabs-inner").dblclick(function() {
			var ti = $(this).children("span").text();
			if(ti != defaultTitle){
				$('#main').tabs('close',ti);
			}
		});

		//禁止windows自带的右键点击事件
		document.oncontextmenu = function() {
			return false;
		};
		//右键点击事件
		$(".tabs-inner").bind('contextmenu', function(e) {
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
			var subtitle = $(this).children("span").text();
			$('#main').data("currtab", subtitle);
			return false;
		});
	}
	
	function updateTab() {
		var tab = $('#main').tabs('getSelected');
		var tname = tab.panel('options').title;
		$('#main').tabs('refresh', tname);
	}

	function closeTab() {
		var tab = $('#main').tabs('getSelected');
		var tname = tab.panel('options').title;
		$('#main').tabs('close', tname);
	}
	
	function getNotice(){
		$.ajax({
		    url: rfPath + '/notice/getNotice',
		    type: 'POST',
		    dataType: 'json',
		    success: function(data){
		    	if(data.success && data.noticeCount != 0) {
		    		var showMsg = "您有新的通知通告<a id='a_notice' href='###' onclick='addTab(\"\",\"查阅通知通告\",\"/notice/showNotice\",null)' class='cYellow'>"+data.noticeCount+"</a>条";
		    		$("#noticeCount").html(showMsg);
		    		/*var objVo = data.objVo
		    		var showMsg = '<div align="center"><table width="350"><tr align="center" style="font-size:20px"><td colspan="2">'+objVo.title+'</td><tr>';
		    		showMsg += '<tr align="right" height="10"><td colspan="2">&nbsp;</td><tr>';
		    		showMsg += '<tr><td  colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+objVo.content+'</td><tr>';
		    		if(objVo.fileUrl != ""){
		    			showMsg += '<tr><td  colspan="2">文件：'+'<a href="'+(objVo.fileUrl != null?rfPath + '/download_file/dbp?p='+objVo.fileUrl+'&fn='+decodeURI(objVo.sourceFileName):'###')+'" target="_blank">'+objVo.sourceFileName+'</a></td><tr>';
		    		}
		    		showMsg += '<tr><td>'+(objVo.contactUser != ""?'联系人：'+objVo.contactUser:"")+'</td><td>'+(objVo.tel != ""?'联系电话：'+objVo.tel:"")+'</td><tr>';
					showMsg += '<tr><td>'+(objVo.fax != ""?'传真：'+objVo.fax:"")+'</td><td>'+(objVo.email != ""?'电子邮箱：'+objVo.email:"")+'</td><tr>';
					showMsg += '<tr align="right" height="25"><td colspan="2">&nbsp;</td><tr>';
					showMsg += '<tr align="right"><td colspan="2">'+objVo.releaseTime+'</td><tr>';
		    		showMsg += '</table></div>';
		    		var options = {
		    			title: "通知通告",
		    			msg: showMsg,
		    			showType: "slide",
		    			width: 500,
		    			height: 300,
		    			timeout: 10000
		    		}
		    		parent.$.messager.show(options); */
		    	}
		    }
		});
	}
	
	function getMeetNoRead(){
		$.ajax({
		    url: rfPath + '/mrapply/getMeetNoRead',
		    type: 'POST',
		    dataType: 'json',
		    success: function(data){
		    	if(data.success) {
		    		var objPo = data.objPo
		    		$('#applyUserId_jsid').val(data.applyUserId);
		    		$('#applyId_jsid').val(objPo.applyId);
		    		$('#meetingName_jsid').html(objPo.meetingName);
		    		$('#conferenceName_jsid').html(objPo.conferenceName);
		    		$('#time_jsid').html(objPo.beginDate+" - "+objPo.endDate);
		    		$('#remark_jsid').html(objPo.remark);
		    		$('#dlg_read').dialog('open').dialog('setTitle','新会议邀请通知');
		    	}
		    }
		});
	}
	
	function read(){
		$.ajax({
		    url: rfPath + '/mrapply/meetRead',
		    type: 'POST',
		    dataType: 'json',
		    data:{
		    	applyUserId : $('#applyUserId_jsid').val(),
		    	applyId : $('#applyId_jsid').val()
		    },
		    success: function(data){
		    	$('#dlg_read').dialog('close');
		    }
		});
	}
	
</script>
</head>
<body>
	<div class="easyui-layout" style="height: 100%;"
		data-options="fit:true">
		<div data-options="region:'north',split:true"style="height:62px;cursor:pointer;">
			<%-- <div class="easyui-panel" data-options="border:false">
				<span class="ico01" style="padding-top: 40px;"> <a
					href="javascript:void(0);" class="easyui-linkbutton"
					data-options="plain:true"><font color="red">您好，欢迎<sec:authentication
								property="name" />来到档案网上办公系统！
					</font></a> <a href="<c:url value="/" />" class="easyui-linkbutton" data-options="plain:true"><font color="red">首页</font></a>
					<a href="<c:url value="/j_logout"/>" class="easyui-linkbutton"
					target="_self" data-options="plain:true"><font color="red">安全退出</font></a>
				</span>
			</div> --%>
			<div class="top">
				<div class="logo">城市建设档案网上办公系统</div>
				<div class="top_right">
					<div class="info">欢迎您登录！<span><sec:authentication property="name" /></span>&nbsp;&nbsp;<a  id="a_" href="javascript:void(0);" onclick='javascript:addTab("","修改密码","/securityU/toModifyPwd",null)' class="cYellow">修改密码</a>&nbsp;&nbsp;<a href="<c:url value="/j_logout"/>" class="exit"></a><p><span id="noticeCount"></span></div>
					
				</div>
			</div>
		</div>
		<div data-options="region:'west',split:true" title="菜单项"
			style="width: 210px;">
			
			<%-- <div class="easyui-accordion" data-options="fit:true,border:false">
				<c:forEach items="${menuNodes }" var="menu">
					<div title="${menu.resourceName }">
						<c:if test="${fn:length(menu.childrenList) > 0 }">
							<div class="easyui-accordion"
								data-options="fit:true,border:false">
								<c:forEach items="${menu.childrenList }" var="child">
									<div title="${child.resourceName }" style="padding: 10px;">
										<c:forEach items="${child.childrenList }" var="ch">
											<c:if test="${ch.resourceUrl != null }">
												<c:choose>
												<c:when test="${ch.jsParameters != null}">
													<a href="javascript:void(0);"
														onclick="javascript:addTab('${ch.resourceId }','${ch.resourceName }','${ch.resourceUrl }', ${ch.jsParameters})"
														class="easyui-linkbutton" data-options="plain:true">${ch.resourceName }</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:void(0);"
														onclick="javascript:addTab('${ch.resourceId }','${ch.resourceName }','${ch.resourceUrl }', null)"
														class="easyui-linkbutton" data-options="plain:true">${ch.resourceName }</a>
												</c:otherwise>
												</c:choose>
												<br />
											</c:if>
											<c:if test="${ch.resourceUrl == null }">
												<a href="javascript:void(0);" target="mIframe"
													class="easyui-linkbutton" data-options="plain:true">${ch.resourceName }</a>
												<br />
												<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${ch.resourceName }</a><br />
											</c:if>
										</c:forEach>
									</div>
								</c:forEach>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</div> --%>
			
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<c:forEach items="${menuNodes }" var="menu">
					<div title="${menu.resourceName }">
						<c:if test="${fn:length(menu.childrenList) > 0 }">
							${menu.resourceUrl }
						</c:if>
					</div>
				</c:forEach>
			</div>
			
		</div>
		<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
		<div data-options="region:'center',split:true"
			<%-- title="标准时间：<fmt:formatDate value="${now}" pattern="yyyy年MM月dd日 E"/>" --%>>
			<div id="main" data-options="fit:true,border:false"></div>
		</div>
		<div data-options="region:'south',split:true" style="height: 24px;">
			<div id="dlg_read" class="easyui-dialog" style="width:400px;height:160px;padding:5px" data-options="closed:true,buttons:'#up-buttons',resizable:true">
				<div align="center">
					<input type="hidden" id="applyUserId_jsid" />
					<input type="hidden" id="applyId_jsid" />
					<table width="360">
						<tr><td align="right" width="100">会议室：</td><td align="left" id="meetingName_jsid"></td></tr>
						<tr><td align="right" width="100">会议名称：</td><td align="left" id="conferenceName_jsid"></td></tr>
						<tr><td align="right" width="100">会议时间：</td><td align="left" id="time_jsid"></td></tr>
						<tr><td align="right" width="100">备注：</td><td align="left" id="remark_jsid"></td></tr>
					</table>
				</div>
				<div id="up-buttons">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:read()">不再提醒</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#dlg_read').dialog('close')">取消</a>
				</div>
			</div>
			<div align="center">Powered by &copy;</div>
		</div>
	</div>
</body>
</html>
<div id="mm" class="easyui-menu" style="width: 150px;">
	<div id="mm-tabclose">关闭</div>
	<div id="mm-tabcloseall">全部关闭</div>
	<div id="mm-tabcloseother">除此之外全部关闭</div>
	<div class="menu-sep"></div>
	<div id="mm-tabcloseright">当前页右侧全部关闭</div>
	<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
</div>
<script>
	function tabCloseEven() {
		//关闭当前
		$('#mm-tabclose').click(function() {
			var tt = $('#main').data("currtab");
			$('#main').tabs('select', tt);
			if(tt == defaultTitle){
				$.messager.alert('信息提示',"不能关闭默认tab页", 'info',function(){
			        return false;
			    });
			}else{
				$('#main').tabs('close', tt);
			}
			
		}),
		//全部关闭
		$('#mm-tabcloseall').click(function() {
			var tt = $('#main').data("currtab");
			$('#main').tabs('select', tt);

			$('.tabs-inner span').each(function(i, n) {
				var t = $(n).text();
				if(t != defaultTitle){
					$('#main').tabs('close', t);
				}
				
			});
		});
		//关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function() {
			var tt = $('#main').data("currtab");
			$('#main').tabs('select', tt);

			$('.tabs-inner span').each(function(i, n) {
				var t = $(n).text();
				if (t != tt) {
					$('#main').tabs('close', t);
				}
			});
		});
		//关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function() {
			var tt = $('#main').data("currtab");
			$('#main').tabs('select', tt);

			var nextall = $('.tabs-selected').nextAll();
			if (nextall.length == 0) {
				$.messager.alert('信息提示', "右侧没有可以关闭的tab页", 'info', function() {
					return false;
				});
			}
			nextall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				if(t != defaultTitle){
					$('#main').tabs('close', t);
				 }
				
			});
			return false;
		});
		//关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function() {
			var tt = $('#main').data("currtab");
			$('#main').tabs('select', tt);

			var prevall = $('.tabs-selected').prevAll();
			if (prevall.length <= 0) {
				$.messager.alert('信息提示', "左侧没有可以关闭的tab页", 'info', function() {
					return false;
				});
			}
			prevall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				if(t != defaultTitle){
					$('#main').tabs('close', t);
				}
				
			});
			return false;
		});
	}
</script>

<!-- jquery-easyui: easyui-layout版系统主页 > ztree -->
<%-- 
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
    <title>档案网上办公系统</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/demo.css">
    <link rel="stylesheet" href="${ctx}/resources/css/ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.all-3.5.min.js"></script>
    <SCRIPT type="text/javascript">
	<!--
	var setting = {
		view: {
			showIcon: showIconForTree,
			showLine: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url:"${ctx}/toLeft",
			dataFilter: filter
		}
	};
	
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	
	function showIconForTree(treeId, treeNode) {
		return !treeNode.isParent;
	};
	
	$(document).ready(function(){
		$.fn.zTree.init($("#menuTree"), setting);
	});
	//-->
	</SCRIPT>
</head>
<body>
<div align="center">
    <div id="cc" class="easyui-layout" style="width:1100px;height:500px;">
        <div data-options="region:'north'" style="height:60px;padding-top: 20px;">
	        <a href="<c:url value="/" />">首页</a>&nbsp;&gt;&nbsp;系统菜单
	    	<span class="ico01" style="padding-top: 20px;">您好，欢迎<sec:authentication property="name"/>来到档案网上办公系统！
			<a href="<c:url value="/j_logout"/>" target="_self">[安全退出]</a>
			</span>
        </div>
        <div data-options="region:'west',split:true" title="档案网上办公系统菜单" style="width:250px;">
			<ul id="menuTree" class="ztree"></ul>
        </div>
        <div data-options="region:'center',title:'档案网上办公系统主页'">
			<iframe name="mIframe" id="mIframe" frameborder="no" scrolling="auto" border="0" marginwidth="0" marginheight="0" width="100%" height="100%" ></iframe>
        </div>
    </div>
</div>
<div>&nbsp;</div>
<%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
 --%>


<!-- css: 普通版系统主页 -->
<%-- 
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<title>Index_Page</title>
		<link rel="stylesheet" href="${ctx}/resources/css/ztree/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.all-3.5.min.js"></script>
		<SCRIPT type="text/javascript">
		<!--
		var setting = {
			view: {
				showIcon: showIconForTree,
				showLine: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			async: {
				enable: true,
				url:"${ctx}/toLeft",
				dataFilter: filter
			}
		};
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		
		function showIconForTree(treeId, treeNode) {
			return !treeNode.isParent;
		};
		
		$(document).ready(function(){
			$.fn.zTree.init($("#menuTree"), setting);
		});
		//-->
		</SCRIPT>
	</head>
	<body>
		<div class="hr1 ow mt1">
	    	<div class="fl">档案网上办公系统</div>
		</div>
		<div class="mn2 mt1">
			<h2 class="te2">
		        <a href="<c:url value="/" />">首页</a>&nbsp;&gt;&nbsp;系统菜单
		    	<span class="ico01">您好，欢迎<sec:authentication property="name"/>来到档案网上办公系统！
				<a href="<c:url value="/j_logout"/>" target="_self">[安全退出]</a>
				</span>
		    </h2>
		</div>
		<div class="mn2 mt1">
		    <div class="pb">
				<div class="lb">
					<ul id="menuTree" class="ztree"></ul>
				</div>
				<div class="mb">
					<iframe name="mIframe" id="mIframe" frameborder="no" scrolling="auto" border="0" marginwidth="0" marginheight="0" width="100%" height="480px" ></iframe>
				</div>
			</div>
		</div>
<%@ include file="/WEB-INF/jsp/common/foot.jsp" %>
 --%>

