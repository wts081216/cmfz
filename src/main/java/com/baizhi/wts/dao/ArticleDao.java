package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {

}
