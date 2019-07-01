$.widget("ui.table", {
	// 默认的参数
	options: {
		autowidth:true,
		filterQuery:false,
		info:true,
		jqueryui:true,
		lengthchange:true,
		paginate:true,
		paginationtype:"full_numbers",
		processing:true,
		displaylength:10,
		sort:true,
		sortclasses:false,
		ajaxdataprop:"aaData",
		ajaxsource:null,
		scrolly:"100%",// "450px",
		scrollx:"100%",
		scrollxInner:"100%",
		scrollinfinite:false,
		serverside:true,
		servermethod:"POST",
		aoColumns:null,
		aoColumnDefs:null,
		processtext:'正在加载......',
		scrollcollapse:false,
		lengthMenu:[[10, 25, 50, 100], [10, 25, 50, 100]],
		sorting:[]
	},
	_init: function() {
		if(this.options.ajaxsource==null)
			{
				this.options.processing=false;
				this.options.serverside=false;
			}
		var self = this,op = self.options,ele=this.element;			
			ele.dataTable({
			"oLanguage": {
				"sLengthMenu": "每页显示_MENU_条记录",
				"sZeroRecords": " ",
				"sInfo": "第 _START_ 条到 _END_ 条，总共 _TOTAL_ 条",
				"sInfoEmpty":"找不到相关数据", // "Showing 0 to 0 of 0 records",
				"sInfoFiltered": "数据表中共为_MAX_条记录",
				"sSearch":"搜索",
				"oPaginate": {
					"sFirst": "首页",
					"sLast": "末页",
					"sNext": "下一页",
					"sPrevious": "上一页"
				 },
				"sProcessing":op.processtext
			},
			"bSortClasses": op.sortclasses,
			"bJQueryUI": op.jqueryui,
			"sPaginationType": op.paginationtype,
			"bScrollInfinite":op.scrollinfinite,
			"bSort":op.sort,
			"bAutoWidth":op.autowidth,
			"bFilter":op.filterQuery,
			"bInfo":op.info,
			"iDisplayLength":op.displaylength,
			"bLengthChange":op.lengthchange,
			"bPaginate":op.paginate,
			"sAjaxDataProp":op.ajaxdataprop,
			"sAjaxSource":op.ajaxsource,
			"sScrollY": op.scrolly,
			"sScrollX": op.scrollx,
			"sScrollXInner": op.scrollxInner,
			"bProcessing":op.processing,
			"aoColumnDefs":[{"bVisible":false,"aTargets":[op.aoColumnDefs]}],// 0,1,2隐藏某列
			"bServerSide": op.serverside,
			"bScrollCollapse": true,
			"fnServerData":this._queryData,
			"sServerMethod":op.servermethod,
			"aoColumns": op.aoColumns,
			"bScrollCollapse": op.scrollcollapse,
			"aaSorting":op.sorting,
			"fnDrawCallback":this._drawCallBack,
			"aLengthMenu":op.lengthMenu
//			"fnInitComplete" :this._initComplete
			
		});
		return this;
	},
	/**
	 * 首列添加复选框
	 */
	_drawCallBack:function(){
		$(".check_all").prop("checked", false);
		/*var _this = this;
		//table被draw完后调用
		if(_this.find(":checkbox").length>0){
			$.each(_this.find(":checkbox"), function (i, n) {
		        	$(this).hide();//隐藏当前复选框 //after在每个匹配的元素之后插入内容 before在每个匹配的元素之前插入内容
		        	var t = $("<span class='checkbox0'></span>"); //构建模拟的复选框
		        	if ($(this).attr("checked") == true || $(this).attr("checked") == "checked") { //判断当前复选框的选中状态
		        		t.addClass("checkboxcheck"); //选中状态的背景
		        	}
		        	else {
		        		t.addClass("checkbox"); //选中状态的背景
		        	}
		        	var $this = $(this);
		        	t.click(function (event) { //注册点击选中事件
		        		if ($(this).hasClass("checkbox")) { //选中状态则更改为非选中
		        			$this.attr("checked", true);
		        			$(this).removeClass("checkbox").addClass("checkboxcheck").closest("tr").addClass("dataTables_scrollBody_tr_selected");
		        		}
		        		else {
		        			$this.attr("checked", false);
		        			$(this).removeClass("checkboxcheck").addClass("checkbox").closest("tr").removeClass("dataTables_scrollBody_tr_selected");
		        		}
		        		event.stopPropagation();
		        	});
		        	$(this).after(t); //将模拟的复选框插入到当前复选框的后面
		    });
		}else{
			
		}*/
	},
	/*_initComplete : function(){
		var _this = this;
		
		if(_this.find(":checkbox").length>0){
			_this.find("tbody tr").live("click",function() {
				if ($(this).find("input").attr("checked") == true || $(this).find("input").attr("checked") == "checked") { // 判断当前复选框的选中状态
					$(this).removeClass("dataTables_scrollBody_tr_selected");
					$(this).find("input").attr("checked", false).next(".checkbox0").removeClass("checkboxcheck").addClass("checkbox");
		        }else{
				$(this).addClass("dataTables_scrollBody_tr_selected");
				$(this).find("input").attr("checked", true).next(".checkbox0").removeClass("checkbox").addClass("checkboxcheck");
		        }
			});
		}else{
			_this.find("tbody tr").live("click",function() {
				_this.find("tbody tr").removeClass("dataTables_scrollBody_tr_selected");
				$(this).addClass("dataTables_scrollBody_tr_selected");
			});
		}
	},*/
	_queryData:function(sSource, aoData, fnCallback,oSettings)
	{
		// 将客户名称加入参数数组
		var params;
		if(oSettings.params)
		{
			for(var i=0;i<oSettings.params.length;i++){
				aoData.push(oSettings.params[i]);
			}
			var sourceUrlName=oSettings.params[oSettings.params.length-1].name;
			var sourceUrl=oSettings.params[oSettings.params.length-1].value;
			if(sourceUrl && sourceUrl != "" && sourceUrlName && sourceUrlName=="parmUrl"){
				sSource=sourceUrl;
			}
		}
		$.ajax( {
			"type": "GET",
			"contentType": "application/json",
			"url": sSource+"?r="+Math.random(),
			"dataType": "json",
			"cache":false,
			"data": aoData,// params, //以json格式传递
			"success": function(resp) {
				fnCallback(resp); // 服务器端
			}
		});
	},
	queryData:function(param)
	{
		var oSettings=this.element.dataTableSettings[0];
		oSettings._iDisplayStart = 0;
		oSettings.params=param;
		this.element._fnDraw(oSettings);
	},
	getSelectedRow:function()
	{
		var rows=this.element.$('tr.dataTables_scrollBody_tr_selected');
		var selectRows=new Array();
		if(rows)
			{
				for(var i=0;i<rows.length;i++)
					{
						var dataIndex =rows[i]._DT_RowIndex;
						var oData = this.element.dataTableSettings[0].aoData[dataIndex];
						selectRows.push(oData._aData);
					}
			}
		return selectRows;
	}
});

