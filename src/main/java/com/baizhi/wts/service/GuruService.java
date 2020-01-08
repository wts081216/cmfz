package com.baizhi.wts.service;

import com.baizhi.wts.entity.Album;
import com.baizhi.wts.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    //批量删除
    public void deletes(List<String> id);
    //添加
    public void insert(Guru guru);
    //修改
    public void update(Guru guru);
    //分页查
    public Map quryBypage(Integer page, Integer rows);
    //查所有
    public List<Guru> quryAll();
}
