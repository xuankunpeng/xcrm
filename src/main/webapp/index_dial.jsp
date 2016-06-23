<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户信息输入</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- http://10.11.3.14:8080/iface/http/rest/iface/1.8/dial?params={'srcnum':'8001','dstnum':'8002'} -->
	<script src="bs/system/user/jquery-2.2.4.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$("#dial").on("click",function(){
			showUserData();
		});
	});
	
	function showUserData(){
 		/* var dialUrl = "http://192.168.1.187:8087/iface/http/rest/iface/1.8/dial?"; */
		var dialUrl = "http://192.168.1.187:8080/iface/http/rest/iface/1.8/dial?"; 
		var params = "params={'srcnum':'"+60080+"','dstnum':'"+18017058409+"'}";
    	$.ajax({
		        type : 'GET',
				url : dialUrl, 
				data: params,
				cache:false,
				dataType:"jsonp",
				jsonpCallback:"callback",
				success : function(data) {
					
					/* alert(data); */
					alert(data.code+data.msg);
					/* alert("Record saved successfully in database"); */
				},
				error : function() {
					alert("异常！！！！");
				}
			});
		}
	
    </script>
  </head>
  
  <body>
     <br>
  <center>
  <h2>用户信息输入</h2><hr>

 	<button id="dial" >加载数据到Table</button>
  <br>
  <a href="addServlet.do?methodName=list&id=<%="" %>&name=<%="" %>">查看已输入信息</a>
  </center>
  </body>
</html>
