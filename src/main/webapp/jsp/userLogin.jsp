<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript">
        //页面加载
        $(function () {
            //更换验证码
            $("#imgVcode").click(function () {
                //向后台发请求从新响应验证码，替换src属性中的图片
                $("#imgVcode").prop("src","${pageContext.request.contextPath}/admin/serverCode?d="+new Date().getTime());
            });
            //登陆
            $("#log").click(function () {
                var usernameVal = $("#username").val();
                var passwordVal = $("#password").val();
                var clientCodeVal = $("#clientCode").val();
                $.post(
                    "${pageContext.request.contextPath}/admin/login",
                    "username="+usernameVal+"&password="+passwordVal+"&clientCode="+clientCodeVal,
                    function (message){
                        if(message=="登陆成功"){
                            location.href="${pageContext.request.contextPath}/jsp/main.jsp";
                        }else{
                            $("#messageVal").text(message);
                        }
                    },
                    "json"
                );
            });
        });
    </script>
</head>
<body style=" background: url(../imag/slide1.png); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
            <div id="messageVal"></div>
        </div>
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/user/login">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input id="username" type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input id="password" type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <input  id="clientCode" class="form-control" placeholder="验证码" autocomplete="off" name="clientCode">
                <img id="imgVcode" src="${pageContext.request.contextPath}/admin/serverCode" class="Ucc_captcha Captcha-image" click="changeImage()">
            </div>
            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" click="login()">登录</button>
            </div>
        </div>
        </form>
    </div>
</div>
</body>
</html>
