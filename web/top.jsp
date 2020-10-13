<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/My97DatePicker/WdatePicker.js"></script> 
<%-- <c:set value="" var="datas"></c:set>  --%>
<script type="text/javascript">
var cart = [<c:forEach items="${sessionScope.cart.items}"  var="item" varStatus="status"><c:if test="${not status.first}">,</c:if>
['files/${item.product.fileName}','${item.product.price}' ,'${item.product.name}']</c:forEach>];
</script>
<%-- <c:forEach items="${sessionScope.cart2.items}"  var="item" varStatus="status">
   ['files/${item.product.fileName}' ,${item.product.price} ,${item.product.name}]
    <c:set var="datas" value="${datas+'[files/'+item.product.fileName+','+item.product.price+','+item.product.name+']'}">
    </c:set>
</c:forEach>
${datas } --%>
<div id="header" class="wrap">
	<div id="logo"><img src="images/logo.gif" /></div>
	<div class="help">
	<a href="Cart" class="shopping" id="shoppingBag" data="cart">购物车<c:out value="${sessionScope.cart.items.size()}" default="0"/>件
	</a>
	<c:if test="${sessionScope.loginUser!=null && sessionScope.cart.items.size()>0}">	     
		 <a class="button" id="logout" href="javascript:void(0);">注销</a>
	</c:if>
	
	<c:if test="${sessionScope.loginUser!=null && sessionScope.cart==null || sessionScope.loginUser!=null&&sessionScope.cart.items.size()==0}">
    	<a class="button" href="Login?action=logout">注销</a>
	</c:if>
	<c:if  test="${sessionScope.loginUser==null}" >
		<a href="login.jsp">登录</a>
	</c:if>
	<a href="register.jsp">注册</a>
	<!-- && sessionScope.loginUser.administrator -->
	<c:if test="${sessionScope.loginUser!=null }">
		<a href="GuestBook">留言</a>
    	<a href="manage/index.jsp">后台管理</a>
	</c:if>
	</div>
	<div class="navbar">
		<ul class="clearfix">
			<li class="current"><a href="Home">首页</a></li>
			<li><a href="#">图书</a></li>
			<li><a href="#">百货</a></li>
			<li><a href="#">品牌</a></li>
			<li><a href="#">促销</a></li>
		</ul>
	</div>
</div>
<div id="childNav">
	<div class="wrap">
		<ul class="clearfix">
			<li class="first"><a href="#">音乐</a></li>
			<li><a href="#">影视</a></li>
			<li><a href="#">少儿</a></li>
			<li><a href="#">动漫</a></li>
			<li><a href="#">小说</a></li>
			<li><a href="#">外语</a></li>
			<li><a href="#">数码相机</a></li>
			<li><a href="#">笔记本</a></li>
			<li class="last"><a href="#">Investor Relations</a></li>
		</ul>
	</div>
</div>