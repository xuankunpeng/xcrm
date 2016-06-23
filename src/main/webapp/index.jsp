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
	<script src="bs/system/user/jquery-2.2.4.min.js" type="text/javascript"></script>
  </head>
  
  <body>
     <br>
  <center>
  <h2>用户信息输入</h2><hr>
 <form  method="post" id="myform" onsubmit="validate()" >
    <!-- <input type="hidden" name="methodName" value="add"/> -->
	<h4>  用戶名：<input type="text" name="username" class="{required:true}" title="用户名不能为空" id="username_of_user"></input><br></h4>
	<h4>  密碼：<input type="text" name="password"title="密码不能为空"></input><br></h4>
	<h4>  年龄：<input type="text" name="age"title="年龄必须为数字" id="age_of_user"></input><br></h4>
	<h4>  性别：<input type="radio" name="gender" value="男" >男
            <input type="radio" name="gender" value="女">女<br></h4>
 	<input type="button" id="somebutton" value="提交"/>
  </form>
  <br>
  <a href="addServlet.do?methodName=list&id=<%="" %>&name=<%="" %>">查看已输入信息</a>
  </center>
  
  <script type="text/javascript">
  
  $("#somebutton11").click(function(){
	  $.ajax({
			type: "POST",
			url: "addUser.do?t="+ Math.random(),
			data: $('#myform').serialize(),
			cache:false,
			dataType:"text",
			success: function (data) {
				window.location.href = "<%=basePath%>system/user/listUserJqury.jsp"; 
				console.log("success");
			},
			error: function(request) {
				alert("Connection error"); // Optional
   		}
		});
	  
  });
   
	  $("#somebutton").on("click",function(){
		     $.ajax({
				type: "POST",
				url: "addUser.do?t="+ Math.random(),
				data: $('#myform').serialize(),
				cache:false,
				dataType:"text",
				success: function (data) {
					window.location.href = "<%=basePath%>system/user/listUserJqury.jsp";
					console.log("success");
					},
				error : function(request) {
					alert("Connection error"); // Optional
					}
					});
			});

			function validate() {
				var username = document.forms[0].username.value;
				var password = document.forms[0].password.value;
				var age = document.forms[0].age.value;
				if (username.length <= 0) {
					alert("用户名不能为空，请输入用户名！");
					return false;
				} else if (password.length <= 0) {
					alert("密码不能为空，请输入密码！");
					return false;
				} else if (age <= 0) {
					alert("请输入合法年龄！");
					return false;
				}

				else {
					return true;
				}
			}
		</script>
    
  </body>
</html>
