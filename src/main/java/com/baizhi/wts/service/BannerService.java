package com.baizhi.wts.service;

import com.baizhi.wts.dao.BannerPageDto;
import com.baizhi.wts.entity.Banner;
import java.util.List;

public interface BannerService {
    //删除
    public void deletes(List<String> id);
    //添加
    public void insert(Banner banner);
    //修改
    public void update(Banner banner);
    //分页查
    public BannerPageDto quryBypage(Integer pageSize, Integer curPage);
    //查一个
    public Banner queryOne(String id);
    //查所有
    public List<Banner> queryAll();
    //按时间降序查询5篇
    public List<Banner> queryBannerBytime();
}
