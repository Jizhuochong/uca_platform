var ermContextPath = this.rfPath;

function activeProcessDefinition(procDefId) {
	parent.$.messager.confirm('Confirm', '<p>是否同时激活下属所有流程实例?</p>(取消操作请关闭对话框)', function(confirm) {
		var containProcessInstance = '/this';
		if(confirm) {
			containProcessInstance = '/all';
		}
		$.post(
				ermContextPath + '/procDef/action/' + 'active' + containProcessInstance
				, {'procDefId' : procDefId}
				, function(reponse) {
					if (reponse.success){
						parent.$.messager.alert('提示', reponse.msg);
						window.location.href = ermContextPath + '/procDef/indexGrid';
					} else {
						parent.$.messager.alert('提示', reponse.errorMsg);
					}
				}
				, 'json'
		);
	});
}

function suspendProcessDefinition(procDefId) {
	parent.$.messager.confirm('Confirm', '<p>是否同时挂起下属所有流程实例?</p>(取消操作请关闭对话框)', function(confirm) {
		var containProcessInstance = '/this';
		if(confirm) {
			containProcessInstance = '/all';
		}
		$.post(
				ermContextPath + '/procDef/action/' + 'suspend' + containProcessInstance
				, {'procDefId' : procDefId}
				, function(reponse) {
					if (reponse.success){
						parent.$.messager.alert('提示', reponse.msg);
						window.location.href = ermContextPath + '/procDef/indexGrid';
					} else {
						parent.$.messager.alert('提示', reponse.errorMsg);
					}
				}
				, 'json'
		);
	});
}

function deleteProcessDeployment() {
	var row = $('#dg').datagrid('getSelected');
	if(row) {
		var deploymentId = row.deploymentId;
		
		parent.$.messager.confirm('Confirm', '<p>是否级联删除所有流程实例?</p>(取消操作请关闭对话框)', function(confirm) {
			var containProcessInstance = 'false';
			if(confirm) {
				containProcessInstance = 'true';
			}
			$.post(
					ermContextPath + '/procDef/deleteDeployment/?deploymentId=' + deploymentId + '&isCascade=' + containProcessInstance
					, function(reponse) {
						if (reponse.success){
							parent.$.messager.alert('提示', reponse.msg);
							window.location.href = ermContextPath + '/procDef/indexGrid';
						} else {
							parent.$.messager.alert('提示', reponse.errorMsg);
						}
					}
					, 'json'
			);
		});
	} else {
		parent.$.messager.alert('提示', "请选择要删除的数据！");
	}
}

function openProcessDeploymentDialog() {
	$('#ermProcessDeploymentDialog_id').dialog('open').dialog('setTitle', '上传流程定义文件并部署');
	$('#ermProcessDeploymentDialog_id').dialog('center');
	$('#ermProcessDeploymentDialog_id').form('clear');
}
