package com.baizhi.wts.service;

import com.baizhi.wts.annotation.LogAnnotation;
import com.baizhi.wts.dao.BannerDao;
import com.baizhi.wts.dao.BannerPageDto;
import com.baizhi.wts.entity.Admin;
import com.baizhi.wts.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;
    //删除
    @Override
    @LogAnnotation(value = "轮播图删除")
    public void deletes(List<String> id) {
        bannerDao.deleteByIdList(id);
    }
    //添加
    @Override
    @LogAnnotation(value = "轮播图添加")
    public void insert(Banner banner) {
        banner.setCreateDate(new Date());
        bannerDao.insert(banner);
    }
    //修改
    @Override
    @LogAnnotation(value = "轮播图修改")
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }
   //分页查
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public BannerPageDto quryBypage(Integer pageSize,Integer curPage) {
        BannerPageDto dto = new BannerPageDto();
        // 调用dao实现每个步骤
        dto.setPage(curPage);// 设置当前页
        List<Banner> banners = bannerDao.selectByRowBounds(null, new RowBounds((curPage-1)*pageSize,pageSize));

        int totalCount = bannerDao.selectCount(null);
        dto.setRecords(totalCount);// 设置总行数
        dto.setTotal(totalCount%pageSize==0? totalCount/pageSize : totalCount/pageSize+1);// 设置总页
        dto.setRows(banners);// 设置当前页的数据行
        return dto;
    }

    @Override
    //查一个
    public Banner queryOne(String id) {
        return bannerDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Banner> queryAll() {
        return bannerDao.selectAll();
    }

    @Override
    //按时间降序查询前5个
    public List<Banner> queryBannerBytime() {
        List<Banner> banners = bannerDao.queryBannerBytime();
        return banners;
    }
}
