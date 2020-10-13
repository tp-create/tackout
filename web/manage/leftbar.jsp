<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<div id="menu-mng" class="lefter">
		<div class="box">
			<dl>			 
	    	<c:if test="${sessionScope.loginUser!=null && sessionScope.loginUser.administrator}">
				<dt>用户管理</dt>
				<dd><a href="User">用户管理</a></dd>
				<dt>商品信息</dt>
				<dd><em><a href="Category?action=create">新增</a></em><a href="Category">分类管理</a></dd>
				<dd><em><a href="Product?action=create">新增</a></em><a href="Product">商品管理</a></dd>
				<dt>订单管理</dt>
				<dd><a href="Order">订单管理</a></dd>
				<dt>留言管理</dt>
				<dd><a href="GuestBook">留言管理</a></dd>
				<dt>新闻管理</dt>
				<dd><em><a href="News?action=create">新增</a></em><a href="News">新闻管理</a></dd>
			</c:if>
			<c:if test="${sessionScope.loginUser!=null && !(sessionScope.loginUser.administrator)}">
				<dt>用户管理</dt>
				<dd><a href="User?action=self">用户管理</a></dd>				
				<dt>订单管理</dt>
				<dd><a href="Order">订单管理</a></dd>							
			</c:if>
			</dl>
		</div>
	</div>