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
		<h2>修改订单</h2>
		<div class="manage">
			<form action="Order?action=save&entityId=${entityId}" method="post">
				<table class="form">
					<tr>
						<td class="field">订单编号：</td>
						<td><input type="text" class="text" name="entityId" value="${entityId}" readonly="readonly" disabled="disabled"/></td>
					</tr>
					<tr>
						<td class="field">订购人姓名：</td>
						<td><input type="text" class="text" name="name" value="${userName}" readonly="readonly" disabled="disabled"/></td>
					</tr>
					<tr>
						<td class="field">订购人地址：</td>
						<td><input type="text" class="text" name="userAddress" value="${userAddress}" readonly="readonly" disabled="disabled"/></td>
					</tr>
					<tr>
						<td class="field">总金额：</td>
						<td><input type="text" class="text" name="cost" value="${cost}" readonly="readonly" disabled="disabled"/></td>
					</tr>
					<tr>
						<td class="field">下单日期：</td>
						<td><input type="text" class="text" name="createTime" value="${createTime}" readonly="readonly" disabled="disabled"/></td>
					</tr>
					<tr>
						<td class="field">订单状态：</td>
						<td> 
						<select name="status">
						    <c:if test="${sessionScope.loginUser!=null && (sessionScope.loginUser.administrator)}">
								<option value="1"  <c:if test="${status==1}">selected="selected" </c:if>>待审核</option>
								<option value="2"  <c:if test="${status==2}">selected="selected" </c:if>>审核通过</option>
								<option value="3"  <c:if test="${status==3}">selected="selected" </c:if>>配货</option>
								<option value="4"  <c:if test="${status==4}">selected="selected" </c:if>>发货</option>
							</c:if>								
							<c:if test="${sessionScope.loginUser!=null && !(sessionScope.loginUser.administrator)}">
								<option value="5"  <c:if test="${status==5}">selected="selected" </c:if>>收货确认</option>
							</c:if>
						</select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-blue"><input type="submit" name="submit" value="更新" /></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2013 汉唐知本 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
