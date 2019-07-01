<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<link rel="stylesheet" href="${ctx}/resources/css/ztree/zTreeStyle.css" type="text/css">
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/default/easyui.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/icon.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/demo.css">
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.core-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
		<SCRIPT type="text/javascript">
		<!--
		var IDMark_A = "_a";
		
		var setting = {
			view: {
				dblClickExpand: dblClickExpand,
				addDiyDom: addDiyDom
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			async: {
				enable: true,
				url:"${ctx}/resource/trees",
				autoParam:["id"],
				dataFilter: filter
			},
			callback: {
				onRightClick: OnRightClick
			}
		};
		
		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}
		
		function addDiyDom(treeId, treeNode) {
			if(treeNode.level == 0) {
				var aObj = $("#" + treeNode.tId + IDMark_A);
				
				var addStr = "<span class='demoIcon' id='diyBtn_add_" +treeNode.id+ "'><span class='button add'></span></span>";
				aObj.after(addStr);
				var add_btn = $("#diyBtn_add_"+treeNode.id);
				if (add_btn) {
					add_btn.bind("click", function(){
						//$("#addForm").attr("action","${ctx}/resource/toAdd");
						//$("#res_parent_id").attr("value", treeNode.id);
						//$("#addForm").submit();
						
						var htmlContent = "<iframe frameborder='no' scrolling='auto' border='0' style='width:100%;height:100%;' marginwidth='0' marginheight='0' src='${ctx}/resource/toAdd?res_parent_id="+treeNode.id+"'></iframe>";
						$('#w').html(htmlContent);
		                $('#w').panel('open');
					});
				}
			}
			if(treeNode.level > 0) {
				var aObj = $("#" + treeNode.tId + IDMark_A);
				
				var delStr = "<span class='demoIcon' id='diyBtn_del_" +treeNode.id+ "'><span class='button remove'></span></span>";
				aObj.after(delStr);
				var del_btn = $("#diyBtn_del_"+treeNode.id);
				if (del_btn) {
					del_btn.bind("click", function(){
						if (confirm('是否确认删除？')) {
					    	$.ajax({
							    url: '${ctx}/resource/delete/'+treeNode.id,
							    type: 'GET',
							    dataType: 'json',
							    success: function(data){
						    		alert(data.text);
						    		//location.href='${ctx}/resource/toTree';
						    		removeNode(treeNode.id, treeNode.pId);
							    },
							    error: function(){
							    	alert("资源删除失败，请重试！");
							    }
							});
					    }
					});
				}
				
				var showStr = "<span class='demoIcon' id='diyBtn_show_" +treeNode.id+ "'><span class='button icon01_open'></span></span>";
				aObj.after(showStr);
				var show_btn = $("#diyBtn_show_"+treeNode.id);
				if (show_btn) {
					show_btn.bind("click", function(){
						//$("#showForm").attr("action","${ctx}/resource/show/"+treeNode.id);
						//$("#showForm").submit();
						
						var htmlContent = "<iframe frameborder='no' scrolling='auto' border='0' style='width:100%;height:100%;' marginwidth='0' marginheight='0' src='${ctx}/resource/show/"+treeNode.id+"'></iframe>";
						$('#w').html(htmlContent);
		                $('#w').panel('open');
					});
				}
				
				var editStr = "<span class='demoIcon' id='diyBtn_edit_" +treeNode.id+ "'><span class='button edit'></span></span>";
				aObj.after(editStr);
				var edit_btn = $("#diyBtn_edit_"+treeNode.id);
				if (edit_btn) {
					edit_btn.bind("click", function(){
						//$("#editForm").attr("action","${ctx}/resource/update/"+treeNode.id);
						//$("#editForm").submit();
						
						var htmlContent = "<iframe frameborder='no' scrolling='auto' border='0' style='width:100%;height:100%;' marginwidth='0' marginheight='0' src='${ctx}/resource/update/"+treeNode.id+"'></iframe>";
						$('#w').html(htmlContent);
		                $('#w').panel('open');
					});
				}
				
				var addStr = "<span class='demoIcon' id='diyBtn_add_" +treeNode.id+ "'><span class='button add'></span></span>";
				aObj.after(addStr);
				var add_btn = $("#diyBtn_add_"+treeNode.id);
				if (add_btn) {
					add_btn.bind("click", function(){
						//$("#addForm").attr("action","${ctx}/resource/toAdd");
						//$("#res_parent_id").attr("value", treeNode.id);
						//$("#addForm").submit();
						
						var htmlContent = "<iframe frameborder='no' scrolling='auto' border='0' style='width:100%;height:100%;' marginwidth='0' marginheight='0' src='${ctx}/resource/toAdd?res_parent_id="+treeNode.id+"'></iframe>";
						$('#w').html(htmlContent);
		                $('#w').panel('open');
					});
				}
			}
		}
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		
		function OnRightClick(event, treeId, treeNode) {
			if(treeNode.level > 0) {
				if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
					zTree.cancelSelectedNode();
					showRMenu(event.clientX, event.clientY);
				} else if (treeNode && !treeNode.noR) {
					zTree.selectNode(treeNode);
					showRMenu(event.clientX, event.clientY);
				}
				$("#moveUp").attr("value", treeNode.id);
				$("#moveUp").attr("title", treeNode.pId);
				$("#moveDown").attr("value", treeNode.id);
				$("#moveDown").attr("title", treeNode.pId);
			}
		}
		
		function showRMenu(x, y) {
			$("#rMenu ul").show();
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

			$("body").bind("mousedown", onBodyMouseDown);
		}
		
		function hideRMenu() {
			if (rMenu) {
				rMenu.css({"visibility": "hidden"});
			}
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
		}
		
		function nodeMoveUp() {
			hideRMenu();
			var nodeId = $("#moveUp").val(), pid = $("#moveUp").attr("title");
			$.ajax({
			    url: '${ctx}/resource/move/7/'+nodeId,
			    type: 'GET',
			    dataType: 'json',
			    //timeout: 1000,
			    success: function(data){
			    	if(!data.success) {
			    		alert(data.msg);
			    	} else {
			    		alert(data.msg);
			    		//location.href='${ctx}/resource/toTree';
			    		moveNode(pid);
			    	}
			    }
			});
		}
		
		function nodeMoveDown() {
			hideRMenu();
			var nodeId = $("#moveDown").val(), pid = $("#moveDown").attr("title");
			$.ajax({
			    url: '${ctx}/resource/move/8/'+nodeId,
			    type: 'POST',
			    dataType: 'json',
			    //timeout: 1000,
			    success: function(data){
					if(data.success) {
						alert(data.msg);
						//window.location='${ctx}/resource/toTree';
						moveNode(pid);
			    	} else {
			    		alert(data.msg);
			    	}
			    }
			});
		}
		
		function addNode(id, pid, name) {
			var zTree = $.fn.zTree.getZTreeObj("resTree"),
			type = "refresh",
			silent = false,
			pnode = zTree.getNodeByParam("id", pid);
			var newNode = {"id":id,"pId":pid,"name":name};
			zTree.addNodes(pnode, newNode);
			if(pnode) {
				zTree.reAsyncChildNodes(pnode, type, silent);
				if (!silent) zTree.selectNode(pnode);
                $('#w').panel('close');
			} else {
                $('#w').panel('close');
			}
		}
		
		function updateNode(id, pid, name, beforePid) {
			var zTree = $.fn.zTree.getZTreeObj("resTree"),
			type = "refresh",
			silent = false,
			node = zTree.getNodeByParam("id", id),
			pnode = zTree.getNodeByParam("id", pid);
			node.pId = pid;
			node.name = name;
			if(pid != beforePid) {
				// 新父节点添加子节点
				addNode(id, pid, name);
				// 旧父节点删除子节点
				removeNode(id, beforePid);
			} else {
				zTree.updateNode(node);
				if(pnode) {
					zTree.reAsyncChildNodes(pnode, type, silent);
					if (!silent) zTree.selectNode(pnode);
	                $('#w').panel('close');
				} else {
	                $('#w').panel('close');
				}
			}
		}
		
		function removeNode(id, pid) {
			var zTree = $.fn.zTree.getZTreeObj("resTree"),
			type = "refresh",
			silent = false,
			node = zTree.getNodeByParam("id", id),
			pnode = zTree.getNodeByParam("id", pid);
			zTree.removeNode(node);
			if(pnode) {
				zTree.reAsyncChildNodes(pnode, type, silent);
				if (!silent) zTree.selectNode(pnode);
			}
		}
		
		function moveNode(pid) {
			var zTree = $.fn.zTree.getZTreeObj("resTree"),
			type = "refresh",
			silent = false,
			pnode = zTree.getNodeByParam("id", pid);
			if(pnode) {
				zTree.reAsyncChildNodes(pnode, type, silent);
				if (!silent) zTree.selectNode(pnode);
			}
		}
		
		var zTree, rMenu;
		$(document).ready(function(){
			$.fn.zTree.init($("#resTree"), setting);
			zTree = $.fn.zTree.getZTreeObj("resTree");
			rMenu = $("#rMenu");
		});
		//-->
	</SCRIPT>
	<style type="text/css">
		.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
		.ztree li ul.level0 {padding:0; background:none;}
		div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
		div#rMenu ul li{
			margin: 1px 0;
			padding: 0 5px;
			cursor: pointer;
			list-style: none outside none;
			background-color: #DFDFDF;
		}
	</style>
	</head>
	<body>
		<div class="easyui-panel" data-options="fit:true,border:false" style="padding:2px;">
	        <div class="easyui-layout" data-options="fit:true">
	            <div data-options="region:'west',split:true" style="width:380px;">
					<ul id="resTree" class="ztree"></ul>
	            </div>
	            <div data-options="region:'center'">
	                <div id="w" class="easyui-panel" data-options="fit:true,border:false,collapsible:true,closable:true,closed:true"></div>
	            </div>
	        </div>
	    </div>
		<form method="post" action="" id="addForm">
			<input type="hidden" name="res_parent_id" id="res_parent_id" value=""/>
		</form>
		<form method="post" action="" id="editForm"></form>
		<form method="post" action="" id="showForm"></form>
		<div id="rMenu">
			<ul>
				<li id="moveUp" onclick="nodeMoveUp();" value="" title="">同级节点上移</li>
				<li id="moveDown" onclick="nodeMoveDown();" value="" title="">同级节点下移</li>
			</ul>
		</div>
	</body>
</html>
