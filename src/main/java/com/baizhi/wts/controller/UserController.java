package com.baizhi.wts.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.baizhi.wts.entity.MapDto;
import com.baizhi.wts.entity.User;
import com.baizhi.wts.service.UserService;
import com.baizhi.wts.util.HttpUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    UserService userService;
    //分页查
    @RequestMapping("/queryByPage")
    @ResponseBody
    public Map queryByPage(Integer page ,Integer rows){
        Map map = userService.queryByPage(page, rows);
        return map;
    }
    //删除  添加 修改
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(String oper, String[] id, User user){
        HashMap hashMap = new HashMap();
        //添加逻辑
        if(oper.equals("add")){
            String uuid = UUID.randomUUID().toString();
            user.setId(uuid);
            userService.add(user);
            hashMap.put("userId",uuid);
            //修改逻辑
        }else if(oper.equals("edit")){
            if(user.getPhoto().equals("")){
                user.setPhoto(null);
                userService.update(user);
            }else {
                userService.update(user); // 数据库里存file:///C:/fakepath/%E5%BA%93%E8%A1%A8%E6%80%BB%E7%BB%93.png
                hashMap.put("id",user.getId());
            }
        }else {
            userService.delete(Arrays.asList(id));
        }
        return  hashMap;
    }
    //文件上传
    @ResponseBody
    @RequestMapping("/upload")
    public void upload(HttpSession session, HttpServletRequest request, MultipartFile photo,String userId){
        String httpUrl = HttpUtil.getHttpUrl(photo, request, session, "/upload/user/");
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/user/");
        //获取文件名
        String[] split = httpUrl.split("/");
        String s = split[split.length - 1];
        File file = new File(realPath,s);
        User user = new User();
        user.setId(userId);
        user.setPhoto(httpUrl);
        userService.update(user);
    }
    //根据用户注册时间，性别 进行查询
    @ResponseBody
    @RequestMapping("/showUserTime")
    public Map  showUserTime(){
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userService.queryUserByTime("男",1));
        manList.add(userService.queryUserByTime("男",7));
        manList.add(userService.queryUserByTime("男",30));
        manList.add(userService.queryUserByTime("男",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userService.queryUserByTime("女",1));
        womenList.add(userService.queryUserByTime("女",7));
        womenList.add(userService.queryUserByTime("女",30));
        womenList.add(userService.queryUserByTime("女",365));
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return  hashMap;
    }
    @ResponseBody
    @RequestMapping("addUser")
    public void adddUser(User user){
        user.setLocation("北京");
        user.setRigestDate(new Date());
        user.setSex("男");
        userService.add(user);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-79f070fddc974cd7bf77693de3bf4228");
        Map map = showUserTime();
        String jsonString = JSONObject.toJSONString(map);
        goEasy.publish("081216",jsonString);
    }
    //根据地区进行查询
    @ResponseBody
    @RequestMapping("/showUserByAddress")
    public Map queryByLocation(String sex){
        HashMap hashMap = new HashMap();
        List<MapDto> man = userService.queryByLocation("男");
        List<MapDto> women = userService.queryByLocation("女");
        hashMap.put("man",man);
        hashMap.put("women",women);
        return hashMap;
    }
}
