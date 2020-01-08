package com.baizhi.wts.service;

import com.baizhi.wts.entity.Chapter;
import com.baizhi.wts.entity.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class Test_LogServiceImpl {

    @Autowired
    private LogService logService;

    @Test
    public void selectByPage() {
        Map map = logService.queryByPage(10, 10);
        List<Log> rows = (List<Log>) map.get("rows");
        for (Log row : rows) {
            System.out.println("row = " + row);
        }
    }
}
