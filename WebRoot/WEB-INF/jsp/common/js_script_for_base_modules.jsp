<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>

<!-- jquery-easyui-1.3.5 -->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/icon.css">
<script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script> --%>

<!-- jquery-easyui-1.4 -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>

<!-- easyui扩展 -->
<script type="text/javascript" src="${ctx}/resources/js/jquery-extensions-master/release/jquery.jdirk.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-extensions-master/icons/icon-all.min.css">
<script type="text/javascript" src="${ctx}/resources/js/jquery-extensions-master/release/jeasyui.icons.all.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/js/jquery-extensions-master/release/jeasyui.extensions.min.css">
<script type="text/javascript" src="${ctx}/resources/js/jquery-extensions-master/release/jeasyui.extensions.all.min.js"></script>

<!-- jquery & easyui helpers -->
<%-- <script type="text/javascript" src="${ctx}/resources/js/erm/_helpers/SetColumns.js"></script> --%>
<script type="text/javascript" src="${ctx}/resources/js/uca/_helpers/EnumsHelper.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/_helpers/FilePathHelper.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/_helpers/MaskJqueryPlugin.js"></script>
<%@ include file="/WEB-INF/jsp/uca/_helpers/easyui_extension_combobox_clear.jsp"%>

<!-- others -->
<script type="text/javascript">
	//禁止windows自带的右键点击事件
	document.oncontextmenu = function() {
		return false;
	}; 
</script>
