var ermContextPath = this.rfPath;

function checkProcessResourceFileFormat(file) {
	if (/.(xml|zip|bar|bpmn|bpmn20.xml)$/.test(file)) {
		return true;
	}
	
	return false;
}

function processDeployFromFile(file) {
var file = $("#processResourceFileInput_id").val();
	
	if ("" == file) {
		alert("请选择您要部署的工作流程定义文件");
		return false;
	}
	
	if (!checkProcessResourceFileFormat(file)) {
		alert("暂不支持部署此后缀的工作流程定义文件！");
		return false;
	}
	
	$('#processDeployForm_id').form(
		'submit'
		,{
	        url: ermContextPath + '/procDef/deployFromFile',
	        success: function(reponse){
	            var dataObj = eval('(' + reponse + ')');
	            if (dataObj.success){
	            	parent.$.messager.alert('提示', '流程定义部署成功！');
	            	window.location.href = ermContextPath + '/procDef/indexGrid';
	            } else {
	            	parent.$.messager.alert('提示', dataObj.errorMsg);
	            }
	        }
		}
	);
	
	return false;
}