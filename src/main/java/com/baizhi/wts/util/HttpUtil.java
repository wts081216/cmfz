package com.baizhi.wts.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class HttpUtil {
    public static String getHttpUrl(MultipartFile file,HttpServletRequest request, HttpSession session, String dir){
        //获取路径
        String realPath = session.getServletContext().getRealPath(dir);
        //判断文件是否存在
        File file1 = new File(realPath);
        if(!file1.exists()){
            file1.mkdirs();
        }
        //防止文件重名
        String originalFilename = file.getOriginalFilename();
        originalFilename = new Date().getTime()+"_"+originalFilename;
        try {
            file.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //网络路径 ：http://IP:端口/项目名/文件夹存放位置
        String http = request.getScheme();
        //获取IP地址
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口号
        Integer port = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        String url = http+"://"+localhost.split("/")[1]+":"+port+contextPath+dir+originalFilename;
        return url;
    }
}
