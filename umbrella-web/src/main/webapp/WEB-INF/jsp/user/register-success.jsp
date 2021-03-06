<%@ page language="java" pageEncoding="UTF-8"%>
<%--
  ~ Copyright 2002-2015 by bafeimao.net
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

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
