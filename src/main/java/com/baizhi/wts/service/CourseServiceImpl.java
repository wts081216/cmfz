package com.baizhi.wts.service;

import com.baizhi.wts.dao.CourseDao;
import com.baizhi.wts.entity.Course;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseDao courseDao;

    @Override
    //添加
    public void add(Course course) {
        course.setCreateDate(new Date());
        courseDao.insert(course);
    }

    @Override
    //删除
    public void delete(List<String> id) {
        courseDao.deleteByIdList(id);
    }

    @Override
    //修改
    public void update(Course course) {
        courseDao.updateByPrimaryKeySelective(course);
    }

    @Override
    //分页查
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryBypage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //查询总行数
        Integer records = courseDao.selectCount(null);
        //计算总页数
        Integer total = records%rows == 0 ? records/rows : records/rows+1;
        List<Course> courses = courseDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("rows",courses);
        hashMap.put("total",total);
        return hashMap;
    }

    @Override
    //查所有
    public List<Course> queryAll() {
        return courseDao.selectAll();
    }

    @Override
    //根据id进行查询
    public List<Course> queryById(String uid) {
        Course course = new Course();
        course.setId(uid);
        List<Course> select = courseDao.select(course);
        return select;
    }
}
