<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <link rel="icon" type="image/x-icon" href="<c:url value='/favicon.ico'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css'/>"/>
<%--
    <script language="javascript" type="text/javascript"
            src="<c:url value='/resources/assets/lib/jquery-2.0.3.min.js'/>"></script>
--%>
    <title>Непредвиденная ошибка</title>
</head>
<c:set var="locale" value="ru"/>
<body>
<div class="page_wrapper">
    <div class="clear">
        <div class="main_content">
            <h1 class="header_logo"></h1>
            <div class="user_menu">
                <ul>
                    <li><a href="${pageContext.request.contextPath}" class="home_ico">На главную</a></li>
                    <li><a href="#" onclick="document.getElementById('logout').submit();">Выйти</a></li>
                    <c:url var="logoutUrl" value="/logout.html"/>
                    <form action="${pageContext.request.contextPath}/logout.html" id="logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </ul>
            </div>
            <div class="user_form error">
                <h3>В системе произошла непредвиденная ошибка или была запрошена несуществующая страница</h3>
            </div>
        </div>
    </div>
</div>
</body>
</html>