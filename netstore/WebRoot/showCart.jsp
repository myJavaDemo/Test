<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp" %>
	<br/>
	<c:if test="${empty sessionScope.cart.items}">
		<h1>你还没有购买物品</h1>
	</c:if>
	<c:if test="${!empty sessionScope.cart.items}">
		<table border="1" width="666" align="center">
			<tr>
				<th>选择</th>
    			<th>商品名称</th>
    			<th>单价</th>
    			<th>数量</th>
    			<th>小计</th>
    			<th>操作</th>
			</tr>
		<c:forEach items="${sessionScope.cart.items}" var="me" varStatus="vs">
			<tr class="${vs.index%2==0?'odd':'even'}">
				<td><input type="checkbox" name="bookIds" value="${me.key}"/></td>
				<td>${me.value.book.name}</td>
				<td>${me.value.book.price}</td>
				<td>
					<input type="text" size="3" id="quantity" name="quantity" value="${me.value.quantity}" onchange="changeNum(this,'${me.key}',${me.value.quantity})"/>
				</td>
				<td>${me.value.totalPrice}</td>
				<td>
					<a href="javascript:delOneItem('${me.key}')">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="6">
				共${sessionScope.cart.totalQuantity}件商品，付款金额为${sessionScope.cart.amount}
				<a href="${pageContext.request.contextPath}/servlet/ClientServlet?op=genOrder">去付款</a>
			</td>
		</tr>
		</table>
		<script type="text/javascript">
			function changeNum(inputObj,bookId,oldNum){
				var num = inputObj.value;
				if(!/^[1-9][0-9]*$/.test(num)){
					alert("请输入正确的数量");
					return;
				}
				
				var sure = window.confirm("确定要修改数量吗?");
				if(sure){
					window.location.href="${pageContext.request.contextPath}/servlet/ClientServlet?op=changeNum&bookId="+bookId+"&num="+num;
				}else{
					inputObj.value=oldNum;
				}
			}
			function delOneItem(bookId){
				var sure = window.confirm("确定要删除该项吗?");
				if(sure){
					window.location.href="${pageContext.request.contextPath}/servlet/ClientServlet?op=delOneItem&bookId="+bookId;
				}
			}
		</script>
	</c:if>
  </body>
</html>
