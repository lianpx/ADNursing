<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<br/>
    <form action="${pageContext.request.contextPath}/servlet/UpdateTestInforServlet?mode=web&owner=趋驰者" method="post" >
    <h2 align="center">添加测试者信息</h2>
    	<table border="1" width="438" align="center">
    		<tr>
    			<td>testedPerson</td>
    			<td>
    				<input type="text" name="testedPerson"/>
    			</td>
    		</tr>
    		<tr>
    			<td>testedBirth</td>
    			<td>
    				<input type="text" name="testedBirth"/>
    			</td>
    		</tr>
    		    		<tr>
    			<td>testedGender</td>
    			<td>
    				<input type="text" name="testedGender"/>
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
