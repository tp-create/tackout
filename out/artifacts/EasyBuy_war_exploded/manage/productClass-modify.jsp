<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
	    <c:if test="${name==null || name.equals('')}"><h2>新增分类</h2></c:if>
		<c:if test="${name!=null && !name.equals('')}"><h2>修改分类</h2></c:if>
		<div class="manage">
			<form action="Category?action=save&entityId=${entityId}" method="post">
				<table class="form">
					<tr>
						<td class="field">父分类：</td>
						<td>
							<c:if test="${parentId==0 && entityId!=null}">
							<input type="hidden" class="text" name="parentId" value="0" />
							<input type="text" class="text" name="parent" value="根栏目" readonly="readonly"/>
							</c:if>
							<c:if test="${entityId==null || (entityId!=null && parentId!=0)}">
							<select name="parentId" >
								<option value="0" 
								     <c:if test="${parentId==0}">selected="selected" </c:if> >根栏目</option>
								<c:forEach items="${categories}" var="category">
								<option value="<c:out value='${category.id}'/>"  <c:if test="${parentId==category.id}">selected="selected" </c:if>><c:out value="${category.name}"/></option>
								</c:forEach>
							</select>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="field">分类名称：</td>
						<td><input type="text" class="text" name="name" value="${name}" /></td>
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
