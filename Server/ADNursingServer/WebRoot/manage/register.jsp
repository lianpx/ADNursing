<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>注册界面</title>

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
	<div align="center">
		<h2>欢迎您，请注册账号</h2>
		<form action="${pageContext.request.contextPath}/servlet/RegisterServlet?mode=web" method="post">
			账号:<input type="text" name="userName" /><br/> 
			密码:<input type="password" name="userPassword" /><br/> 
			<input type="submit" value="注册" />
		</form>
	</div>
  </body>
</html>
