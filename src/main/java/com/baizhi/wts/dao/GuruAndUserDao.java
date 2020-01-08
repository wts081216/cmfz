package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Guruanduser;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;



public interface GuruAndUserDao extends Mapper<Guruanduser>, DeleteByIdListMapper<Guruanduser,String> {

}
