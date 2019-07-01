
function InstanceStatusHelper (status) {
	// guard clauses
	if(!status) {
		return '';
	}
	if(status == 1) {
		return '正常';
	}
	if(status == 0) {
		return '删除';
	}
	
	return '';
}

function ErmFileStatusHelper (status) {
	// guard clauses
	if(!status) {
		return '';
	}
	if(status == 100) {
		return '待归档';
	}
	if(status == 200) {
		return '已归档';
	}
	if(status == 300) {
		return '归档且已装盒';
	}
	
	return '';
}

function ErmFileBoxStatusHelper (status) {
	// guard clauses
	if(!status) {
		return '';
	}
	if(status == 100) {
		return '空盒';
	}
	if(status == 200) {
		return '已装盒';
	}
	if(status == 300) {
		return '已装且已封盒';
	}
	if(status == 400) {
		return '已上架';
	}
	
	return '';
}

function ErmContainerRackCellStatusHelper (status) {
	// guard clauses
	if(!status) {
		return '';
	}
	if(status == 0) {
		return '注销';
	}
	if(status == 1) {
		return '空格';
	}
	if(status == 2) {
		return '已装';
	}
	if(status == 3) {
		return '已满';
	}

	return '';
}

function ErmContainerRackCellSeatStatusHelper (status) {
	// guard clauses
	if(!status) {
		return '';
	}
	if(status == 0) {
		return '注销';
	}
	if(status == 1) {
		return '空格';
	} 
	if(status == 2) {
		return '已装';
	}
	
	return '';
}

function SfBinaryFileTypeHelper (binaryFileType) {
	// guard clauses
	if(!binaryFileType) {
		return '';
	}
	if(binaryFileType == 100) {
		return '原始文件';
	}
	if(binaryFileType == 200) {
		return '转换后预览文件';
	}
	if(binaryFileType == 300) {
		return '转换后加水印文件';
	}
	if(binaryFileType == 400) {
		return '转换后缩略图';
	}
	
	return '';
}

//----------------------------------------------//
//------------ Segmented Separator -------------//
//----------------------------------------------//

function InstanceStatusComboboxDataHelper () {
	return [
	        {
	        	value : 1,
	        	text : '正常'
	        }
	        , {
	        	value : 0,
	        	text : '删除'
	        }
	        ]
}

function ErmFileStatusComboboxDataHelper () {
	return [
	        {
	        	value : 100,
	        	text : '待归档'
	        }
	        , {
	        	value : 200,
	        	text : '已归档'
	        }
	        , {
	        	value : 300,
	        	text : '归档且已装盒'
	        }
	        ]
}

function ErmFileBoxStatusComboboxDataHelper () {
	return [
	        {
	        	value : 100,
	        	text : '空盒'
	        }
	        , {
	        	value : 200,
	        	text : '已装盒'
	        }
//	        , {
//	        	value : 300,
//	        	text : '已装且已封盒'
//	        }
	        , {
	        	value : 400,
	        	text : '已上架'
	        }
	        ]
}

function ErmContainerRackCellStatusComboboxDataHelper () {
	return [
	        {
	        	value : 0,
	        	text : '注销'
	        }
	        , {
	        	value : 1,
	        	text : '空格'
	        }
	        , {
	        	value : 2,
	        	text : '已装'
	        }
	        , {
	        	value : 3,
	        	text : '已满'
	        }
	        ]
}

function ErmContainerRackCellSeatStatusComboboxDataHelper () {
	return [
	        {
	        	value : 0,
	        	text : '注销'
	        }
	        , {
	        	value : 1,
	        	text : '空格'
	        }
	        , {
	        	value : 2,
	        	text : '已装'
	        }
	        ]
}

function SfBinaryFileTypeComboboxDataHelper () {
	return [
	        {
	        	value : 100,
	        	text : '原始文件'
	        }
	        , {
	        	value : 200,
	        	text : '转换后预览文件'
	        }
	        , {
	        	value : 300,
	        	text : '转换后加水印文件'
	        }
	        , {
	        	value : 400,
	        	text : '转换后缩略图'
	        }
	        ]
}
