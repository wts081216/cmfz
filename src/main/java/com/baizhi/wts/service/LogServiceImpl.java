package com.baizhi.wts.service;

import com.baizhi.wts.dao.LogDao;
import com.baizhi.wts.entity.Log;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class LogServiceImpl implements LogService{
    @Autowired
    LogDao logDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    //分页查
    public Map queryByPage(Integer rows, Integer page) {
        HashMap hashMap = new HashMap();
        //page当前页 ；rows 每页展示的数据条数 ； records 一共多少条数据；total 总页数
        Integer records = logDao.selectCount(null);
        //计算总页数
        Integer total = records%rows == 0 ? records/rows : records/rows+1;
        List<Log> logs = logDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("rows",logs);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("records",records);
        return hashMap;
    }

    @Override
    //添加
    public void insert(Log log) {
        String id = UUID.randomUUID().toString();
        log.setId(id);
        log.setDate(new Date());
        logDao.insert(log);
    }

    @Override
    //删除
    public void deletes(List<String> id) {
        logDao.deleteByIdList(id);
    }
    //修改
    @Override
    public void update(Log log) {
        logDao.updateByPrimaryKeySelective(log);
    }
}
