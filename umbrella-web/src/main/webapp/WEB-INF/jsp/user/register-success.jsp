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
			<h3>注册成功！</h3>
			<br/>
			<br/>
			我们给你发送了一封邮件，请及时<a href="#">验证</a>!&nbsp;&nbsp;&nbsp;&nbsp;
			没收到？<a href="#">重发</a>
		</div>
	</body>

</html>
