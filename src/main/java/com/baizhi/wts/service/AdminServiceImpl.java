package com.baizhi.wts.service;

import com.baizhi.wts.annotation.LogAnnotation;
import com.baizhi.wts.dao.AdminDao;
import com.baizhi.wts.entity.Admin;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Autowired
    HttpSession session;

    @Override
    public String selectOneByName(Admin admin, String clientCode) {
        String message = null;
        //获取session中存储的字符
        String  serverCode = (String) session.getAttribute("serverCode");
        //判断验证码
        if (clientCode.equals(serverCode)) {
            Admin admin1 = adminDao.selectOne(new Admin(null, admin.getUsername(), null));
            //判断此人是否存在
            if (admin1 != null) {
                //此人存在 判断密码是否正确
                if (admin.getPassword().equals(admin1.getPassword())) {
                    session.setAttribute("CurrentUser",admin1);
                    message = "登陆成功";
                } else {
                    message = "密码不正确";

                }
            } else {
                message = "用户不存在";
            }
        } else {
            message = "验证码不正确";
        }
        return message;
    }
}
