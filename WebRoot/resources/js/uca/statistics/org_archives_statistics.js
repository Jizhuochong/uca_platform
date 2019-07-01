function toSearch() {
	if($('#sd').datebox('getValue') == null || 
			$('#sd').datebox('getValue').length == 0 || $('#sd').datebox('getValue') == ""){
		parent.$.messager.alert('提示', "请输入开始时间！");
		return;
	}
	if($('#ed').datebox('getValue') == null || 
			$('#ed').datebox('getValue').length == 0 || $('#ed').datebox('getValue') == ""){
		parent.$.messager.alert('提示', "请输入结束时间！");
		return;
	}
	window.location.href=rfPath + '/orgarchivesstatistics/statistics?sd='+
			$('#sd').datebox('getValue')+'&ed='+$('#ed').datebox('getValue');
}

function clearForm() {
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}