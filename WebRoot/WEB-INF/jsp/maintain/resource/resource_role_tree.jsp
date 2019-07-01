<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<link rel="stylesheet" href="${ctx}/resources/css/ztree/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.core-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
		<SCRIPT type="text/javascript">
		<!--
		var setting = {
			view: {
				dblClickExpand: dblClickExpand
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			async: {
				enable: true,
				url:"${ctx}/resource/treesForRole",
				otherParam: ["roleId", ${roleId}],
				dataFilter: filter
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "ps" }
			}
		};
		
		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		
		var zTree;
		$(document).ready(function(){
			$.fn.zTree.init($("#roleResTree"), setting);
			zTree = $.fn.zTree.getZTreeObj("roleResTree");
		});
		//-->
	</SCRIPT>
	<style type="text/css">
		.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
		.ztree li ul.level0 {padding:0; background:none;}
	</style>
	</head>
	<body>
		<form action="${ctx}/role/saveRoleAndResource" method="post" id="subForm" >
		<div>
			<input type="button" class="bn3" value="保存" onclick="return subCheck()"/>
			<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);"/>
		</div>
		<div class="zTreeDemoBackground center">
			<ul id="roleResTree" class="ztree"></ul>
		</div>
		<div>
			<input type="button" class="bn3" value="保存" onclick="return subCheck()"/>
			<input type="button" class="bn3" value="返回" onclick="javascript:history.go(-1);"/>
		</div>
		</form>
	<script type="text/javascript">
	function subCheck(){
		var arr = new Array();
		$.each(zTree.getCheckedNodes(true), function(index, node){
			arr.push(node.id);
		});
		$.ajax({
		    url: '${ctx}/role/saveRoleAndResource',
		    type: 'POST',
		    dataType: 'json',
		    data: {resIds:arr,roleId:${roleId}},
		    success: function(data){
	    		alert(data.text);
	    		location.href='${ctx}/role/index';
		    },
		    error: function(){
		    	alert("资源分配保存失败，请重试！");
		    }
		});
	}
	</script>
	</body>
</html>