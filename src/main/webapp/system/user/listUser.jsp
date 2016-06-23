<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>用戶信息</title>
</head>
<body onload="showUserData()">
	<h3>全部用戶信息如下</h3>
	
	<hr>
	<button id="loadGrid" >加载数据到Table</button>
	<hr>
	
	<table id="userList" width="510" border="100" cellSpacing=1 style="border: 1pt dashed; font-size: 15pt;" height="31">
		<thead>
			<tr>
			    <td>id</td>
				<td>用戶名</td>
				<td>密碼</td>
				<td>年齡</td>
				<td>性别</td>
				<th>操作</th>
			</tr>
	 	</thead>
		<tbody id="tb">
		<tr>
			    <td>id</td>
				<td>用戶名</td>
				<td>密碼</td>
				<td>年齡</td>
				<td>性别</td>
				<th>操作</th>
			</tr>
		</tbody>
	</table>
	<br>
	用户名:<input name="username" id="username" onblur="showUserData();"/>
	<br>
	<h3>
		<a href=putin.jsp>返回信息输入页面</a>
	</h3>
	<br>
	
	
	
	
	
	<script type="text/javascript">

	/* window.onload = function(){
		
		//showUserData();
		
		var button = document.getElementById("loadGrid");
		button.onclick = function(){
			var userListTable = document.getElementById("userList");
			var tbody = document.getElementById("userList").getElementsByTagName("tbody")[0];
			tbody.innerHTML="111111";
			alert(tbody.id);
			
		};
	} */

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

	    
    function confirmdel(){
       if(window.confirm("您确定要删除此条信息？")){
       return true;
       }
       else{
     //  alert("取消删除！");
       return false;
       }      
    }
    
    function showUserData(){
    	var xhr = getXhr();
    	/* alert("xuan111111111111"); */
    	xhr.open('GET','listUser.do?t='+ Math.random(),true);
    	xhr.onreadystatechange=function(){
			if(xhr.readyState == 4){
				if(xhr.status == 200){
					var txt = xhr.responseText;
					var jsonobj=eval('('+txt+')'); 
					/* alert($('userList').getElementsByTagName("tbody")); */
					var arr = new Array();
					for(var i=0; i<jsonobj.length;i++){
						arr.push('<tr>'); 
						arr.push('<td>' +jsonobj[i].id+ '</td>');  
						arr.push('<td>' +jsonobj[i].username+ '</td>');  
						arr.push('<td>' +jsonobj[i].password+ '</td>');  
						arr.push('<td>' +jsonobj[i].age+ '</td>');  
						arr.push('<td>' +jsonobj[i].gender+ '</td>');  
						arr.push('<td><a href=\"edituser.do?id='+jsonobj[i].id+'\">编辑</a>|<a href=\"deleteuser.do?id='+jsonobj[i].id+'\" onclick=\"return confirmdel()\">删除</a></td>');  
						arr.push('</tr>');
					}
					$('tb').innerHTML = arr.join(''); 
				}else{
					$('tb').innerHTML =  '加载失败！';
				}
			}
		};
		$('tb').innerHTML = '正在加载...';
		xhr.send(null);
    }
    
 </script>
</body>
</html>
