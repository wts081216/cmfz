package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Guru;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface GuruDao extends Mapper<Guru>, DeleteByIdListMapper<Guru,String> {
}
