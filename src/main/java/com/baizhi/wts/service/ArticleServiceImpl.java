package com.baizhi.wts.service;

import com.baizhi.wts.dao.ArticleDao;
import com.baizhi.wts.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    ArticleDao  articleDao;

    @Override
    //添加
    public void add(Article article) {
        String uuid = UUID.randomUUID().toString();
        article.setId(uuid);
        article.setCreateDate(new Date());
        articleDao.insert(article);
    }

    @Override
    //删除
    public void delete(List<String> id) {
        articleDao.deleteByIdList(id);
    }

    @Override
    //修改
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    //分页查
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryBypage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        //查询总行数
        Integer records = articleDao.selectCount(null);
        //计算总页数
        Integer total = records%rows == 0 ? records/rows : records/rows+1;
        List<Article> articles = articleDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records",records);
        hashMap.put("page",page);
        hashMap.put("rows",articles);
        hashMap.put("total",total);
        return hashMap;
    }

    @Override
    //查所有
    public List<Article> queryAll() {
        return articleDao.selectAll();
    }
}
