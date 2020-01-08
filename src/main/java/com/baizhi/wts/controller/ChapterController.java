package com.baizhi.wts.controller;

import com.baizhi.wts.entity.Chapter;
import com.baizhi.wts.service.ChapterService;
import com.baizhi.wts.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.jta.WebSphereUowTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    //分页查
    @RequestMapping("/queryByPage")
    @ResponseBody
    public Map queryByPage(Integer page, Integer rows, Chapter chapter) {
        Map map = chapterService.quryBypage(page, rows, chapter);
        return map;
    }

    //增删改
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(String[] id, String oper, Chapter chapter) {
        HashMap hashMap = new HashMap();
        //添加
        if (oper.equals("add")) {
            String uuid = UUID.randomUUID().toString();
            chapter.setId(uuid);
            //chapter.setCreateTime(new Date());
            chapterService.insert(chapter);
            hashMap.put("chapterId", uuid);
            //修改
        } else if (oper.equals("edit")) {
            chapterService.update(chapter);
            hashMap.put("chapterId", chapter.getId());
        } else {
            //删除
            chapterService.deletes(Arrays.asList(id));
        }
        return hashMap;
    }

    //文件上传
    /*
     * 上传音频文件；计算音频时常、大小；更改网络路径
     *
     * */
    @RequestMapping("/upload")
    @ResponseBody
    // MultipartFile url(上传的文件),String chapterId(章节Id 更新使用)
    public void upload(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        //获取网络路径 http://localhost:8989/项目名/文件夹/文件名
        String httpUrl = HttpUtil.getHttpUrl(url, request, session, "/upload/chapter/");
        //获取/upload/chapter/真实路径
        String realPath = session.getServletContext().getRealPath("/upload/chapter/");
        //获取文件名
        String[] split = httpUrl.split("/");
        String s = split[split.length - 1];
        File file = new File(realPath,s);
        //获取文件大小
        long length = file.length();
        String size = length/1024/1024 + "MB";
        //获取音频文件时长
        MP3File read = (MP3File) AudioFileIO.read(file);
        //获取头文件
        MP3AudioHeader mp3AudioHeader = read.getMP3AudioHeader();
        //获取音频文件多少秒
        int trackLength = mp3AudioHeader.getTrackLength();
        String min = trackLength / 60 + "分";
        String sec = trackLength % 60 + "秒";
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setSize(size);
        chapter.setTime(min+sec);
        chapter.setUrl(httpUrl);
        chapterService.update(chapter);
    }
    //文件下载
    @RequestMapping("/download")
    public void upload(HttpSession session, HttpServletResponse response,String url) throws IOException {
        //处理路径找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/chapter/");
        String name = split[split.length - 1];
        File file = new File(realPath,name);
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(name, "utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        // FileUtils.copyFile("服务器文件",outputStream)
        FileUtils.copyFile(file,outputStream);
    }

}