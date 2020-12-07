<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
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
		<h2>修改用户</h2>
		<div class="manage">
			<form id="regForm1" method="post" action="User?action=save&entityId=${param.entityId}" onsubmit="return checkForm(this)">
				<table class="form">
					<tr>
						<td class="field">用户名(*)：</td>
						<td><input class="text" type="text" name="userId"    value="${userId}" readonly="readonly" style="border:0px;"/>
							<c:if test="${not empty errors && not empty errors['userId']}">
								<span class="error">${errors['userId']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['userId']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">真实姓名(*)：</td>
						<td><input class="text" type="text" name="userName"  value="${userName}" maxlength="10"/>
							<c:if test="${not empty errors && not empty errors['userName']}">
								<span class="error">${errors['userName']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['userName']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">登录密码(*)：</td>
						<td><input class="text" type="password" id="password" name="password"  maxlength="16"  value="${password}"/>
							<c:if test="${not empty errors && not empty errors['password']}">
								<span class="error">${errors['password']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['password']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">确认密码(*)：</td>
						<td><input class="text" type="password" name="confirmPassword"  maxlength="16"  value="${confirmPassword}"/>
						<c:if test="${not empty errors && not empty errors['confirmPassword']}">
								<span class="error">${errors['confirmPassword']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['confirmPassword']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">性别(*)：</td>
						<td>
						  <input class="radio" type="radio" name="sex" value="male" <c:if test="${sex=='male'|| sex == null}">checked="checked" </c:if> >男性</input>
						  <input class="radio" type="radio" name="sex" value="female" <c:if test="${sex=='female'}">checked="checked" </c:if>>女性</input>
						  <span></span></td>
					</tr>
					<tr>
						<td class="field">出生日期：</td>
						<td><input class="text" type="text" name="birthday"   value="${birthday}"/>
							<c:if test="${not empty errors && not empty errors['birthday']}">
								<span class="error">${errors['birthday']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['birthday']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">身份证：</td>
						<td><input class="text" type="text" name="identityCode"  maxlength="18" value="${identityCode}"/><span></span></td>
					</tr>
					<tr>
						<td class="field">电子邮件：</td>
						<td><input class="text" type="text" name="email" maxlength="30"  value="${email}"/>
							<c:if test="${not empty errors && not empty errors['email']}">
								<span class="error">${errors['email']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['email']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">手机(*)：</td>
						<td><input class="text" type="text" name="mobile"  maxlength="11" value="${mobile}"/>
							<c:if test="${not empty errors && not empty errors['mobile']}">
								<span class="error">${errors['mobile']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['mobile']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">收货地址(*)：</td>		
						<td>
							<c:forEach items="${addr}" var="add">
							<input  class="text" type="text" name="address" maxlength="50"  value="<c:out value="${add}"/>" />
							</c:forEach>
							<c:if test="${not empty errors && not empty errors['address']}">
								<span class="error">${errors['address']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['address']}"><span></span></c:if>
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
