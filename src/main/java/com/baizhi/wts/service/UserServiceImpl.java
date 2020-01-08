package com.baizhi.wts.service;

import com.baizhi.wts.dao.UserDao;
import com.baizhi.wts.entity.MapDto;
import com.baizhi.wts.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    //添加
    public void add(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        userDao.insert(user);
    }

    @Override
    //删除
    public void delete(List<String> id) {
        userDao.deleteByIdList(id);
    }

    @Override
    //修改
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    //分页查
    public Map queryByPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //查询总条数
        Integer records = userDao.selectCount(null);
        //查询总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<User> users = userDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        hashMap.put("rows", users);
        return hashMap;
    }

    @Override
    //根据用户注册时间，性别 进行查询
    public Integer queryUserByTime(String sex, Integer day) {
        Integer integer = userDao.queryUserByTime(sex, day);
        return integer;
    }

    @Override
    //根据地区进行查询
    public List<MapDto> queryByLocation(String sex) {
        List<MapDto> mapDtos = userDao.queryByLocation(sex);
        return mapDtos;
    }

    @Override
    public Map queryByPhone(String phone, String password) {
        String message;
        HashMap hashMap = new HashMap();
        User user1 = new User(null,phone,null,null,null,null,null,null,null,null,null,null,null );
        User user2 = userDao.selectOne(user1);
        //判断该人是否存在
        if (user2 != null){
            //该人存在  判断密码是否正确
            if(user2.getPassword().equals(password)){
                message = "登陆成功";
                hashMap.put("status","200");
                hashMap.put("user",user2);
            } else{
                //密码输入错误
                message = "密码输入错误";
                hashMap.put("status","-200");
                hashMap.put("message",message);
            }
        }else{
            //该人不存在
            message = "该用户不存在";
            hashMap.put("status","-200");
            hashMap.put("message",message);
        }
        return hashMap;
    }

    @Override
    //查一个
    public User queryOne(User user) {
        HashMap hashMap = new HashMap();
         return  userDao.selectOne(user);
    }

   @Override
    //随机查询5人
    public List<User> queryRandom(String uid) {
       List<User> users = userDao.queryRandom(uid);
       return users;
    }

}

