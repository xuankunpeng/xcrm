//依据id,返回dom节点
function $(id){
	return document.getElementById(id);
}
//返回id对应的dom节点的value属性值
function $F(id){
	return $(id).value;
}

//获得ajax对象
function getXhr() {
	var xhr = null;
	if (window.XMLHttpRequest) {
					//非ie浏览器
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHttp");
	}
	return xhr;
}