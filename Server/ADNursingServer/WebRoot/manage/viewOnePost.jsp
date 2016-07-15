<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
	文章标题：${post.post_title}<br>
	文章内容:${post.post_text}<br>
	发布时间:${post.post_date}<br>
	发布者:${post.owner}<br>
	评论:${post.comment_num}<br>
	种类:${post.post_kind}<br>
	图片:${post.img_url}<br>
	<a href="${pageContext.request.contextPath}/servlet/AddFavoritesServlet?mode=web&postId=${post.post_id}">收藏</a><br/><br/>
	<a href="${pageContext.request.contextPath}/manage/addComment.jsp">评论</a><br/><br/>
    
</html>
