package com.baizhi.wts.service;


import com.baizhi.wts.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    //添加
    public void add(Course course);
    //删除
    public void delete(List<String> id);
    //修改
    public void update(Course course);
    //分页查
    public Map queryBypage(Integer page, Integer rows);
    //查所有
    public List<Course> queryAll();
    //根据id进行查询
    public List<Course> queryById(String uid);

}
