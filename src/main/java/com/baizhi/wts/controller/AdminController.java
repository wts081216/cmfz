package com.baizhi.wts.controller;

import com.baizhi.wts.entity.Admin;
import com.baizhi.wts.service.AdminService;
import com.baizhi.wts.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("/login")
    @ResponseBody
    public String login(Admin admin,String clientCode){
        String message = adminService.selectOneByName(admin,clientCode);
        return  message;
    }

    //获取验证码
    // 获取验证码
    @RequestMapping("/serverCode")
    public void serverCode(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode vcode = new CreateValidateCode();
        String code = vcode.getCode();  // 随机验证码
        vcode.write(response.getOutputStream());    // 将图片输出到client
        // 往session中存命名属性
        session.setAttribute("serverCode", code);
    }
    //退出登录
    @RequestMapping("/exit")
    public String exit(HttpSession session){
        session.removeAttribute("CurrentUser");
        return  "redirect:/jsp/login.jsp";
    }

}
