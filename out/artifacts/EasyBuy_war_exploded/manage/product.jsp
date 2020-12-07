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
			<h2>商品管理</h2>			
			<div class="manage">
				<div class="search">
					<form method="post" action="Product">
					          所属分类：<c:out value="${product.name}"/>
							<select name="category" id="category">	
									<option value="" ></option>
							        <c:forEach items="${categories}" var="c">					
									<option value="${c.name}" <c:if test="${c.name.equals(product.name)}">selected="selected" </c:if>>${c.name}</option>
									</c:forEach>																				
							</select>					
							
						商品名称：<input type="text" class="text" name="fileName" id="fileName"
							value="<c:out value="${product.fileName}"/>" />						
						<label class="ui-blue"><input type="submit" name="submit"
							value="查询" />
						</label>
					</form>
				</div>
				<div class="spacer"></div>
				<table class="list">
				    <c:if test="${products==null || products.size()==0}">
				             无符合此条件的商品！
				    </c:if>	
				    <c:if test="${products!=null && products.size()>0}">				
					<tr>
						<th>编号</th>
						<th>商品名称</th>
						<th>操作</th>
					</tr>
					</c:if>
					
					<c:forEach items="${products}" var="product">
					<c:set var="i" value="${i+1}"></c:set>
						<tr>
							<td class="first w4 c"><c:out
									value="${(pageNo-1)*(pager.pagesPerDisplay)+i}" />
							</td>
							<td class="thumb"><img src="../files/${product.fileName}" /><a
								href="Product?action=read&entityId=${product.id}"><c:out
										value="${product.name}" />
							</a>
							</td>
							<td class="w1 c"><a
								href="Product?action=read&entityId=${product.id}">修改</a> 
								<div class="manageDel">
								<a  self_url="Product?action=delete&entityId=${product.id}">删除</a></div>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul class="clearfix">
					<c:if test="${pager.pageCount>1}">
					<li><a href="Product?page=1&category=${product.name}&fileName=${product.fileName}">首页</a></li>	
					<c:choose>
						<c:when test="${pager.currentPage>=3}">
						<li>...</li>
						<li class="current"><a href="Product?page=<c:out value='${pager.currentPage-1}'/>&category=${product.name}&fileName=${product.fileName}">
						<c:out value="${pager.currentPage-1}" /></a></li>
						</c:when>					
						<c:when test="${pager.currentPage>=2}">
						<li class="current"><a href="Product?page=<c:out value='${pager.currentPage-1}'/>&category=${product.name}&fileName=${product.fileName}">
						<c:out value="${pager.currentPage-1}" /></a></li>
						</c:when>			
					</c:choose>	
					
					<li class="current"><c:out value="${pager.currentPage}" /></li>
					
					<c:choose>
						<c:when test="${pager.currentPage+1<pager.pageCount}">
						<li><a href="Product?page=<c:out value='${pager.currentPage+1}'/>&category=${product.name}&fileName=${product.fileName}"><c:out value="${pager.currentPage+1}" /></a></li>
						<li>...</li>
						</c:when>
						<c:when test="${pager.currentPage+1<=pager.pageCount}">
						<li><a href="Product?page=<c:out value='${pager.currentPage+1}'/>&category=${product.name}&fileName=${product.fileName}"><c:out value="${pager.currentPage+1}" /></a></li>
						</c:when>					
					</c:choose>				
					<li><a href="Product?page=<c:out value='${pager.pageCount}'/>&category=${product.name}&fileName=${product.fileName}">尾页</a></li>
					</c:if>
					</ul>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div id="footer">Copyright &copy; 2013 汉唐知本 All Rights Reserved.
		京ICP证1000001号</div>
</body>
</html>
