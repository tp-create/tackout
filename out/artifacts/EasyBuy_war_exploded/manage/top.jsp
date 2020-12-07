<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<div id="header" class="wrap">
	<div id="logo"><img src="../images/logo.gif" /></div>
	<div class="help"><a href="../Home">返回前台页面</a></div>
	<div class="navbar">
		<ul class="clearfix">
			<li class="current"><a href="index.jsp">首页</a></li>
			<li><a href="User">用户</a></li>
			<li><a href="Product">商品</a></li>
			<li><a href="Order">订单</a></li>
			<li><a href="GuestBook">留言</a></li>
			<li><a href="News">新闻</a></li>
		</ul>
	</div>
</div>
<div id="childNav">
	<div class="welcome wrap">
		<c:out value="${sessionScope.loginUser.userName}"/>您好，今天是<fmt:formatDate pattern="yyyy-MM-dd" 	value="<%=new Date()%>"/>，欢迎回到管理后台。
	</div>
</div>
<div id="position" class="wrap">
	您现在的位置：<a href="../Home">易买网</a> &gt; 管理后台
</div>