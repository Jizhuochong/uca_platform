function clearForm() {
	$('#user_id_select_jsid').combobox('clear');
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}

function setTitle(date){
	if(date != null){
		$('#dutysd_ed').text(date);
	}else{
		$('#dutysd_ed').text("");
	}
}

function toSearch() {
	var user_id_select_jsid = $('#user_id_select_jsid').combobox('getValue');
	var sd_jsid = $('#sd').datebox('getValue'),ed_jsid = $('#ed').datebox('getValue');
	if(sd_jsid != '' && ed_jsid != ''){
		setTitle(sd_jsid+"至"+ed_jsid);
	} else {
		if(sd_jsid != '' ){
			setTitle(sd_jsid);
		} else if(ed_jsid != '' ){
			setTitle(ed_jsid);
		} else {
			setTitle(null);
		}
	}
	
	$("#list_data").html("");
	$.ajax({
	    url: rfPath + '/auditWorkStatistics/listIndex',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	userId : user_id_select_jsid,
	    	sd : sd_jsid,
	    	ed : ed_jsid
	    },
	    success: function(data){
	    	if(data.success) {
	    		$('#totalCount').text(data.totalCount);
	    		$('#allPicNum').text(data.allPicNum);
	    		$('#allMovMinute').text(data.allMovMinute);
	    		var rowsData = data.rows;
	    		var trObj = '<tr align="center"><td width="20%"><strong>工程名称</strong></td><td width="20%"><strong>办理人员姓名</strong></td><td width="15%"><strong>相片数量(张)</strong></td><td width="15%"><strong>相片容量(GB)</strong></td><td width="15%"><strong>视频时间(分钟)</strong></td><td width="15%"><strong>视频容量(GB)</strong></td><tr>';
	    		$(trObj).appendTo($("#list_data"));
	    		$.each(rowsData, function(index, row){
    				trObj = buildTd(row);
    				$(trObj).appendTo($("#list_data"));
	    		});
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function buildTd(row){
	var trObj = '<tr align="center"><td>'+(row.projectNameTotal!=null?row.projectNameTotal:"")+'</td><td>'+(row.userName==null?"":row.userName)+'</td><td>'+row.picNumTotal+'</td><td>'+row.picSizeTotal+'</td><td>'+row.movMinuteTotal+'</td><td>'+row.movSizeTotal+'</td></tr>';
	return trObj;
}

function nowprint(oper) {
	if (oper < 10){
		bdhtml=window.document.body.innerHTML;//获取当前页的html代码
		sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域
		eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域
		prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html
		prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html
		var iframe = document.createElement('iframe');
		iframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
		document.body.appendChild(iframe);
		var doc = iframe.contentWindow.document;
		// 引入打印的专有CSS样式根据实际修改
//		doc.write('<LINK rel="stylesheet" type="text/css" href="css/print.css">');
		doc.write('<div>' + prnhtml + '</div>');
		doc.close();
		iframe.contentWindow.focus();
		iframe.contentWindow.print();
		if (navigator.userAgent.indexOf("MSIE") > 0){
		     document.body.removeChild(iframe);
		}
	}else{
		window.print();
	}
}