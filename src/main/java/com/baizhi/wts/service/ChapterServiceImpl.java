package com.baizhi.wts.service;

import com.baizhi.wts.dao.ChapterDao;
import com.baizhi.wts.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;
    @Override
    //删除
    public void deletes(List<String> id) {
        chapterDao.deleteByIdList(id);
    }

    @Override
    //查一个
    public Chapter qurryOne(String id) {
        return chapterDao.selectByPrimaryKey(id);
    }

    @Override
    //添加
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    //修改
    public void update(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    //分页查
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map quryBypage(Integer page, Integer rows,Chapter chapter) {
        //page当前页 ；rows 每页展示的数据条数 ； records 一共多少条数据；total 总页数
        HashMap hashMap = new HashMap();
        Integer records = chapterDao.selectCount(null);//不传入任何数据查所有
        //计算总页数
        Integer total = records%rows == 0 ? records/rows : records/rows+1;
        System.out.println("chapter = " + chapter);
        List chapters = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        hashMap.put("page",page);
        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("rows",chapters);
        return  hashMap;
    }
}
