package com.baizhi.wts.service;

import com.baizhi.wts.dao.GuruDao;
import com.baizhi.wts.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class GuruServiceImpl implements GuruService  {
    @Autowired
    GuruDao guruDao;
    @Override
    //删除
    public void deletes(List<String> id) {
        guruDao.deleteByIdList(id);
    }

    @Override
    //添加
    public void insert(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    //修改
    public void update(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }

    @Override
    //分页查
    public Map quryBypage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //计算总条数
        Integer records = guruDao.selectCount(null);
        //计算总页数
        Integer total = records%rows == 0 ? records/rows : records/rows+1;
        List<Guru> gurus = guruDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("rows",gurus);
        hashMap.put("total",total);
        return hashMap;
    }

    @Override
    //查所有
    public List<Guru> quryAll() {
        return guruDao.selectAll();
    }
}
