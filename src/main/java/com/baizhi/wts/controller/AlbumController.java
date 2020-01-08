package com.baizhi.wts.controller;

import com.baizhi.wts.entity.Album;
import com.baizhi.wts.entity.Banner;
import com.baizhi.wts.service.AlbumService;
import com.baizhi.wts.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    //分页查
    @RequestMapping("/queryBypage")
    @ResponseBody
    public Map queryBypage(Integer page, Integer rows){
        Map map = albumService.quryBypage(page, rows);

        return map;
    }

    //增删改
    @ResponseBody
    @RequestMapping("/editAlbum")
    public Map editAlbum(String[] id,Album album,String oper){
        HashMap hashMap = new HashMap();
        //添加逻辑
        if(oper.equals("add")){
            String uuid = UUID.randomUUID().toString();
            album.setId(uuid);
            albumService.insert(album);
            hashMap.put("albumId",uuid);
            //修改逻辑
        }else if(oper.equals("edit")){
            if(album.getCover().equals("")){
                System.out.println("未选album = " + album);
                album.setCover(null);
                albumService.update(album);
            }else {
                albumService.update(album); // 数据库里存file:///C:/fakepath/%E5%BA%93%E8%A1%A8%E6%80%BB%E7%BB%93.png
                System.out.println("选中album = " + album);
                hashMap.put("id",album.getId());
            }
        }else {
            albumService.deletes(Arrays.asList(id));
        }
        return  hashMap;
    }
    //图片上传
    @RequestMapping("/uploadCover")
    @ResponseBody
    public void uploadCover(HttpSession session, MultipartFile cover, HttpServletRequest request,String albumId){
        String httpUrl = HttpUtil.getHttpUrl(cover, request, session, "/upload/cover/");
        //获取/upload/chapter/真实路径
        String realPath = session.getServletContext().getRealPath("/upload/cover/");
        //获取文件名
        String[] split = httpUrl.split("/");
        String s = split[split.length - 1];
        File file = new File(realPath,s);
        Album album = new Album();
        album.setId(albumId);
        album.setCover(httpUrl);
        albumService.update(album);
    }
}
