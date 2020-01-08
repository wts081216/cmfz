package com.baizhi.wts.controller;

import com.baizhi.wts.entity.Guru;
import com.baizhi.wts.service.GuruService;
import com.baizhi.wts.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/guru")
public class GuruController {
    @Autowired
    GuruService guruService;
    //分页查
    @RequestMapping("/quryBypage")
    public Map quryBypage(Integer page,Integer rows){
        Map map = guruService.quryBypage(page, rows);
        return map;
    }
    //添加 删除 修改 上师
    @RequestMapping("/edit")
    public Map edit(String oper, String[] id, Guru guru){
        HashMap hashMap = new HashMap();
        //添加逻辑
        if(oper.equals("add")){
            String uuid = UUID.randomUUID().toString();
            guru.setId(uuid);
            guruService.insert(guru);
            hashMap.put("guruId",uuid);
            //修改逻辑
        }else if(oper.equals("edit")){
            if(guru.getPhoto().equals("")){
               // System.out.println("未选album = " + guru);
                guru.setPhoto(null);
                guruService.update(guru);
            }else {
                guruService.update(guru); // 数据库里存file:///C:/fakepath/%E5%BA%93%E8%A1%A8%E6%80%BB%E7%BB%93.png
              //  System.out.println("选中album = " + guru);
                hashMap.put("id",guru.getId());
            }
        }else {
            guruService.deletes(Arrays.asList(id));
        }
        return  hashMap;
    }
    //上传图片
    @RequestMapping("/upload")
    public void uoload(HttpSession session, HttpServletRequest request, MultipartFile photo,String guruId){
        String httpUrl = HttpUtil.getHttpUrl(photo, request, session, "/upload/guru/");
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/guru/");
        //获取文件名
        String[] split = httpUrl.split("/");
        String s = split[split.length - 1];
        File file = new File(realPath,s);
        Guru guru = new Guru();
        guru.setId(guruId);
        guru.setPhoto(httpUrl);
        guruService.update(guru);
    }
    //分页查
    @RequestMapping("/queryAll")
    public List<Guru> quryAll(){
        return  guruService.quryAll();
    }
}
