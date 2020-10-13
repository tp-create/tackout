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
<title>易买网 - 首页</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="scripts/function.js"></script>
</head>
<body>
<jsp:include page="top.jsp" />
<div id="register" class="wrap">
	<div class="shadow">
		<em class="corner lb"></em>
		<em class="corner rt"></em>
		<div class="box">
			<h1>欢迎注册易买网</h1>
			<ul class="steps clearfix">
				<li class="current"><em></em>填写注册信息</li>
				<li class="last"><em></em>注册成功</li>
			</ul>			
			<form id="regForm" method="post" action="Register?action=save" >
				<table>
					<tr>
						<td class="field">用户名(*)：</td>
						<td>
							<input class="text" type="text" name="userId"  id="userId"  maxlength="10"  value="${param.userId}"/>							
							<c:if test="${not empty errors && not empty errors['userId']}">
								<span class="error">${errors['userId']}</span>
							</c:if>
							<c:if test="${empty errors || empty errors['userId']}"><span></span></c:if>
						</td>
					</tr>
					<tr>
						<td class="field">真实姓名(*)：</td>
						<td><input class="text" type="text" name="userName" value="${param.userName}" maxlength="10"/><span></span></td>
					</tr>
					<tr>
						<td class="field">登录密码(*)：</td>
						<td><input class="text" type="password" id="password" name="password"  value="${param.password}" maxlength="16"/><span></span></td>
					</tr>
					<tr>
						<td class="field">确认密码(*)：</td>
						<td><input class="text" type="password" name="confirmPassword" value="${param.confirmPassword}" maxlength="16"/><span></span></td>
					</tr>
					<tr>
						<td class="field">性别(*)：</td>
						<td>
						  <input class="radio" type="radio" name="sex" value="male" 
						  <c:if test="${empty param.sex || param.sex=='male'}">checked="checked"</c:if>
						  >男性</input>
						  <input class="radio" type="radio" name="sex" value="female"
						  <c:if test="${not empty param.sex && param.sex=='female'}">checked="checked"</c:if>
						  >女性</input>
						  <span></span></td>
					</tr>
					<tr>
						<td class="field">出生日期：</td>
						<td><input class="text" type="text" id="birthday" name="birthday"  value="${param.birthday}"/><span></span></td>
					</tr>
					<tr>
						<td class="field">身份证：</td>
						<td><input class="text" type="text" name="identityCode" value="${param.identityCode}" maxlength="18"/><span></span></td>
					</tr>
					<tr>
						<td class="field">电子邮件：</td>
						<td><input class="text" type="text" name="email" value="${param.email}" maxlength="30"/><span></span></td>
					</tr>
					<tr>
						<td class="field">手机(*)：</td>						
						<td><input class="text" type="text" name="mobile" value="${param.mobile}" maxlength="11"/><span></span></td>
					</tr>
					<tr>
						<td class="field">收货地址(*)：</td>
						<td><input class="text" type="text" name="address" value="${param.address}" maxlength="50"/><span></span></td>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-green"><input type="submit" name="submit" value="提交注册" /></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2015 汉唐知本 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
