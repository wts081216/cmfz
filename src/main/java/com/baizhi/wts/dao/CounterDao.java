package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Counter;
import com.baizhi.wts.entity.Course;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao extends Mapper<Counter>, DeleteByIdListMapper<Counter,String> {

}
