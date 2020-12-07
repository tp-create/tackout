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
		<c:if test="${reply!=null && !(reply.equals(''))}">
			<h2>修改留言</h2>
		</c:if>
		<c:if test="${reply==null || (reply.equals(''))}">
			<h2>回复留言</h2>
		</c:if>
		<div class="manage">
			<form action="GuestBook?action=save" method="post" id="guestBook">
				<table class="form">
					<tr>
						<td class="field">留言编号：</td>
						<td><input type="text" class="text" name="entityId" value="${entityId}" readonly="readonly" style="border:0px;"/></td>
					</tr>
					<tr>
						<td class="field">留言姓名：</td>
						<td><input type="text" class="text" name="nickName" value="${nickName}" readonly="readonly" style="border:0px;"/></td>
					</tr>
					<tr>
						<td class="field">留言内容：</td>
						<td><input type="text" class="text" name="content" value="${content}" readonly="readonly" style="border:0px;"/></td>
					</tr>
					<tr>
						<td class="field">回复内容：</td>
						<td><textarea name="reply" >${reply}</textarea><span></span></td>
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
