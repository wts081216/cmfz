package com.baizhi.wts.service;



import com.baizhi.wts.dao.CounterDao;

import com.baizhi.wts.entity.Counter;

import com.baizhi.wts.entity.Course;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CounterServiceImpl implements CounterService{
    @Autowired
    CounterDao counterDao;

    @Override
    //添加
    public void add(Counter counter) {
        counter.setCreateDate(new Date());
        counterDao.insert(counter);
    }


    @Override
    //删除
    public void delete(List<String> id) {
        counterDao.deleteByIdList(id);
    }


    @Override
    //修改
    public void update(Counter counter) {
        counterDao.updateByPrimaryKeySelective(counter);
    }

    @Override
    //分页查
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryBypage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //查询总行数
        Integer records = counterDao.selectCount(null);
        //计算总页数
        Integer total = records%rows == 0 ? records/rows : records/rows+1;
        List<Counter> counters = counterDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("rows",counters);
        hashMap.put("total",total);
        return hashMap;
    }

    @Override
    //查所有
    public List<Counter> queryAll() {
        List<Counter> counters = counterDao.selectAll();
        return counters;
    }

    @Override
    //根据id进行查询
    public List<Counter> queryById(String uid) {
        Counter counter = new Counter();
        counter.setId(uid);
        List<Counter> select = counterDao.select(counter);
        return select;
    }
}
