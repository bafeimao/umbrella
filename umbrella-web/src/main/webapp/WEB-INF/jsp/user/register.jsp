<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
<jsp:include page="../fragments/header.jsp"/>
<body>
<jsp:include page="../fragments/topnav.jsp"/>

<div class="container">
    <h2>用户注册</h2>
    <form:form modelAttribute="owner" id="frmRegister" action="/register" method="post" class="form-horizontal"
               role="form">
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">
                邮箱
            </label>

            <div class="col-sm-3">
                <input type="email" id="email" name="email" value="29283212@qq.com" class="form-control" placeholder="请输入正确的邮箱地址"/>
            </div>
        </div>

        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">
                密码
            </label>

            <div class="col-sm-3">
                <input type="password" id="password" name="password" value="aaaaaa" class="form-control" placeholder="请输入至少6位的密码"/>
            </div>
        </div>

        <div class="form-group">
            <label for="password2" class="col-sm-2 control-label">
                确认密码
            </label>

            <div class="col-sm-3">
                <input type="password" id="password2" name="password2" value="aaaaaa" class="form-control" placeholder="请输入确认密码"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-3">
                <button type="submit" class="btn btn-primary btn-lg">
                    注册
                </button>
                <input type="checkbox" id="agree" name="agree"> <a href="#">服务协议</a>
            </div>
        </div>
    </form:form>
</div>
</body>

</html>
