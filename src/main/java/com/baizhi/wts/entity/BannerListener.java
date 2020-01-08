package com.baizhi.wts.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.wts.service.BannerService;
import com.baizhi.wts.util.SpringBeanUtil;

import java.util.ArrayList;
import java.util.List;

/*
    1. 创建一个Listener类 继承 AnalysisEventListener<实体类>
    2. 重写方法
    invoke : 读取每行数据后会执行的方法
    doAfterAllAnalysed: 所有数据读取完毕后执行的方法
 */
public class BannerListener extends AnalysisEventListener<Banner> {

    @Override
    //banner 读取每行的数据进行封装
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        BannerService bannerService = (BannerService) SpringBeanUtil.getBeanByClass(BannerService.class);
        bannerService.insert(banner);
        System.out.println("banner ================================ " + banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
