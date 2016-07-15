<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
    <form enctype="multipart/form-data" action="${pageContext.request.contextPath}/servlet/AddCommentServlet?mode=web&owner=趋驰者&postId=2" method="post" >
    <h2 align="center">发布评论</h2>
    	<table border="1" width="438" align="center">
    		<tr>
    			<td>文章内容</td>
    			<td>
    				<textarea rows="5" cols="45" name="commentText"></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td>图片上传</td>
    			<td>
    				<input type="file" id="uploadfile" name="uploadfile" />
    				<input type="file" id="uploadfile" name="uploadfile" />
    			</td>
    		</tr>

    		<tr>
    			<td colspan="2">
    				<input type="submit" value="发布"/>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>