<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理 - 易买网</title>
<link type="text/css" rel="stylesheet" href="../css/style.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../scripts/function.js"></script>
</head>
<body>
	<jsp:include page="top.jsp" />
	<div id="main" class="wrap">
		<jsp:include page="leftbar.jsp" />
		<div class="main">
			<h2>订单管理</h2>
			<div class="manage">
				<div class="search">
					<form method="post" action="Order" id="orderForm" >
						订单号：<input type="text" class="text" name="entityId" id="entityId"
							value="<c:out value="${param.entityId}"/>" />
						<c:if
							test="${sessionScope.loginUser!=null && (sessionScope.loginUser.administrator)}">
					 订货人：<input type="text" class="text" name="userName"
								value="${param.userName}" />
						</c:if>
						<label class="ui-blue"><input type="submit" name="submit"
							value="查询" />
						</label>
					</form>
				</div>
				<div class="spacer"></div>
				<table class="list">
				    <c:if test="${orders.entrySet()==null || orders.entrySet().size()==0}">
				             无符合此条件的订单！
				    </c:if>
					<c:forEach items="${orders.entrySet().iterator()}" var="entry">
						<c:set var="i" value="0"></c:set>
						<tr>
							<th class="first w4 c" colspan="2">单号:${entry.getKey().id} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间:${entry.getKey().createTime}</th>							
							<th class="w1 c" colspan="2">状态:${entry.getKey().displayStatus}
								
								<span id="span"> 
									<c:if	test="${sessionScope.loginUser!=null && (sessionScope.loginUser.administrator)&& (entry.getKey().status<4)}">
										<select name="status"	onchange='changStatu(this, ${entry.getKey().id})'>
											
												<option value="1"
													<c:if test="${entry.getKey().status==1}">selected="selected" </c:if>>待审核</option>
												<option value="2"
													<c:if test="${entry.getKey().status==2}">selected="selected" </c:if>>审核通过</option>
												<option value="3"
													<c:if test="${entry.getKey().status==3}">selected="selected" </c:if>>配货</option>
												<option value="4"
													<c:if test="${entry.getKey().status==4}">selected="selected" </c:if>>发货</option>																						
										</select> 
									</c:if>
								
									<c:if	test="${sessionScope.loginUser!=null && !(sessionScope.loginUser.administrator)}">																											
										<c:if test="${entry.getKey().status==4}">
											<input  type="button"  value="收货确认" onclick='changStatus(this, ${entry.getKey().id},5)' />											
									    </c:if>
									 </c:if> 
								</span>
								</th>
						</tr>
						<c:forEach items="${entry.getValue()}" var="product">
							<c:set var="i" value="${i+1}"></c:set>
							<c:if test="${i==1}">
								<tr>
									<td class="first w4 c"><img
										src="../files/${product.fileName}" /><a
										href="../Product?action=view&entityId=${product.id}">${product.name}</a>
									</td>
									<td class="w1 c">${product.price}</td>
									<td class="w1 c">${product.stock}</td>
									<td rowspan="${entry.getValue().size()}" class="w1 c">总计:${entry.getKey().cost}</td>
								</tr>
							</c:if>
							<c:if test="${i>1}">
								<tr>
									<td class="first w4 c"><img
										src="../files/${product.fileName}" /><a
										href="../Product?action=view&entityId=${product.id}">${product.name}</a></td>
									<td class="w1 c">${product.price}</td>
									<td class="w1 c">${product.stock}</td>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
					
					
				</table>
				
				<div class="pager">
					<ul class="clearfix">
						<c:if test="${pager.pageCount>1}">
							<li><a href="Order?page=1">首页</a>
							</li>
							<c:choose>
								<c:when test="${pager.currentPage>=3}">
									<li>...</li>
									<li class="current"><a
										href="Order?page=<c:out value='${pager.currentPage-1}'/>">
											<c:out value="${pager.currentPage-1}" />
									</a>
									</li>
								</c:when>
								<c:when test="${pager.currentPage>=2}">
									<li class="current"><a
										href="Order?page=<c:out value='${pager.currentPage-1}'/>">
											<c:out value="${pager.currentPage-1}" />
									</a>
									</li>
								</c:when>
							</c:choose>

							<li class="current"><c:out value="${pager.currentPage}" />
							</li>

							<c:choose>
								<c:when test="${pager.currentPage+1<pager.pageCount}">
									<li><a
										href="Order?page=<c:out value='${pager.currentPage+1}'/>"><c:out
												value="${pager.currentPage+1}" />
									</a>
									</li>
									<li>...</li>
								</c:when>
								<c:when test="${pager.currentPage+1<=pager.pageCount}">
									<li><a
										href="Order?page=<c:out value='${pager.currentPage+1}'/>"><c:out
												value="${pager.currentPage+1}" />
									</a>
									</li>
								</c:when>
							</c:choose>
							<li><a href="Order?page=<c:out value='${pager.pageCount}'/>">尾页</a>
							</li>
						</c:if>						
					</ul>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div id="footer">Copyright &copy; 2013 汉唐知本 All Rights Reserved.
		京ICP证1000001号</div>
</body>
</html>
