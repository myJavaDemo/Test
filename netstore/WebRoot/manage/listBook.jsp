<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp" %>

	<br/><br/>
	<table border="1" width="666" align="center">
	  <tr>
			<th>选择</th>
			<th>书名</th>
			<th>作者</th>
			<th>单价</th>
			<th>描述</th>
			<th>所属分类</th>
			<th>图片</th>
			<th>操作</th>
		</tr>
	  	<c:forEach items="${page.records}" var="b" varStatus="vs">
	  		<tr class="${vs.index%2==0?'odd':'even'}">
	  			<td><input type="checkbox" name="ids" value="${b.id}"/></td>
	  			<td>${b.name}</td>
	  			<td>${b.author}</td>
	  			<td>${b.price}</td>
	  			<td>${b.description}</td>
	  			<td>${myfn:showCategoryName(b.categoryId)}</td>
	  			<td><img src="${pageContext.request.contextPath}/images${b.path}/${b.photoFileName}"></td>
	  			<td>
	  				<a href="${pageContext.request.contextPath}/servlet/ControlServlet?op=delBookById&bookId=${b.id}">删除</a>
	  			</td>
	  		</tr>
	  		
	  	</c:forEach>
	</table>
	<%@ include file="/commons/page.jsp" %>
  </body>
</html>
