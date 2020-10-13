<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>    
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
		<h2>用户管理</h2>
		<div class="manage">
			<table class="list">
				<tr>
					<th>用户名</th>
					<th>真实姓名</th>
					<th>性别</th>
					<th>Email</th>
					<th>手机</th>
					<th>操作</th>
				</tr>				
				<c:forEach items="${users}" var="user">
				<tr>
					<td class="first w4 c"><c:out value="${user.userId}"/></td>
					<td class="w1 c"><c:out value="${user.userName}"/></td>
					<td class="w2 c"><c:out value='${user.male?"男":"女"}'/></td>
					<td><c:out value="${user.email}"/></td>
					<td class="w4 c"><c:out value="${user.mobile}"/></td>
					<td class="w1 c">
						<c:if test="${sessionScope.loginUser!=null && (sessionScope.loginUser.userId==user.userId)}">
							<a href="User?action=read&entityId=<c:out value='${user.userId}'/>&userId=<c:out value='${user.userId}'/>">修改</a>
						</c:if>
						<c:if test="${sessionScope.loginUser!=null && (sessionScope.loginUser.administrator) && (!(user.login))}">
							<div class="manageDel">
							<a  self_url="User?action=delete&userId=${user.userId}" onclick="">删除</a></div>
						</c:if>					
					 </td>					
				</tr>
				</c:forEach>				
				<c:if test="${sessionScope.loginUser!=null && !(sessionScope.loginUser.administrator)}">
				<tr>
					<td class="first w4 c"><c:out value="${user.userId}"/></td>
					<td class="w1 c"><c:out value="${user.userName}"/></td>
					<td class="w2 c"><c:out value='${user.male?"男":"女"}'/></td>
					<td><c:out value="${user.email}"/></td>
					<td class="w4 c"><c:out value="${user.mobile}"/></td>
					<td class="w1 c">
					<c:if test="${sessionScope.loginUser!=null && !(sessionScope.loginUser.administrator)}">
					<a href="User?action=read&entityId=<c:out value='${user.userId}'/>&userId=<c:out value='${user.userId}'/>">修改</a>					
					</c:if>					
					 </td>					
				</tr>
				</c:if>
			</table>
			<div class="pager">
				<ul class="clearfix">					
				<c:if test="${pager.pageCount>1}">
				<li><a href="User?page=1">首页</a></li>	
				<c:choose>
						<c:when test="${pager.currentPage>=3}">
						<li>...</li>
						<li class="current"><a href="User?page=<c:out value='${pager.currentPage-1}'/>">
						<c:out value="${pager.currentPage-1}" /></a></li>
						</c:when>					
						<c:when test="${pager.currentPage>=2}">
						<li class="current"><a href="User?page=<c:out value='${pager.currentPage-1}'/>">
						<c:out value="${pager.currentPage-1}" /></a></li>
						</c:when>			
					</c:choose>	
					<li class="current"><c:out value="${pager.currentPage}" /></li>
					
					<c:choose>
						<c:when test="${pager.currentPage+1<pager.pageCount}">
						<li><a href="User?page=<c:out value='${pager.currentPage+1}'/>"><c:out value="${pager.currentPage+1}" /></a></li>
						<li>...</li>
						</c:when>
						<c:when test="${pager.currentPage+1<=pager.pageCount}">
						<li><a href="User?page=<c:out value='${pager.currentPage+1}'/>"><c:out value="${pager.currentPage+1}" /></a></li>
						</c:when>					
					</c:choose>			
				<li><a href="User?page=<c:out value='${pager.pageCount}'/>">尾页</a></li>
				</c:if>	
			</ul>
			</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2013 汉唐知本 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
