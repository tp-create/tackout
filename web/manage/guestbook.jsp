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
		<h2>留言管理</h2>
		<div class="manage">
			<table class="list">
				<tr>
					<th>编号</th>
					<th>姓名</th>
					<th>留言内容</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${comments}" var="comment">
				<c:set var="i" value="${i+1}"></c:set>
				<tr>
				
					<td class="first w4 c"><c:out
									value="${(pageNo-1)*(pager.pagesPerDisplay)+i}" /></td>
					<td class="w1 c"><c:out value="${comment.nickName}"/></td>
					<td><c:out value="${comment.content}"/></td>
					<td class="w1 c"><c:if test="${comment.replyTime!=null}">已回复</c:if></td>
					<td class="w1 c"><a href="GuestBook?action=read&entityId=${comment.id}"><c:if test="${comment.replyTime!=null}">修改</c:if>
					<c:if test="${comment.replyTime==null}">回复</c:if></a>
					<div class="manageDel"> 
					<a  self_url="GuestBook?action=delete&entityId=${comment.id}">删除</a>	</div>				
					</td>
				</tr>
				</c:forEach>
			</table>
			<div class="pager">
				<ul class="clearfix">
					
	<c:if test="${pager.pageCount>1}">
	<li><a href="GuestBook?page=1">首页</a></li>	
				<c:choose>
					<c:when test="${pager.currentPage>=3}">
						<li>...</li>
						<li class="current"><a href="GuestBook?page=<c:out value='${pager.currentPage-1}'/>">
						<c:out value="${pager.currentPage-1}" /></a></li>
					</c:when>					
					<c:when test="${pager.currentPage>=2}">
						<li class="current"><a href="GuestBook?page=<c:out value='${pager.currentPage-1}'/>">
						<c:out value="${pager.currentPage-1}" /></a></li>
					</c:when>			
				</c:choose>	
					
					<li class="current"><c:out value="${pager.currentPage}" /></li>
					
				<c:choose>
					<c:when test="${pager.currentPage+1<pager.pageCount}">
						<li><a href="GuestBook?page=<c:out value='${pager.currentPage+1}'/>"><c:out value="${pager.currentPage+1}" /></a></li>
						<li>...</li>
					</c:when>
					<c:when test="${pager.currentPage+1<=pager.pageCount}">
						<li><a href="GuestBook?page=<c:out value='${pager.currentPage+1}'/>"><c:out value="${pager.currentPage+1}" /></a></li>
					</c:when>					
				</c:choose>				
	<li><a href="GuestBook?page=<c:out value='${pager.pageCount}'/>">尾页</a></li>
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
