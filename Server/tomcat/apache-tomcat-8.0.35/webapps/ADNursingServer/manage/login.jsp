<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登陆界面</title>

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
		<h2>欢迎您，使用资源库</h2>
		<form action="${pageContext.request.contextPath}/servlet/LoginServlet?mode=web" method="post">
			用户账号:<input type="text" name="username" /><br/> 
			用户密码:<input type="password" name="password" /><br/> 
			<input type="submit" value="登录" />
		</form>
		<a href="${pageContext.request.contextPath}/manage/register.jsp">注册</a>
	</div>
  </body>
</html>
