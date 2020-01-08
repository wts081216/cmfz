package com.baizhi.wts.dao;

import com.baizhi.wts.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminDaoTest {
    @Autowired
    AdminDao adminDao;

    @Test
    public void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        System.out.println("admins = " + admins);
    }
}
