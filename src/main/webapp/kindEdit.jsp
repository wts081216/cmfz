<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="./boot/css/back.css">
    <link rel="stylesheet" href="./jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="./jqgrid/css/jquery-ui.css">
    <script src="./boot/js/jquery-2.2.1.min.js"></script>
    <script src="./boot/js/bootstrap.min.js"></script>
    <script src="./jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="./jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="./boot/js/ajaxfileupload.js"></script>
    <script src="./kindeditor/kindeditor-all-min.js"></script>
    <script src="./kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript">
        KindEditor.ready(function(K) {
            //K.create中存放textarea的Id
            //如需使用自定义配置 在id后使用,{}形式声明
            window.editor = K.create('#editor_id',{
                width : '700px'
            }
            ,{
                uploadJson : '${pageContext.request.contextPath}/article/upload',
                fileManagerJson : '../jsp/file_manager_json.jsp',//图片管理url
                allowFileManager : true//允许图片管理
                });
        });

    </script>

</head>
<body>
    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
    &lt;strong&gt;HTML内容&lt;/strong&gt;
    </textarea>
</body>