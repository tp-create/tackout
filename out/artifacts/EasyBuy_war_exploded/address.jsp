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
	您现在的位置：<a href="Home">易买网</a> &gt; 结账
</div>
<div class="wrap">
	<div id="shopping">	
		<form action="Cart?action=pay" method="post">		
			收货地址:<input name="addr" id="addr" type="button"  value="添加新地址" onclick="addAddress()" /><span id="span"></span> <br />
			<c:forEach items="${address}" var="add">
			<c:set var="i" value="${i+1}"></c:set>
			<c:if test="${i==1}">
			<input name="address" id="address" type="radio" checked="checked" value="<c:out value="${add}"/>" /><span><c:out value="${add}"/></span><br />
			</c:if>
			<c:if test="${i!=1}">
			<input name="address" id="address" type="radio" value="<c:out value="${add}"/>" /><span><c:out value="${add}"/></span><br />
			</c:if>
			</c:forEach>
			<div class="button"><input type="submit" value="" /></div>
		</form>		
	</div>
<div class="clear"></div>
<div id="position1" class="wrap"></div>
<div class="wrap">
	<div id="shoppin"></div>
</div>
<div id="footer">
	Copyright &copy; XXX All Rights Reserved. 京ICP证1000001号
</div>
</div>
</body>
</html>
