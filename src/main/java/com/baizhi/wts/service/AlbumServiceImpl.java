package com.baizhi.wts.service;

import com.baizhi.wts.dao.AlbumDao;
import com.baizhi.wts.dao.ChapterDao;
import com.baizhi.wts.entity.Album;
import com.baizhi.wts.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ChapterDao chapterDao;
    @Override
    //删除
    public void deletes(List<String> id) {
        albumDao.deleteByIdList(id);
    }

    @Override
    //添加
    public void insert(Album album) {
        album.setCreateDate(new Date());
        albumDao.insert(album);
    }

    @Override
    //修改
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    //分页查
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map quryBypage(Integer page, Integer rows) {
        //page当前页 ；rows 每页展示的数据条数 ； records 一共多少条数据；total 总页数
        HashMap hashMap = new HashMap();
        Integer records = albumDao.selectCount(null);//不传入任何数据查所有
        //计算总页数
        Integer total = records%rows == 0 ? records/rows : records/rows+1;
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        for (Album album1 : albums) {
            Chapter chapter = new Chapter();
            chapter.setAlbumId(album1.getId());
            Integer count = chapterDao.selectCount(chapter);
            album1.setCount(count);
        }
        hashMap.put("page",page);
        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("rows",albums);
        return  hashMap;
    }

    @Override
    //查所有
    public List<Album> queryAll() {
        return albumDao.selectAll();
    }

}