/**
 * 弹出窗口显示记录的详细信息
 * 
 * @param tableId:
 *            表格的id
 * @param formId:
 *            Form的Id
 * @param dialogId:
 *            弹出dialog的Id
 * @param dialogWidth:
 *            弹出dialog的宽度
 * @param dialogHeight:
 *            弹出dialog的高度
 */
function createUpdateModalDialog(tableId,formId,dialogId,formSuffix,dialogWidth,dialogHeight)
{
	if (!dialogWidth && typeof(dialogWidth)!="number") dialogWidth=1280;
	if (!dialogHeight && typeof(dialogHeight)!="number") dialogHeight=600;
	if(tableId&&tableId.length>0 && formId&& formId.length>0&&
			formSuffix && formSuffix.length>0&& dialogId&& dialogId.length>0){
		var form=$("#"+formId);
		form[0].reset();
		// 获取选中的行
		var rows=$("#"+tableId).table("getSelectedRow");
		if(rows && rows[0] && rows.length==1){
		// 将选中的行的json数据绑定到弹出窗口的表单，由于担心出现重复命名所以采用在属性名称后增加后缀方式区分
			bindForm(formId,rows[0],formSuffix);
			$.fn.modalDialog({
			type : "normal",
			id : dialogId,
			width : dialogWidth, /* 窗口宽度 */
			height : dialogHeight, /* 窗口高度 */
			border : "1px solid #444444"
			});
		}else{
			$(this).message({
				type:'alert',
				text: '有且只能选择一条记录！',
				icontype:'warning',
				title: '提示',
				width:400,
				height:300,
				yesfn: function(){
					void(0);
				}
			});
		}
	}
}
/**
 * 添加窗口
 */
function createAddModalDialog(formId,dialogId,dialogWidth,dialogHeight)
{
	if (!dialogWidth && typeof(dialogWidth)!="number") dialogWidth=1280;
	if (!dialogHeight && typeof(dialogHeight)!="number") dialogHeight=600;
	if(formId&&formId.length>0&& dialogId&& dialogId.length>0){
		var form=$("#"+formId);
		form[0].reset();
		$.fn.modalDialog({
			type : "normal",
			id : dialogId,
			width : dialogWidth, /* 窗口宽度 */
			height : dialogHeight, /* 窗口高度 */
			border : "1px solid #444444"
		});
	}
}