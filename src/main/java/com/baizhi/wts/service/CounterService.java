package com.baizhi.wts.service;


import com.baizhi.wts.entity.Counter;


import java.util.List;
import java.util.Map;

public interface CounterService {
    //添加
    public void add(Counter counter);
    //删除
    public void delete(List<String> id);
    //修改
    public void update(Counter counter);
    //分页查
    public Map queryBypage(Integer page, Integer rows);
    //查所有
    public List<Counter> queryAll();
    //根据id进行查询
    public List<Counter> queryById(String uid);

}
