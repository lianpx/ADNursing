<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
<table width="80%" align="center" cellpadding="0" cellspacing="1" >
	<tr>
		<td colspan="4" >文章</td> 
	</tr>
	<tr  height="30">
		<th>文章标题</th>
		<th>文章内容</th>
		<th>发布时间</th>
		<th>发布者</th>	
		<th>评论</th>
		<th>文章种类</th>	
		<th>图片</th>
	</tr>
	<c:forEach items="${posts}" var="i" varStatus="vs">
		<tr height="24" class="${vs.index%2==0?'odd':'even' }">
			<td nowrap="nowrap"><a href="${pageContext.request.contextPath}/servlet/ViewOnePostServlet?mode=web&op=owner&postId=${i.post_id}">${i.post_title}</a></td>
			<td nowrap="nowrap">${i.post_text}</td>
			<td nowrap="nowrap" name="postDate">${i.post_date}</td>
			<td nowrap="nowrap" name="owner">${i.owner}</td>
			<td nowrap="nowrap" name="thx"><a href="${pageContext.request.contextPath}/servlet/ViewPostCommentServlet?mode=web&postId=${i.post_id}">${i.comment_num}</a></td>
			<td nowrap="nowrap" name="postKind">${i.post_kind}</td>
			<td nowrap="nowrap" name="img_url">${i.img_url}</td>
			<td nowrap="nowrap" ><a href="${pageContext.request.contextPath}/servlet/DelPostServlet?mode=web&postId=${i.post_id}">删除</a></td>
		</tr>
	</c:forEach>
</table>
  </body>
</html>