package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Log;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface LogDao extends Mapper<Log>,DeleteByIdListMapper<Log,String>{
}
