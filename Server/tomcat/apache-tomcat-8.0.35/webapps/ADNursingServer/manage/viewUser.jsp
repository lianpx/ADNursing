<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
	昵称：${user.username}<br>
	<a href="${pageContext.request.contextPath}/servlet/ViewOwnerPostServlet?mode=web&op=visitor&owner=${user.username}">他的文章</a><br/><br/>
  </body>
</html>