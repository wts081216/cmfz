package com.baizhi.wts.service;

import com.baizhi.wts.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceTest {
    @Autowired
    AdminService adminService;
    @Test
    public void testLogin(){
        Admin admin = new Admin(null,"admin","admi");
        String s = adminService.selectOneByName(admin,"1234");
        System.out.println("s = " + s);
    }

}
