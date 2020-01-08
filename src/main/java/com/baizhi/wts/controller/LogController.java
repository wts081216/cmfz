package com.baizhi.wts.controller;

import com.baizhi.wts.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;
    //分页查
    @RequestMapping("/queryByPage")
    @ResponseBody
    public Map queryByPage( Integer rows,Integer page) {
        Map map = logService.queryByPage(rows, page);
        return map;
    }
}
