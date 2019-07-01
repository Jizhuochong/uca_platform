$(document).ready(function(){
	$('#qDate').datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
        	span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10) + 1; //月份
                    $('#qDate').datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
        },
        parser: function (s) {//配置parser，返回选择的日期
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: formatDate//配置formatter，只返回年月
    });
    var p = $('#qDate').datebox('panel'), //日期选择对象
        tds = false, //日期选择对象中月份
        span = p.find('span.calendar-text'); //显示月份层的触发控件
	/*$("#qDate").datebox({
		formatter : formatDate
	});*/
	
	function formatDate(date) {
		var month = (date.getMonth() + 1);
		if(month < 10){
			month = "0" + month;
		}
		return date.getFullYear() + "-" + month;
	}
	toSearch();
});

function clearForm() {
	$('#qDate').datebox('clear');
}

function setTitle(qDate){
	if(qDate != null){
		var dateArr = qDate.split("-");
		$('#dutyYear').text(dateArr[0]);
		$('#dutyMonth').text(dateArr[1]);
	}else{
		var d = new Date();
		$('#dutyYear').text(d.getFullYear());
		$('#dutyMonth').text(d.getMonth() + 1);
	}
}

function toSearch() {
	var dataObj = null;
	var qDate = $('#qDate').datebox('getValue');
	
	if(qDate != ''){
		dataObj = {qDate : qDate};
		setTitle(qDate);
	}else{
		dataObj = {};
		setTitle(null);
	}
	
	
	$("#list_data").html("");
	$.ajax({
	    url: rfPath + '/duty_user/list',
	    type: 'POST',
	    dataType: 'json',
	    data: dataObj,
	    success: function(data){
	    	if(data.success) {
	    		var map = data.map;
	    		var trObj = '<tr align="center"><td width="10%">日&nbsp;&nbsp;期</td><td width="10%">农历</td><td width="5%">星期</td><td width="25%">白班<p>(8:00-20:00)</p></td><td width="25%">夜班<p>(20:00-8:00)</p></td><td width="25%">带班领导</td><tr>';
	    		$(trObj).appendTo($("#list_data"));
	    		var rows = 0;
	    		$.each(map, function(key,values){
	    			var ind = 0;
	    			rows = values.length;
	    			$(values).each(function(){
	    				ind++;
	    				trObj = buildTd(key,this,ind,rows);
	    				$(trObj).appendTo($("#list_data"));
	    			});
	    		});
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function buildTd(key, voObj, ind, rows){
	var trObj = '<tr align="center"><td>'+(voObj.dutyDate!=null?voObj.dutyDate:"")+'</td><td>'+(voObj.lunarCalendar==null?"":voObj.lunarCalendar)+'</td><td>'+(voObj.week!=null?voObj.week:"")+'</td><td>'+(voObj.dayShiftUser!=null?voObj.dayShiftUser:"")+'</td><td>'+(voObj.nightShiftUser!=null?voObj.nightShiftUser:"")+'</td>';
	var keyArr = key.split(",");
	if(keyArr.length == 2){
		if(ind == 1){
			trObj += '<td rowspan="'+rows+'">'+key.substring(key.indexOf(",")+1)+'</td>';
		}
	}else{
		trObj += '<td>'+voObj.leader+'</td>';
	}
	
	trObj += '</tr>';
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