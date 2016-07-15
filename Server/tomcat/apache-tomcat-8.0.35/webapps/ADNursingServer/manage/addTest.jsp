<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
    <form action="${pageContext.request.contextPath}/servlet/AddTestServlet?mode=web&owner=lhc" method="post" >
    <h2 align="center">添加测试结果</h2>
    	<table border="1" width="438" align="center">
    		<tr>
    			<td>类型</td>
    			<td>
    				<input type="text" name="testType"/>
    			</td>
    		</tr>
    		<tr>
    			<td>分数</td>
    			<td>
    				<input type="text" name="testPoint"/>
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
