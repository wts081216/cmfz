package com.baizhi.wts.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.wts.dao.BannerPageDto;
import com.baizhi.wts.entity.Banner;
import com.baizhi.wts.entity.BannerListener;
import com.baizhi.wts.service.BannerService;
import com.baizhi.wts.util.HttpUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    //分页查
    @RequestMapping("/queryByPage")
    @ResponseBody
    public BannerPageDto queryByPage(Integer rows, Integer page) {
        return bannerService.quryBypage(rows, page);
    }

    //添加 删除  修改
    @ResponseBody
    @RequestMapping("/multi")
    public Map multi(Banner banner, String oper, String[] id) {
        HashMap map = new HashMap();
        if ("add".equals(oper)) {
            String uuid = UUID.randomUUID().toString();
            banner.setId(uuid);
            map.put("bannerId", uuid);
            bannerService.insert(banner);
        } else if ("edit".equals(oper)) {
            if (banner.getUrl().equals("")) {
                Banner queryOneBanner = bannerService.queryOne(banner.getId());
                String url = queryOneBanner.getUrl();
                banner.setUrl(url);
                System.out.println("banner = " + banner);
                bannerService.update(banner);
            } else {
                String uuid = banner.getId();
                map.put("bannerId", uuid);
                //System.out.println("banner = " + banner);
                bannerService.update(banner);
            }
        } else {
            bannerService.deletes(Arrays.asList(id));
        }
        return map;
    }

    //图片上传
    @ResponseBody
    @RequestMapping("/uploadBanner")
    public void uploadBanner(HttpSession session, MultipartFile url, String bannerId) {
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            //创建多级目录
            file.mkdirs();
        }
        //防止文件重名
        String name = new Date().getTime() + "_" + url.getOriginalFilename();
        try {
            url.transferTo(new File(realPath, name));
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl(name);
            bannerService.update(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导出(文件下载)
    @SneakyThrows
    @RequestMapping("/bannerOut")
    public void  bannerOut(HttpServletResponse response,HttpSession session){
        String fileName = new Date().getTime()+".xlsx";
        ServletOutputStream outputStream = response.getOutputStream();
        // write()
        // 参数1:文件路径
        // 参数2:实体类.class sheet()指定写入工作簿的名称 doWrite(List数据) 写入操作
        // 如需下载使用 参数1:outputSteam 参数2:实体类.class
        List<Banner> banners = bannerService.queryAll();
        //设置响应头信息
        response.setHeader("Content-Disposition", "attachment; fileName="+ URLEncoder.encode(fileName, "utf-8"));
        EasyExcel.write(outputStream,Banner.class)//指定文件导出路径及样式
            .sheet("轮播图")//指定导出到哪个工作簿
            .doWrite(banners);//写出操做  准备数据
    }
    //导出模板(文件下载)
    @SneakyThrows
    @RequestMapping("/bannerMould")
    public void  bannerMould(HttpServletResponse response,HttpSession session){
        String fileName = new Date().getTime()+".xlsx";
        ServletOutputStream outputStream = response.getOutputStream();
        // write()
        // 参数1:文件路径
        // 参数2:实体类.class sheet()指定写入工作簿的名称 doWrite(List数据) 写入操作
        // 如需下载使用 参数1:outputSteam 参数2:实体类.class
        //设置响应头信息
        response.setHeader("Content-Disposition", "attachment; fileName="+ URLEncoder.encode(fileName, "utf-8"));
        EasyExcel.write(outputStream,Banner.class)//指定文件导出路径及样式
                .sheet("轮播图")//指定导出到哪个工作簿
                .doWrite(new ArrayList());//写出操做  准备数据
    }
    //导入 （文件上传）
    @RequestMapping("/bannerIn")
    @ResponseBody
    public void bannerIn(HttpServletRequest request,MultipartFile imag) throws IOException {
        //获取真实路径
        String realPath = request.getSession(true).getServletContext().getRealPath("/upload/excel/");
        //判断该文件是否存在
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //防止文件重名
        String newName = new Date().getTime() + "_" + imag.getOriginalFilename();
        //文件上传
        imag.transferTo(new File(realPath,newName));
        String fileName = realPath + newName;
         EasyExcel.read(fileName,Banner.class,new BannerListener()).sheet().doRead();
    }
}
