package com.baizhi.wts.service;

import com.baizhi.wts.entity.Log;

import java.util.List;
import java.util.Map;

public interface LogService {
    //分页查
    public Map queryByPage(Integer pageSize, Integer curPage);
    //添加
    public void insert(Log log);
    //删除
    public void deletes(List<String> id);
    //修改
    public void update(Log log);

}
