package com.baizhi.wts.service;

import com.baizhi.wts.entity.MapDto;
import lombok.experimental.Accessors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    public void queryUserBylocation(){
        List<MapDto> mapDtos = userService.queryByLocation("男");
        for (MapDto mapDto : mapDtos) {
            System.out.println("mapDto ===================== " + mapDto);
        }
    }

    @Test
    public void queryUserByTime(){
        Integer user = userService.queryUserByTime("男", 7);
        System.out.println("user ======================= " + user);
    }
}
