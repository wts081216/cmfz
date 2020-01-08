package com.baizhi.wts.service;

import com.baizhi.wts.entity.Album;
import com.baizhi.wts.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    //批量删除
    public void deletes(List<String> id);
    //查一个
    public Chapter qurryOne(String id);
    //添加
    public void insert(Chapter chapter);
    //修改
    public void update(Chapter chapter);
    //分页查
    public Map quryBypage(Integer page, Integer rows,Chapter chapter);
}
