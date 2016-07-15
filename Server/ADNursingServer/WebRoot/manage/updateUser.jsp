<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
    <form enctype="multipart/form-data" action="${pageContext.request.contextPath}/servlet/UpdateUserServlet?mode=web&owner=趋驰者" method="post" >
    <h2 align="center">更新用户信息</h2>
    	<table border="1" width="438" align="center">
    		<tr>
    			<td>gender</td>
    			<td>
    				<input type="text" name="gender"/>
    			</td>
    		</tr>
    		<tr>
    			<td>address</td>
    			<td>
    				<input type="text" name="address"/>
    			</td>
    		</tr>
    		<tr>
    			<td>description</td>
    			<td>
    				<input type="text" name="description"/>
    			</td>
    		</tr>
    		<tr>
    			<td>图片上传</td>
    			<td>
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
