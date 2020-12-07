<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>易买网 - 首页</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="scripts/function.js"></script>
</head>
<body>
<jsp:include page="top.jsp" />
<div id="position" class="wrap">您现在的位置：<a href="Home">易买网</a> &gt;
在线留言</div>
<div id="main" class="wrap"><jsp:include page="leftbar.jsp" />
<div class="main">
<div class="guestbook">
<h2>全部留言</h2>
<ul>
	<c:forEach items="${comments}" varStatus="status" var="comment">
		<li>
		<dl>
			<dt><c:out value="${comment.content}" /></dt>
			<dd class="author">网友：<c:out value="${comment.nickName}" /> <span
				class="timer"><fmt:formatDate pattern="yyyy-MM-dd"
				value="${comment.createTime}" /></span></dd>
			<dd><c:out value="${comment.reply}" /></dd>
		</dl>
		</li>
	</c:forEach>
</ul>
<div class="clear"></div>
<div class="pager">
<ul class="clearfix">
	<c:if test="${pager.pageCount>1}">
			<li><a href="GuestBook?do=user&page=1">首页</a></li>	
				<c:choose>
					<c:when test="${pager.currentPage>=3}">
					<li>...</li>
					<li class="current"><a href="GuestBook?do=user&page=<c:out value='${pager.currentPage-1}'/>">
					<c:out value="${pager.currentPage-1}" /></a></li>
					</c:when>					
					<c:when test="${pager.currentPage>=2}">
					<li class="current"><a href="GuestBook?do=user&page=<c:out value='${pager.currentPage-1}'/>">
					<c:out value="${pager.currentPage-1}" /></a></li>
					</c:when>			
				</c:choose>	
					
			<li class="current"><c:out value="${pager.currentPage}" /></li>
					
				<c:choose>
					<c:when test="${pager.currentPage+1<pager.pageCount}">
						<li><a href="GuestBook?do=user&page=<c:out value='${pager.currentPage+1}'/>"><c:out value="${pager.currentPage+1}" /></a></li>
						<li>...</li>
					</c:when>
					<c:when test="${pager.currentPage+1<=pager.pageCount}">
						<li><a href="GuestBook?do=user&page=<c:out value='${pager.currentPage+1}'/>"><c:out value="${pager.currentPage+1}" /></a></li>
					</c:when>					
				</c:choose>		
			<li><a href="GuestBook?do=user&page=<c:out value='${pager.pageCount}'/>">尾页</a></li>
	</c:if>
</ul>
</div>
<div id="reply-box">
<form action="GuestBook?action=save" method="post" id="guestBook" >
<table>
	<tr>
		<td class="field">昵称：</td>
		<td><input class="text" type="text" name="nickName" value="<c:out value='${sessionScope.loginUser.getUserName()}'/>" disabled="disabled"/></td>
	</tr>
	<tr>
		<td class="field">留言内容：</td>
		<td><textarea name="content"></textarea><span></span></td>
	</tr>
	<tr>
		<td></td>
		<td><label class="ui-blue"><input type="submit"
			name="submit" value="提交留言" /></label></td>
	</tr>
</table>
</form>
</div>
</div>
</div>
<div class="clear"></div>
</div>
<div id="footer">Copyright &copy; 2015 汉唐知本 All Rights Reserved.
京ICP证1000001号</div>
</body>
</html>
