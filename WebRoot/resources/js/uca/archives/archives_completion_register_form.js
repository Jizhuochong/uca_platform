function nowprint(type,id) {
	/*if (11 < 10){
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
	}else{*/
//		window.print();
	var url = rfPath +'/registerForm/down?id='+id+'&type='+type;
	window.open(url);
//	alert(111);
//		$.ajax({
//		    url: rfPath + '/registerForm/down?id='+id+'&type='+type,
//		    type: 'POST',
//		    dataType: 'json',
////		    data: {
////		    	id : id,
////		    	type : type
////		    },
//		    success: function(data){
//		    	if(data.success) {
//		    		parent.$.messager.alert('提示', data.msg);
////		    		$('#list_data').datagrid('reload');
////		    		$('#A_dialog').dialog('close');
//		    	} else {
//		    		parent.$.messager.alert('提示', data.msg);
//		    	}
//		    },
//		    error: function(){
//		    	parent.$.messager.alert('提示', "添加失败，请重试！");
//		    }
//		});
//	}
}
