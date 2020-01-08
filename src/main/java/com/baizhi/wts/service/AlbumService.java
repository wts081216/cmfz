package com.baizhi.wts.service;

import com.baizhi.wts.entity.Album;
import com.baizhi.wts.entity.Article;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //批量删除
    public void deletes(List<String> id);
    //查一个
   // public Album qurryOne(String id);
    //添加
    public void insert(Album album);
    //修改
    public void update(Album album);
    //分页查
    public Map quryBypage(Integer page, Integer rows);
    //查所有
    public List<Album> queryAll();
}
