var ermContextPath = this.rfPath;

var me = this;

var listToDisplayUrl = ermContextPath + '/procDef/listToDisplay';

var dataGridTitle = '流程定义列表';

// datagrid
$(function(){
	// businessParameters
	var businessParameters = {};
	businessParameters['containColumnProcessDefinitionId'] = true;
	businessParameters['containColumnDeploymentId'] = true;
	businessParameters['containColumnProcessName'] = true;
	businessParameters['containColumnProcessKey'] = true;
	businessParameters['containColumnProcessVersion'] = true;
	businessParameters['containColumnProcessResourceName'] = true;
	businessParameters['containColumnProcessDiagramResourceName'] = true;
	businessParameters['containColumnDeploymentTime'] = true;
	businessParameters['containColumnIsSuspended'] = true;

	// 合并businessParameters
	if(me.businessParameters) {
		businessParameters = $.extend(businessParameters, me.businessParameters);
	}

	// datagrid columns
	var dataGridColumns = new Array();
	
	if(businessParameters.containColumnProcessDefinitionId) {
		dataGridColumns.push(me.getColumnProcessDefinitionId());
	}
	if(businessParameters.containColumnDeploymentId) {
		dataGridColumns.push(me.getColumnDeploymentId());
	}
	if(businessParameters.containColumnProcessName) {
		dataGridColumns.push(me.getColumnProcessName());
	}
	if(businessParameters.containColumnProcessKey) {
		dataGridColumns.push(me.getColumnProcessKey());
	}
	if(businessParameters.containColumnProcessVersion) {
		dataGridColumns.push(me.getColumnProcessVersion());
	}
	if(businessParameters.containColumnProcessResourceName) {
		dataGridColumns.push(me.getColumnProcessResourceName());
	}
	if(businessParameters.containColumnProcessDiagramResourceName) {
		dataGridColumns.push(me.getColumnProcessDiagramResourceName());
	}
	if(businessParameters.containColumnDeploymentTime) {
		dataGridColumns.push(me.getColumnDeploymentTime());
	}
	if(businessParameters.containColumnIsSuspended) {
		dataGridColumns.push(me.getColumnIsSuspended());
	}
	
	$('#dg').datagrid(
			{
				url : listToDisplayUrl,
				title : dataGridTitle,
				rownumbers : true,
				singleSelect : true,
				border : false,
				fitColumns : true,
				loadMsg : '正在加载...',
				pagination : true,
				method : 'post',
				toolbar : '#toolbar',
				fit : true,
				columns : [dataGridColumns],
				//
				nothing : null
			}
	);
	
	var pager = $('#dg').datagrid('getPager'); 
	$(pager).pagination({
		beforePageText:'第',
		afterPageText:'页 - 共{pages}页',
		displayMsg:'本页显示第 {from} 条到第 {to} 条记录 - 共{total}条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			// 搜索后刷新回到第一页
			if($('#dg').datagrid("options").queryParams && pageNumber > 0){
				$(pager).pagination("options").pageNumber = 1;
			}
			// 点击刷新按钮时去掉load的参数
			$('#dg').datagrid("options").queryParams = null;
		}
	});
	
});

//columns
function getColumnProcessDefinitionId() {
	return {
		field : 'processDefinitionId',
		title : 'ID',
		align : 'center',
		halign : 'center',
		width : 100
	};
};

function getColumnDeploymentId() {
	return {
		field : 'deploymentId',
		title : '部署ID',
		align : 'center',
		halign : 'center',
		width : 100
	};
};

function getColumnProcessName() {
	return {
		field : 'processName',
		title : '名称',
		align : 'center',
		halign : 'center',
		width : 100
	};
};

function getColumnProcessKey() {
	return {
		field : 'processKey',
		title : '键值',
		align : 'center',
		halign : 'center',
		width : 100
	};
};

function getColumnProcessVersion() {
	return {
		field : 'processVersion',
		title : '版本号',
		align : 'center',
		halign : 'center',
		width : 50
	};
};

function getColumnProcessResourceName() {
	return {
		field : 'processResourceName',
		title : '流程文件',
		align : 'center',
		halign : 'center',
		width : 100,
		formatter : function(value, row, index){
			var processResourceNameHref = 
				'\<a ' 
				+ 'target=\"_blank\" ' 
				+ 'href=\"' 
				+ ermContextPath 
				+ '/procDef/readResource' 
				+ '?procDefId=' 
				+ row.processDefinitionId 
				+ '&resourceType=xml\">' 
				+ value 
				+ '</a>';
			return processResourceNameHref;
		}
	};
};

function getColumnProcessDiagramResourceName() {
	return {
		field : 'processDiagramResourceName',
		title : '流程图',
		align : 'center',
		halign : 'center',
		width : 100,
		formatter : function(value, row, index){
			var processDiagramResourceNameHref = 
				'<a ' 
				+ 'target=\"_blank\" ' 
				+ 'href=\"' 
				+ ermContextPath 
				+ '/procDef/readResource' 
				+ '?procDefId=' 
				+ row.processDefinitionId 
				+ '&resourceType=image\">' 
				+ value 
				+ '</a>';
			return processDiagramResourceNameHref;
		}
	};
};

function getColumnDeploymentTime() {
	return {
		field : 'deploymentTime',
		title : '部署时间',
		align : 'center',
		halign : 'center',
		width : 100
	};
};

function getColumnIsSuspended() {
	return {
		field : 'suspended',
		title : '流程挂起',
		align : 'center',
		halign : 'center',
		width : 100,
		formatter : function(value, row, index){
			if(value) {
				return '是' 
					+ ' | <a href="#" onclick=' 
					+ '\'javascript:activeProcessDefinition(\"' 
					+ row.processDefinitionId 
					+ '\");\'>' 
					+ '激活' 
					+ '</a>';
			} else {
				return '否' 
					+ ' | <a href="#" onclick=' 
					+ '\'javascript:suspendProcessDefinition(\"' 
					+ row.processDefinitionId 
					+ '\");\'>' 
					+ '挂起' 
					+ '</a>';
			}
		}
	};
};
