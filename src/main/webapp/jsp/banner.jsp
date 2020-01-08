<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    //页面加载
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/banner/queryByPage",
                styleUI: 'Bootstrap',
                cellEdit: false,//开启单元格编辑
                autowidth: true,//自适应父容器
                height:450,
                editurl: "${pageContext.request.contextPath}/banner/multi",
                datatype : "json",
                colNames : [ 'id', '标题', '图片', '链接', '创建时间','描述', '状态' ],
                colModel : [
                    {name : 'id',index : 'id',width : 90,hidden:true},
                    {name : 'title',index : 'title',width : 90, editable: true,align : "center",editrules:{required:true}},
                    {name : 'url',editable:true,index : 'invdate',width : 90,formatter:showPicture,edittype:'file',
                        editoptions:{enctype:"multipart/form-data"}},
                    {name : 'href',index : 'href',width : 80,align : "center", editable: true},
                    {name : 'createDate',editrules:{required:true},index : 'create_date',width : 120,align : "center"},
                    {name : 'description',editrules:{required:true},index : 'descript',width : 100,align : "center", editable: true},
                    {name : 'status',editrules:{required:true},editable:true,align:"center",formatter:changestatus,edittype:'select', editoptions: {value: {1:'正常', 0:'冻结'}}}

                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#pager',
                sortname : "id",
                mtype : "post",
                viewrecords : true,
                multiselect:true,
                sortorder : "desc"
                // caption : "轮播图模块数据管理"
            }).jqGrid('navGrid', '#pager',
                {edit : true, add : true, del : true,edittext:"编辑",addtext:"添加",deltext:"删除"},
                {
                    closeAfterEdit: true,
                    afterSubmit:function (response,postData) {
                        var bannerId = response.responseJSON.bannerId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url:"${pageContext.request.contextPath}/banner/uploadBanner",
                            type:"post",
                            datatype:"json",
                            //发送添加图片的id至controller
                            data:{bannerId:bannerId},
                            //指定上传的input框id
                            fileElementId:"url",
                            success:function (data) {
                                //取消表格选中的记录
                                $("#bannerTable").resetSelection();
                                //刷新整个列表
                                $("#bannerTable").trigger("reloadGrid");
                            }
                        });
                        //防止页面报错
                        return postData;
                    }
                },
                {closeAfterAdd: true,
                    // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                    //                   editurl 完成后 返回值信息
                    afterSubmit:function (response,postData) {
                        var bannerId = response.responseJSON.bannerId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url:"${pageContext.request.contextPath}/banner/uploadBanner",
                            type:"post",
                            datatype:"json",
                            //发送添加图片的id至controller
                            data:{bannerId:bannerId},
                            //指定上传的input框id
                            fileElementId:"url",
                            success:function (data) {
                                //取消表格选中的记录
                                $("#bannerTable").resetSelection();
                                //刷新整个列表
                                $("#bannerTable").trigger("reloadGrid");
                            }
                        });
                        //防止页面报错
                        return postData;
                    }
                });

        function showPicture(cellvalue){
            return "<img src='${pageContext.request.contextPath}/upload/img/" +cellvalue + "' height='72' width='150'/>";
        }
        function changestatus(status) {

            if (status == "1"){
                status = "正常";
            }else {
                status = "冻结";
            }
            return status;
        }
        //给导入轮播图信息注册单击事件
        $("#myBannerModal").click(function () {
            $("#myModal").modal("show");
        });
        //给导入提交按钮注册单击事件
        $("#submitBtn").click(function () {
           $.ajaxFileUpload({
               url: "${pageContext.request.contextPath}/banner/bannerIn",
               type: "post",
               datatypee: "json",
               fileElementId:"imag",
               success:function () {
                   //取消表格选中的记录
                   $("#bannerTable").resetSelection();
                   //刷新整个列表
                   $("#bannerTable").trigger("reloadGrid");
                   $("#myModal").modal("hide");
               }

           })
        })

    });
</script>
<div class="page-header">
    <h4>轮播图管理</h4>
    <ul class="nav nav-tabs">
        <li><a>轮播图信息</a></li>
        <li><a href="${pageContext.request.contextPath}/banner/bannerOut">导出轮播图信息</a></li>
        <li><a id="myBannerModal">导入轮播图信息</a></li>
        <li><a href="${pageContext.request.contextPath}/banner/bannerMould">Excel模板下载</a></li>
    </ul>
</div>
<table id="bannerTable"></table>
<div id="pager" style="height: 50px"></div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">选择文件</h4>
            </div>
            <div class="form-group">
                <label for="imag">文件输入</label>
                <input type="file" id="imag" name="imag">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="submitBtn">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>