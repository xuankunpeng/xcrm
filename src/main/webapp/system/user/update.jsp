<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.xcrm.system.user.entity.User" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改成功</title>
    
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
    
    <h3>修改后的信息为：</h3>
    
    <hr>
    <br>
    <br>
<table width="450" border="100" cellSpacing=1 style="font-size:15pt;border:dashed 1pt">
    <tr>
    <td>id</td>
    <td>用户名</td>
    <td>密码</td>
    <td>年龄</td>
    <td>性别</td>
    </tr>
    <%
     response.setCharacterEncoding("UTF-8");
     request.setCharacterEncoding("UTF-8");
       User user=(User)request.getAttribute("result");
            out.print("<tr>");
            out.print("<td>"+user.getId()+"</td>");
            out.print("<td>"+user.getUsername()+"</td>");
            out.print("<td>"+user.getPassword()+"</td>"); 
            out.print("<td>"+user.getAge()+"</td>");
            out.print("<td>"+user.getGender()+"</td>");
            out.print("</tr>");
     %>
          </table>
      <br>
      <br>
      <h3><a href=<%=basePath%>/index.jsp>返回信息输入页面</a></h3>
      <h3><a href=<%=basePath%>/system/user/listUserJqury.jsp>返回信息查询页面</a></h3>
  </body>
</html>
