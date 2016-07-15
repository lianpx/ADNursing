<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
	昵称：${user.username}<br>
	<a href="${pageContext.request.contextPath}/servlet/ViewOwnerPostServlet?mode=web&op=owner">我的文章</a><br/><br/>
	<a href="${pageContext.request.contextPath}/servlet/ViewOwnerCommentServlet?mode=web&op=owner">我的评论</a><br/><br/>
	<a href="${pageContext.request.contextPath}/servlet/ViewOwnerFavoritesServlet?mode=web">我的收藏</a><br/><br/>
</html>