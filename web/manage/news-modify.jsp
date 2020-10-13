<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/> 
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
	<c:if test="${title!=null && !title.equals('')}">
		<h2>修改新闻</h2>
	</c:if>
	<c:if test="${title==null || title.equals('')}">
		<h2>新增新闻</h2>
	</c:if>
		<div class="manage">
			<form action="News?action=save&entityId=${entityId}" method="post" id="newsAdd">
				<table class="form">
					<tr>
						<td class="field">新闻标题(*)：</td>
						<td><input type="text" class="text" name="title" value="${title}" />
							<c:if test="${not empty errors && not empty errors['title']}">
								<span class="error">${errors['title']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['title']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">新闻内容(*)：</td>
						<td><textarea name="content" class='text'>${content}</textarea>
							<c:if test="${not empty errors && not empty errors['content']}">
								<span class="error">${errors['content']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['content']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-blue"><input type="submit" name="submit" value="确定" /></label></td>
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
