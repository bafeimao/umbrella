<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%
    pageContext.setAttribute("foo", "bar");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>
    JSTL使用指南
</h1>
<hr/>

<c:out value="这是out的输出，中间带有<html>标签，这里的标签符号将会被转移以便能够正常的显示" />
<br/>

<c:out value="${null}" default="这一行显示的是默认值，因为value值为null"/>
<br/>

<%--尽量减少这种将java逻辑和页面混杂在一起的情况--%>
<%out.println("这一行是来自java的out输出"); %>
<br/>

<%String s = "这是一个java变量值";%>
<%="输出变量值：" + s%>
<br/>

<ul>
    <h1>Remove标签的使用：</h1>
    <c:set var="name" scope="session">张三</c:set>
    <c:set var="age" scope="session">22</c:set>
    <c:set var="sex" scope="session">男</c:set>

    <li><c:out value="${sessionScope.name}"></c:out>
    <li><c:out value="${sessionScope.age}"></c:out>
    <li><c:out value="${sessionScope.sex}"></c:out>

    <c:remove var="age"/>

    <li><c:out value="${sessionScope.name}"></c:out>
    <li><c:out value="${sessionScope.age}"></c:out>
    <li><c:out value="${sessionScope.sex}"></c:out>
</ul>

<ul>
    <h1>catch的使用：</h1>
    <c:catch var="error">
        <c:set target="foo" property="bar"/>
    </c:catch>

    <c:out value="${error}" escapeXml="false"/>
</ul>

<hr/>
<div>
    for each
</div>
<hr/>

<table border="1">
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                    ${user.id}
            </td>
            <td>
                    ${user.name }
            </td>
        </tr>
    </c:forEach>
</table>

<c:set var="author" value="sb"/>
<c:out value="${author}"/>

<hr/>
<%--		<c:set property="name" target="user" value="ktgu-updated" />--%>

<c:out value="${user.email}" default="default user value"/>
<c:out value="${foo}"/>

<% out.println("aaa"); %>

</body>
</html>