<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="REFRESH" CONTENT="60">
    <link rel="icon" type="image/x-icon" href="<c:url value='/resources/favicon.ico'/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/assets/css/sorm.css'/>"/>
    <script language="javascript" type="text/javascript"
            src="<c:url value='/resources/assets/lib/jquery-2.0.3.min.js'/>"></script>
    <title>ЭРА-ГЛОНАСС</title>
</head>
<c:set var="locale" value="ru"/>
<body>
<div class="page_wrapper">
    <div class="clear">
        <h1 class="big_logo">
            Авторизация
        </h1>

        <div class="login_form">
            <div class="form_wrapper">
                <h3><span>Авторизация</span></h3>

                <div class="error_message" id="message-box">
                </div>
                <form action="<c:url value='/j_spring_security_check'/>" name="forma" method="post">
                    <fieldset>
                        <div class="form_field">
                            <label for="user_name">Логин</label>
                            <input type="text" name="j_username" id="user_name" maxlength="256"/>
                        </div>
                        <div class="form_field">
                            <label for="user_pass">Пароль</label>
                            <input type="password" name="j_password" id="user_pass" maxlength="32" autocomplete="new-password"/>
                        </div>
                        <div class="form_field">
                            <button type="submit" value="Вход" id="loginBtn" onclick="return onFormSubmit()">Вход
                            </button>
                        </div>
                    </fieldset>
                    <input type="hidden"
                           name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>
</div>

</body>

<script type="text/javascript">

    $(document).ready(function () {
        document.getElementById('loginBtn').disabled = true;
        var messageBox = $(".error_message"),
                no_grants_txt = 'У Вас нет прав для доступа к запрашиваемому функционалу.',
                other_err_txt = 'Указанная комбинация логин/пароль отсутствует. В доступе к системе отказано.';
        if (<%=request.getAttribute("errormsg") != null%>) {
            messageBox.show();
            if (<%=request.getAttribute("errormsg") == "NO_GRANTS"%>) {
                messageBox.append($('<p>' + no_grants_txt + '</p>'));
            } else {
                messageBox.append($('<p>' + other_err_txt + '</p>'));
            }
        } else {
            messageBox.hide();
        }

        $('#user_name').on("keyup paste change blur input", setLoginBtnAvailability);
        $('#user_pass').on("keyup paste change blur input", setLoginBtnAvailability);

        function setLoginBtnAvailability() {
            document.getElementById('loginBtn').disabled =
                    $("#user_pass").val() === '' || $("#user_name").val() === '';
        }


    });

    function onFormSubmit() {
        var hash = window.location.hash;
        document.cookie = "hashPart=" + window.btoa(hash);
        return true;
    }

</script>

</body>
</html>