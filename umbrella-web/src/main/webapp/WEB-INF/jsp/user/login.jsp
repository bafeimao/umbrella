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
