// 条形码及二维码地址, 与配置文件中地址对应
function RelativeBarcodeFilePathHelper( moduleName ) {
	return '/resources/workspace/barcode/' + moduleName + '/';
}

function RelativeQrcodeFilePathHelper( moduleName ) {
	return '/resources/workspace/qrcode/' + moduleName + '/';
}