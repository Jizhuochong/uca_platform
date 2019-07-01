/**
 * jQuery EasyUi
 * extend method of datagrid
 * for row draggable and droppable
 */

$
		.extend(
				$.fn.datagrid.methods,
				{
					rowMoving : function(jq, paramObject) {
						return jq
								.each(function() {
									var target = this;
									// datagrid所在的layout
									var targetLayout = $('#'
											+ paramObject.targetLayoutId);
									// 来源datagrid
									var fromDataGrid = $('#'
											+ paramObject.fromDataGridId);
									// 获得所有row对象, last用来避免取到rownumber列
									var cells = $(this)
											.datagrid('getPanel')
											.find(
													'div.datagrid-body:last tr.datagrid-row');

									cells
											.draggable({
												revert : false,
												cursor : 'pointer',
												edge : 5,
												proxy : function(source) {
													var p = $(
															'<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #ff0000"/>')
															.appendTo('body');
													p.html($(source).text());
													p.hide();
													return p;
												},
												onBeforeDrag : function(e) {
													e.data.startLeft = $(this)
															.offset().left;
													e.data.startTop = $(this)
															.offset().top;
												},
												onStartDrag : function() {
													$(this).draggable('proxy')
															.css({
																left : -10000,
																top : -10000
															});
												},
												onDrag : function(e) {
													$(this)
															.draggable('proxy')
															.show()
															.css(
																	{
																		left : e.pageX + 15,
																		top : e.pageY + 15
																	});
													return false;
												}
											});

									targetLayout
											.droppable({
												accept : 'tr.datagrid-row',
												onDragOver : function(e, source) {
													$(source)
															.draggable('proxy')
															.removeClass(
																	'tree-dnd-no')
															.addClass(
																	'tree-dnd-yes');
													$(this)
															.css('border-left',
																	'1px solid #ff0000');
												},
												onDragLeave : function(e,
														source) {
													$(source)
															.draggable('proxy')
															.removeClass(
																	'tree-dnd-yes')
															.addClass(
																	'tree-dnd-no');
													$(this).css('border-left',
															0);
												},
												onDrop : function(e, source) {
													$(this).css('border-left',
															0);

													var columns = $(source)
															.find('td');

													if (columns != null
															&& columns.length > 0) {
														var row = new Object();

														for (i = 0; i < columns.length; i++) {
															row[$(columns[i])
																	.attr(
																			'field')] = $(
																	columns[i])
																	.text();
														}

														// 是否drop,用于避免drop回原地造成错误
														var dropFlag = true;
														var originalRows = $(target).datagrid('getRows');
														for(j=0; j<originalRows.length; j++) {
															// 若drop方向为来源datagrid
															if(row.roleId == originalRows[j].roleId) {
																dropFlag = false;
															}
														}
														
														if(dropFlag) {
															// 目标grid中添加一行
															$(target).datagrid(
																	'appendRow',
																	row);
															
															$(target)
															.datagrid(
																	'rowMoving',
																	{
																		'targetLayoutId' : paramObject.targetLayoutId,
																		'fromDataGridId' : paramObject.fromDataGridId
																	});
															
															// 删除来源grid里的对应行
															$(
																	'#'
																	+ paramObject.fromDataGridId)
																	.datagrid(
																			'deleteRow',
																			$(source)[0].rowIndex);
														} else {
															// 若drop方向为来源datagrid,reload目标和来源datagrid
															$(target).datagrid('reload');
															fromDataGrid.datagrid('reload');
														}
													}
												}
											});
								});
					}
				});