package com.baizhi.wts.controller;

import com.baizhi.wts.entity.Album;
import com.baizhi.wts.entity.Article;
import com.baizhi.wts.service.ArticleService;
import com.baizhi.wts.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/article")
@Controller
public class ArticleController {
    @Autowired
    ArticleService articleService;

    //分页查
    @RequestMapping("/queryBypage")
    @ResponseBody
    public Map queryBypage(Integer page, Integer rows) {
        Map map = articleService.queryBypage(page, rows);
        return map;
    }

    //修改 删除 添加 文章
    @ResponseBody
    @RequestMapping("/edit")
    public Map edit(String oper, Article article, String[] id) {
        HashMap hashMap = new HashMap();
        //添加
        if (oper.equals("add")) {
            String uuid = UUID.randomUUID().toString();
            article.setId(uuid);
            articleService.add(article);
            hashMap.put("articleId", uuid);
        } else if (oper.equals("edit")) {
            //修改  判断是否修改图片
            if (article.getImg().equals("")) {
                article.setImg(null);
                articleService.update(article);
            } else {
                articleService.update(article);
                hashMap.put("id", article.getId());
            }
        } else {
            //删除
            articleService.delete(Arrays.asList(id));
        }
        return hashMap;
    }

    //添加
    @RequestMapping("/add")
    @ResponseBody
    public void add(Article article) {
        articleService.add(article);
    }

    @RequestMapping("/insertArticle")
    @ResponseBody
    public Map insertArticle(Article article, MultipartFile inputfile, HttpServletRequest request, HttpSession session) {
        HashMap hashMap = new HashMap();
        if (article.getId().equals("")) {
            // 添加
            //System.out.println("------------------------------------调用添加方法-------------------------------------------------------");
            String httpUrl = HttpUtil.getHttpUrl(inputfile, request, session, "/upload/articleImg/");
            article.setImg(httpUrl);
            articleService.add(article);
           // System.out.println("添加article ============================="+article);
            hashMap.put("error", 0);
            hashMap.put("httpUrl", httpUrl);
        } else {
            // 修改
            if (article.getImg() == null) {
                article.setImg(null);
                articleService.update(article);
             //   System.out.println("修改article ============================="+article);
                hashMap.put("error", 1);
            } else {
                String httpUrl = HttpUtil.getHttpUrl(inputfile, request, session, "/upload/articleImg/");
                articleService.update(article);
              //  System.out.println("修改article ============================="+article);
                hashMap.put("error", 0);
                hashMap.put("httpUrl", httpUrl);
            }
        }
        return hashMap;
    }

    @RequestMapping("/showAllImg")
    @ResponseBody
    public Map showAllImg(HttpServletRequest request, HttpSession session) {
        HashMap hashMap = new HashMap();
        hashMap.put("current_url", request.getContextPath() + "/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count", files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir", false);
            fileMap.put("has_file", false);
            fileMap.put("filesize", file1.length());
            fileMap.put("is_photo", true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype", extension);
            fileMap.put("filename", name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];
          /*
            创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            需要将String类型 转换为Long类型 Long.valueOf(str);
            */
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime", format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list", arrayList);
        return hashMap;
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request) {
        // 该方法需要返回的信息 error 状态码 0 成功 1失败   成功时 url 图片路径
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        // 网络路径
        try {
            String http = HttpUtil.getHttpUrl(imgFile, request, session, "/upload/articleImg/");
            hashMap.put("error", 0);
            hashMap.put("url", http);
        } catch (Exception e) {
            hashMap.put("error", 1);
            e.printStackTrace();
        }
        return hashMap;
    }

}
