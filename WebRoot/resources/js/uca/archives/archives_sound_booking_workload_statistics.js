function toSearch() {
	window.location.href=rfPath + '/workStatistics/soundBookingWorkload?sd='+
			$('#sd').datebox('getValue')+'&ed='+$('#ed').datebox('getValue')+
			'&status='+$("#type_search_jsid").combobox('getValue');
}

function clearForm() {
	$("#type_search_jsid").combobox('clear');
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}
