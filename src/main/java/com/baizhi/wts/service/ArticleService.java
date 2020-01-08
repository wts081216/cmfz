package com.baizhi.wts.service;

import com.baizhi.wts.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //添加
    public void add(Article article);
    //删除
    public void delete(List<String> id);
    //修改
    public void update(Article article);
    //分页查
    public Map queryBypage(Integer page, Integer rows);
    //查所有
    public List<Article> queryAll();
}
