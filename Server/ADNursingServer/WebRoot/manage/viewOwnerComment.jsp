<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
<table width="80%" align="center" cellpadding="0" cellspacing="1" >
	<tr>
		<td colspan="4" >评论</td> 
	</tr>
	<tr  height="30">
		<th>发布者</th>
		<th>帖子</th>
		<th>内容</th>
		<th>发布时间</th>
		<th>图片</th>
	</tr>
	<c:forEach items="${comments}" var="i" varStatus="vs">
		<tr height="24" class="${vs.index%2==0?'odd':'even' }">
			<td nowrap="nowrap">${i.owner}</td>
			<td nowrap="nowrap">${i.post_id}</td>
			<td nowrap="nowrap" name="commentText">${i.comment_text}</td>
			<td nowrap="nowrap" name="commetnDate">${i.comment_date}</td>
			<td nowrap="nowrap" name="img_url">${i.img_url}</td>
			<td nowrap="nowrap" ><a href="${pageContext.request.contextPath}/servlet/DelCommentServlet?mode=web&commentId=${i.comment_id}">删除</a></td>
		</tr>
	</c:forEach>
</table>
  </body>
</html>