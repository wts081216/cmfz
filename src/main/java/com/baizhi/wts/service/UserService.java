package com.baizhi.wts.service;

import com.baizhi.wts.entity.MapDto;
import com.baizhi.wts.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //添加
    public void add(User user);
    //删除
    public void delete(List<String> id);
    //修改
    public void update(User user);
    //分页查
    public Map queryByPage(Integer page,Integer rows);
    //根据用户注册时间，性别 进行查询
    public Integer queryUserByTime(String sex,Integer day);
    //根据地区进行查询
    public List<MapDto> queryByLocation(String sex);
    //根据手机号进行查询
    public Map queryByPhone(String phone,String password);
    //查一个
    public User queryOne(User user);
    //随机查询5人
    public List<User> queryRandom(String uid);

}
