<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>易买网 - 首页</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="scripts/function.js"></script>
</head>
<body>
<jsp:include page="top.jsp" />
<div id="position" class="wrap">
	您现在的位置：<a href="Home">易买网</a> &gt; 购物车
</div>
<div class="wrap">
	<div id="shopping">
	<c:if test="${sessionScope.cart2==null || sessionScope.cart2.items.size()<1}"> 您尚未购买任何物品，是否进入<a href="Home">商品页</a>进行购买！</c:if>
	<c:if test="${sessionScope.cart2.items.size()>=1}"> 
		<form action="Cart?action=address" method="post">		
			<table>
				<tr>
					<th>商品名称</th>
					<th>商品单价</th>
					<th>商品价格</th>
					<th>购买数量</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${sessionScope.cart2.items}"  var="item" varStatus="status">
				<tr name="products" id="product_id_1">
					<td class="thumb"><img src="files/${item.product.fileName}"  /><a href="Product?action=view&entityId=${item.product.id}">${item.product.name}</a></td>
					<td class="number">${item.product.price}</td>
					<td class="price" id="price_id_1">
						<span><fmt:formatNumber value="${item.cost}" type="NUMBER" minFractionDigits="2" /></span>
						<input type="hidden" value="${item.product.price}" />
					</td>
					<td class="number">
                        <span name="del" data="${item.product.id},${status.index}">-</span>						   
						<input id="number_id_${item.product.id}" type="text" name="number" value="${item.quantity}" />							
						<span name="add" data="${item.product.id},${status.index}">+</span>						
					</td>                        
                    <td class="delete">
					  <a  self_url="Cart?action=remove&entityId=${item.product.id}&index=${status.index}" >删除</a>					                  
					</td>
				</tr>
				</c:forEach>				
			</table>
			 <div class="total"><span id="total">总计：￥<c:out value="${total}"/></span></div>
			<div class="pager">
				<ul class="clearfix">					
					<c:if test="${pager.pageCount>1}"> 	
					<li><a href="Cart?page=1">首页</a></li>					
					<c:choose>
						<c:when test="${pager.currentPage>=3}">
						<li>...</li>
						<li class="current"><a href="Cart?page=<c:out value='${pager.currentPage-1}'/>">
						<c:out value="${pager.currentPage-1}" /></a></li>
						</c:when>					
						<c:when test="${pager.currentPage>=2}">
						<li class="current"><a href="Cart?page=<c:out value='${pager.currentPage-1}'/>">
						<c:out value="${pager.currentPage-1}" /></a></li>
						</c:when>			
					</c:choose>	
					
					<li class="current"><c:out value="${pager.currentPage}" /></li>
					
					<c:choose>
						<c:when test="${pager.currentPage+1<pager.pageCount}">
						<li><a href="Cart?page=<c:out value='${pager.currentPage+1}'/>"><c:out value="${pager.currentPage+1}" /></a></li>
						<li>...</li>
						</c:when>
						<c:when test="${pager.currentPage+1<=pager.pageCount}">
						<li><a href="Cart?page=<c:out value='${pager.currentPage+1}'/>"><c:out value="${pager.currentPage+1}" /></a></li>
						</c:when>					
					</c:choose>		
					<li><a href="Cart?page=<c:out value='${pager.pageCount}'/>">尾页</a></li>
					</c:if>				
				</ul>				
			</div>
			
			<div class="button"><input type="submit" value="" /></div>
		</form>
		</c:if>
	</div>
</div>
<div id="footer">
	Copyright &copy; 2015 汉唐知本 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
