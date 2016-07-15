<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'header.jsp' starting page</title>

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
    This is my JSP page. <br>
    <a href="${pageContext.request.contextPath}/manage/addPost.jsp">添加帖子</a>
    <a href="${pageContext.request.contextPath}/manage/addComment.jsp">添加评论</a>
    <a href="${pageContext.request.contextPath}/manage/addTest.jsp">添加测试结果</a>
    <a href="${pageContext.request.contextPath}/manage/updateTestInfor.jsp">更新测试者信息</a>
    <a href="${pageContext.request.contextPath}/manage/updateUser.jsp">更新个人信息</a>

