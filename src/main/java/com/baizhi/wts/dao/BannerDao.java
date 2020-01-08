package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends Mapper<Banner>, DeleteByIdListMapper<Banner,String> {
    List<Banner> queryBannerBytime();
}
