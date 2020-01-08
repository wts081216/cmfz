<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
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
        $(function () {
            $("#albumTable").jqGrid(
                {
                    url: "${pageContext.request.contextPath}/album/queryBypage",
                    editurl: "${pageContext.request.contextPath}/album/editAlbum",
                    datatype: "json",
                    height: 400,
                    autowidth: true,
                    styleUI: "Bootstrap",
                    colNames: ['id', '标题', '分数', '作者', '播音', '内容数量', '内容简介', '状态', '创建日期','封面'],
                    colModel: [
                        {name: 'id', align: "center",hidden: true},
                        {name: 'title', editable: true, width: 90, align: "center"},
                        {name: 'score', editable: true, width: 100, align: "center"},
                        {name: 'author', editable: true, width: 80, align: "center"},
                        {name: 'broadcast', editable: true, width: 80, align: "center"},
                        {name: 'count', editable: true, width: 80, align: "center"},
                        {name: 'description', editable: true, width: 150, sortable: false},
                        {
                            name: 'status',
                            editable: true,
                            width: 150,
                            sortable: false,
                            align: "center",
                            edittype: 'select',
                            editoptions: {value: "1:启用; 0:冻结"},
                            formatter: function (data) {
                                if (data == "1")
                                    return "启用";
                                else
                                    return "冻结";
                            }
                        },
                        {name: 'createDate', width: 150, sortable: false, align: "center"},
                        {name : 'cover',align:"center",formatter:function (data) {
                                return "<img style='height: 80px;width: 90px' src='"+data+"'>";
                            },editable:true,edittype: "file",editoptions:{enctype:"multipart/form-data"}}
                    ],
                    rowNum: 10,
                    rowList: [10, 20, 30, 40],
                    pager: '#albumPage',
                    sortname: 'id',
                    viewrecords: true,
                    sortorder: "desc",
                    multiselect: true,
                    subGrid: true,
                    // caption : "Grid as Subgrid",
                    subGridRowExpanded: function (subgrid_id, row_id) {
                        var subgrid_table_id, pager_id;
                        subgrid_table_id = subgrid_id + "_t";
                        pager_id = "p_" + subgrid_table_id;
                        $("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='" + pager_id + "' class='scroll'></div>");
                        $("#" + subgrid_table_id).jqGrid(
                            {
                                url: "${pageContext.request.contextPath}/chapter/queryByPage?albumId=" + row_id,
                                editurl: "${pageContext.request.contextPath}/chapter/edit?albumId=" + row_id,
                                datatype: "json",
                                colNames: ['id', '标题', '音频大小', '音频时长', '创建时间', '所属专辑','操作'],
                                colModel: [
                                    {name: "id", width: 80, key: true, align: "center", hidden: true},
                                    {name: "title", index: "item", align: "center", editable: true},
                                    {name: "size", index: "unit", align: "center"},
                                    {name: "time", index: "total", align: "center", sortable: false},
                                    {
                                        name: "createTime",
                                        index: "total",
                                        align: "center",
                                        sortable: false,
                                        editable: true,
                                        edittype: "date"
                                    },
                                    {name: "albumId", index: "total", align: "center", sortable: false, hidden: true},
                                   /* {
                                        name: "url", index: "qty", width: 70, align: "center", editable: true,
                                        edittype: "file", editoptions: {enctype: "multipart/form-data"}
                                    },*/
                                    {name : "url",formatter:function (cellvalue, options, rowObject) {
                                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>&nbsp;&nbsp;";
                                            //  声明一个onPlay方法 --> 显示模态框 ---> 为audio标签添加src  需要url路径作为参数传递
                                            //  'onPlay(参数)' ---> \"onPlay('"+cellvalue+"')\"
                                            button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"onPlay('"+cellvalue+"')\">播放</button>";
                                            return button;
                                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}
                                    }
                                ],
                                rowNum: 10,
                                rowList: [10, 20, 30, 40],
                                viewrecords: true,
                                autowidth: true,
                                pager: pager_id,
                                styleUI: "Bootstrap",
                                multiselect: true,
                                sortname: 'num',
                                sortorder: "asc",
                                height: '100%'
                            });
                        $("#" + subgrid_table_id).jqGrid('navGrid', "#" + pager_id, {
                            add: true,
                            edit: true,
                            del: true,
                            addtext: "添加",
                            edittext: "编辑",
                            deltext: "删除"
                        }, {
                            closeAfterEdit: true,
                            afterSubmit: function (response, postData) {
                                var chapterId = response.responseJSON.chapterId;
                                $.ajaxFileUpload({
                                    //指定上传路径
                                    url: "${pageContext.request.contextPath}/chapter/upload",
                                    type: "post",
                                    datatype: "json",
                                    //发送添加图片的id至controller
                                    data: {chapterId: chapterId},
                                    //指定上传的input框id
                                    fileElementId: "url",
                                    success: function (data) {
                                        //取消表格选中的记录
                                        $("#" + subgrid_table_id).resetSelection();
                                        //刷新整个列表
                                        $("#" + subgrid_table_id).trigger("reloadGrid");
                                    }
                                });
                                //防止页面报错
                                return postData;
                            }
                        },
                        {
                            closeAfterAdd: true,
                            afterSubmit:function (response,postData) {
                                var chapterId = response.responseJSON.chapterId;
                                $.ajaxFileUpload({
                                    //指定上传路径
                                    url:"${pageContext.request.contextPath}/chapter/upload",
                                    type:"post",
                                    datatype:"json",
                                    //发送添加图片的id至controller
                                    data:{chapterId:chapterId},
                                    //指定上传的input框id
                                    fileElementId:"url",
                                    success:function (data) {
                                        //取消表格选中的记录
                                        $("#" + subgrid_table_id).resetSelection();
                                        //刷新整个列表
                                        $("#" + subgrid_table_id).trigger("reloadGrid");
                                    }
                                });
                                //防止页面报错
                                return postData;
                            }
                        },
                        {
                            closeAfterDel: true,
                        });
                    },
                    subGridRowColapsed: function (subgrid_id, row_id) {

                    }
                });
            $("#albumTable").jqGrid('navGrid', '#albumPage', {
                add: true,
                edit: true,
                del: true,
                addtext: "添加",
                edittext: "编辑",
                deltext: "删除"
            }, {
                closeAfterEdit: true,
                // 进行上传 上传完成后需更改url路径 需要获取添加的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var albumId = response.responseJSON.id;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url:"${pageContext.request.contextPath}/album/uploadCover",
                        type:"post",
                        datatype:"json",
                        //发送添加图片的id至controller
                        data:{albumId:albumId},
                        //指定上传的input框id
                        fileElementId:"cover",
                        success:function (data) {
                            //取消表格选中的记录
                            $("#albumTable").resetSelection();
                            //刷新整个列表
                            $("#albumTable").trigger("reloadGrid");
                        }
                    });
                    //防止页面报错
                    return postData;
                }
            }, {
                closeAfterAdd: true,
                // 进行上传 上传完成后需更改url路径 需要获取添加的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var albumId = response.responseJSON.id;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url:"${pageContext.request.contextPath}/album/uploadCover",
                        type:"post",
                        datatype:"json",
                        //发送添加图片的id至controller
                        data:{albumId:albumId},
                        //指定上传的input框id
                        fileElementId:"cover",
                        success:function (data) {
                            //取消表格选中的记录
                            $("#albumTable").resetSelection();
                            //刷新整个列表
                            $("#albumTable").trigger("reloadGrid");
                        }
                    });
                    //防止页面报错
                    return postData;
                }
            }, {
                closeAfterDel: true,
            });

        });
        function onPlay(cellValue) {
            $("#music").attr("src",cellValue);
            $("#myModal").modal("show");
        }
        function download(cellValue) {
            location.href = "${pageContext.request.contextPath}/chapter/download?url="+cellValue;
        }
    </script>
</head>

<table id="albumTable"></table>
<div id="albumPage" style="height: 50px"></div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->
</div>