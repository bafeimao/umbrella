<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html lang="en">
	<jsp:include page="../fragments/header.jsp" />
	<body>
		<jsp:include page="../fragments/topnav.jsp" />

		<div class="container">
			<c:choose>
				<c:when test="${user['id']}">
					<c:set var="method" value="post" />
				</c:when>
				<c:otherwise>
					<c:set var="method" value="put" />
				</c:otherwise>
			</c:choose>

			<h2>
				用户登陆
			</h2>
			<form:form modelAttribute="owner" method="${method}"
				id="formRegister" role="form" class="form-horizontal">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">
						用户名
					</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="name" name="name"
							placeholder="请输入至少4位的用户名" />
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">
						登陆密码
					</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="password"
							name="password" placeholder="请输入至少6位的密码" />
					</div>
				</div>
				验证码： <img src="kaptcha" /> <br>  
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-3">
						<button type="submit" class="btn btn-default">
							登录
							</a>
					</div>
				</div>
			</form:form>
		</div>
	</body>

</html>
