package com.baizhi.wts.dao;

import com.baizhi.wts.entity.MapDto;
import com.baizhi.wts.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    Integer queryUserByTime(@Param("sex") String sex,@Param("day") Integer day);
    List<MapDto> queryByLocation(String sex);
    //随机查询5人
    List<User> queryRandom(String uid);
}
