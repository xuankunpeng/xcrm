<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<title>用戶信息-----</title>
	<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="bs/system/user/jquery-2.2.4.min.js" type="text/javascript"></script>
<style type="text/css">
    #divTb
    {
      width:800px;
      border:1px solid #aaa;
      margin:0 auto;
    }
    .even{background:#CCCCCC;}
    .odd{background:#FFFFFF;}
  </style>
</head>
<body onload="showUserData()">
	<h3>全部用戶信息如下</h3>
	<button id="loadGrid" >加载数据到Table</button>
	<hr>
	<br>
	用户名:<input name="username" id="username" onblur="showUserData();"/>
	<br>
	<br>
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
		</tbody>
	</table>
	<h3>
		<a href=index.jsp>返回信息输入页面</a>
	</h3>
	<br>
	
	<script type="text/javascript">

	$(function(){
		  $("#loadGrid").on("click",
			  showUserData
		  );
	});
	
    function showUserData(){
    	$.ajax({
		        type : 'POST',
				url : 'getUsers.do?t=' + Math.random(), 
				dataType : 'json',
				success : function(data) {
					  $.each(data,function(i,user){
					    var tbBody = "";
			            var trColor;
					    if (i % 2 == 0) {
				              trColor = "even";
				            }
				        else {
				              trColor = "odd";
				            }
					    tbBody += "<tr class='" + trColor + "'>"+
						              "<td>" + user.id + "</td>" + 
						              "<td>" + user.username + "</td>" + 
						              "<td>" + user.password + "</td>" +
						              "<td>" + user.age + "</td>" +
						              "<td>" + user.gender + "</td>"+
						              "<td><a href=\'edituser.do?id=" + user['id'] +"\'>编辑</a>|<a href=\'deleteuser.do?id="+user['id'] +"\' onclick=\'return confirmdel()\'>删除22</a></td>"+
					              
					              "</tr>"; 
					    $("#tb").append(tbBody);
					  });  
				},
				error : function() {
					alert("异常！！！！");
				}
			});
		}
    
    function drawTable(data) {
        for (var i = 0; i < data.length; i++) {
            drawRow(data[i]);
            
        }
    }
    
    function drawRow(rowData) {
        var row = $("<tr />")
        $("#tb").append(row);
        row.append($("<td>" + rowData.id + "</td>"));
        row.append($("<td>" + rowData.username + "</td>"));
        row.append($("<td>" + rowData.password + "</td>"));
        row.append($("<td>" + rowData.age + "</td>"));
        row.append($("<td>" + rowData.gender + "</td>"));
    }
 	//end :Render json in table using jQuery  
  	function confirmdel(){
       if(window.confirm("您确定要删除此条信息？")){
       return true;
       }
       else{
     //  alert("取消删除！");
       return false;
       }      
    }
	</script>
</body>
</html>
