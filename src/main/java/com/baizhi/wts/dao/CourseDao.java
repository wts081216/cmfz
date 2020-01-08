package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Course;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CourseDao extends Mapper<Course>, DeleteByIdListMapper<Course,String> {

}
