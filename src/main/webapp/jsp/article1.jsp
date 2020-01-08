<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid(
            {
                url: '${pageContext.request.contextPath}/article/queryBypage',
                datatype: "json",
                // 时间格式的处理在后台进行
                colNames: ['ID', '标题', '图片', '内容', '创建时间', '状态', '所属上师', '操作'],
                colModel: [
                    {name: 'id', align: "center", hidden: true},
                    {name: 'title', align: "center", editable: true, editrules: {required: true}},
                    {
                        name: 'img', align: "center", formatter: function (data) {
                            return "<img style='width: 180px;height: 80px' src='" + data + "'>"
                        }, editable: true, edittype: "file", editoptions: {enctype: "multipart/form-data"}
                    },
                    {name: 'content', align: "center", editable: true},
                    {
                        name: 'createDate',
                        align: "center",
                        editable: true,
                        editrules: {required: true},
                        edittype: "date"
                    },
                    {
                        name: 'status',
                        align: "center",
                        formatter: function (data) {
                            if (data == "1") {
                                return "展示";
                            } else return "冻结";
                        },
                        editable: true,
                        editrules: {required: true},
                        edittype: "select",
                        editoptions: {value: "1:展示;2:冻结"}
                    },
                    {
                        name: 'guruId',
                        align: "center",
                    },
                    {
                        name: 'option',
                        formatter: function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('" + rowObject.id + "')\">修改</button>&nbsp;&nbsp;";
                            button += "<button type=\"button\" class=\"btn btn-danger\" onclick=\"del('" + rowObject.id + "')\">删除</button>";
                            return button;
                        }
                    }
                ],
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#articlePage',
                sortname: 'id',
                mtype: "post",
                viewrecords: true,
                sortorder: "desc",
                caption: "轮播图信息",
                autowidth: true,
                multiselect: true,
                styleUI: "Bootstrap",
                height: "500px",
                editurl: "${pageContext.request.contextPath}/article/edit"
            }).jqGrid('navGrid', '#articlePage',
            {edit: false, add: false, del: true, edittext: "编辑", addtext: "添加", deltext: "删除"});
    });

    // 点击添加文章时触发事件
    function showArticle() {
        $("#kindForm")[0].reset();
        KindEditor.html("#editor_id", "");
        $.ajax({
            url: "${pageContext.request.contextPath}/guru/queryAll",
            datatype: "json",
            type: "post",
            success: function (data) {
                var option = "<option value=\"0\">请选择所属上师</option>";
                data.forEach(function (guru) {
                    option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option);
            }
        });
        $("#myModal").modal("show");
    }

    // 点击修改时触发事件
    function update(id) {
        var data = $("#articleTable").jqGrid("getRowData", id);
        $("#id").val(data.id);
        $("#title").val(data.title);
        KindEditor.html("#editor_id", data.content)
        $("#status").val(data.status);
        var option = "";
        if (data.status == "展示") {
            option += "<option selected value=\"1\">展示</option>";
            option += "<option value=\"2\">冻结</option>";
        } else {
            option += "<option value=\"1\">展示</option>";
            option += "<option selected value=\"2\">冻结</option>";
        }
        $("#status").html(option);

        $.ajax({
            url: "${pageContext.request.contextPath}/guru/queryAll",
            datatype: "json",
            type: "post",
            success: function (gurulist) {
                var option2 = "<option value=\"0\">请选择所属上师</option>";
                gurulist.forEach(function (guru) {
                    if (guru.id == data.guruId) {
                        option2 += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                    }
                    option2 += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option2);
            }
        });
        $("#myModal").modal("show");
    }

    // 文件添加及修改方法
    function sub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/insertArticle",
            type: "post",
            data: {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "content": $("#editor_id").val(),
                "status": $("#status").val(),
                "guruId": $("#guru_list").val()
            },
            datatype: "json",
            fileElementId: "inputfile",
            success: function (data) {
                //取消表格选中的记录
                $("#articleTable").resetSelection();
                //刷新整个列表
                $("#articleTable").trigger("reloadGrid");
            }
        })
    }
</script>
<div class="page-header">
    <h4>文章管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a onclick="showArticle()">添加文章</a></li>
</ul>
<div class="panel">
    <table id="articleTable"></table>
    <div id="articlePage" style="height: 50px"></div>
</div>

