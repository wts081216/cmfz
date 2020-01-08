<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    //页面加载
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/log/queryByPage",
                styleUI: 'Bootstrap',
                cellEdit: false,//开启单元格编辑
                autowidth: true,//自适应父容器
                height:450,
                datatype : "json",
                colNames: ["id", "执行人员", "执行操作","执行时间", "执行结果"],   //表格标题
                colModel : [
                    {name : 'id',index : 'id',width : 90},
                    {name : 'name',index : 'title',width : 90,align : "center",editrules:{required:true}},
                    {name : 'thing',editrules:{required:true},index : 'create_date',width : 120,align : "center"},
                    {name : 'date',editrules:{required:true},index : 'descript',width : 100,align : "center"},
                    {name : 'flag',editrules:{required:true},index : 'descript',width : 100,align : "center"}
                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#pager',
                sortname : "id",
                mtype : "post",
                viewrecords : true,
                multiselect:true,
                sortorder : "desc",
                caption : "轮播图模块数据管理"
            }).jqGrid('navGrid', '#pager', {edit : false,add : false,del : true});
    });
</script>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<table id="bannerTable"></table>
<div id="pager" style="height: 50px"></div>