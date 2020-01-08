<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    //页面加载
    $(function () {
        $("#guruTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/guru/quryBypage",
                styleUI: 'Bootstrap',
                cellEdit: false,//开启单元格编辑
                autowidth: true,//自适应父容器
                height:450,
                editurl: "${pageContext.request.contextPath}/guru/edit",
                datatype : "json",
                colNames : [ 'id', '上师名称', '图片', '法号','状态' ],
                colModel : [
                    {name : 'id',index : 'id',hidden:true},
                    {name : 'name',index : 'name',editable: true,align : "center",editrules:{required:true}},
                    {name : 'photo',align:"center",formatter:function (data) {
                            return "<img style='height: 80px;width: 90px' src='"+data+"'>";
                        },editable:true,edittype: "file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'nickName',index : 'nickName',editable: true,align : "center",editrules:{required:true}},
                    {name : 'status',editrules:{required:true},editable:true,align:"center",formatter:changestatus,edittype:'select', editoptions: {value: {1:'正常', 0:'冻结'}}}

                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#pager',
                sortname : "id",
                mtype : "post",
                viewrecords : true,
                multiselect:true,
                //sortorder : "desc"
                // caption : "轮播图模块数据管理"
            }).jqGrid('navGrid', '#pager',
                {edit : true, add : true, del : true,edittext:"编辑",addtext:"添加",deltext:"删除"},
                {
                    closeAfterEdit: true,
                    afterSubmit:function (response,postData) {
                        var guruId = response.responseJSON.id;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url:"${pageContext.request.contextPath}/guru/upload",
                            type:"post",
                            datatype:"json",
                            //发送添加图片的id至controller
                            data:{guruId:guruId},
                            //指定上传的input框id
                            fileElementId:"photo",
                            success:function (data) {
                                //取消表格选中的记录
                                $("#guruTable").resetSelection();
                                //刷新整个列表
                                $("#guruTable").trigger("reloadGrid");
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
                        var guruId = response.responseJSON.guruId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url:"${pageContext.request.contextPath}/guru/upload",
                            type:"post",
                            datatype:"json",
                            //发送添加图片的id至controller
                            data:{guruId:guruId},
                            //指定上传的input框id
                            fileElementId:"photo",
                            success:function (data) {
                                //取消表格选中的记录
                                $("#guruTable").resetSelection();
                                //刷新整个列表
                                $("#guruTable").trigger("reloadGrid");
                            }
                        });
                        //防止页面报错
                        return postData;
                    }
                });
        function changestatus(status) {

            if (status == "1"){
                status = "正常";
            }else {
                status = "冻结";
            }
            return status;
        }
    });
</script>
<div class="page-header">
    <h4>上师管理</h4>
</div>
<table id="guruTable"></table>
<div id="pager" style="height: 50px"></div>