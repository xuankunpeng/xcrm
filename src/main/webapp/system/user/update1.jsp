<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.xcrm.system.user.entity.User"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户信息修改</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<br>
	<h2>用户信息</h2>
	<hr>
	<br>
	<h3>要修改的用户信息如下</h3>
	<table width="496" border="100" cellSpacing=1
		style="border: 1pt dashed; font-size: 15pt;" height="31">
		<tr>
			<td>id</td>
			<td>用户名</td>
			<td>密码</td>
			<td>年龄</td>
			<td>性别</td>
		</tr>
		<%
    int id=0;
       /* ArrayList<User> result=new ArrayList<User>(); */
        User st=(User)request.getAttribute("result");
            id=st.getId();
            out.print("<tr>");
            out.print("<td>"+st.getId()+"</td>");
            out.print("<td>"+st.getUsername()+"</td>");
            out.print("<td>"+st.getPassword()+"</td>"); 
            out.print("<td>"+st.getAge()+"</td>");
            out.print("<td>"+st.getGender()+"</td>");
            out.print("</tr>");
     %>
	</table>
	<h3>将学生信息更改为：</h3>
	<form action="updateUser.do" method="post">
		<input type="hidden" name="methodName" value="3" />
		<h4>
			id：<input type="text" name="id" value="<%=id%>" title="id不能改变"></input><br>
		</h4>
		<h4>
			用户名：<input type="text" name="username" title="用户名不能为空"></input><br>
		</h4>
		<h4>
			密码：<input type="text" name="password" title="密码不能为空"></input><br>
		</h4>
		<h4>
			年龄：<input type="text" name="age" title="年龄不能为空"></input><br>
		</h4>
		<h4>
			年龄：<input type="radio" name="gender" value="男">男 <input
				type="radio" name="gender" value="女">女<br>
		</h4>
		<input type="submit" value="修改" />
	</form>

	<br>
	<h3>
		<a href=<%=basePath%>index.jsp>返回信息输入页面</a>
	</h3>
	<h3>
		<a href=<%=basePath%>/system/user/listUserJqury.jsp>返回信息查询页面</a>
	</h3>
</body>

</html>
