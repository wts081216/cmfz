package com.baizhi.wts.service;

import com.baizhi.wts.dao.BannerPageDto;
import com.baizhi.wts.entity.Banner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BannerServiceTest {
    @Autowired
     BannerService bannerService;
    //删除
    /*@Test
    public void testDelete(){
        bannerService.deletes("5");
    }*/
    //添加
    @Test
    public void testInsert(){
        Banner banner = new Banner(null, "a", "a", null, null, "ssss0", "1");
        bannerService.insert(banner);
    }
    //修改
    @Test
    public void testUpdate(){
        Banner banner = new Banner("2", "a", "a", null, null, "ssss0", "1");
        bannerService.update(banner);
    }
    //分页查
    @Test
    public void testQueryByPage(){
        BannerPageDto banners = bannerService.quryBypage(1, 2);
        System.out.println("banners = " + banners);
    }
}
