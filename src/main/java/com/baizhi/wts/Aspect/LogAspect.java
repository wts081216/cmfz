package com.baizhi.wts.Aspect;

import com.baizhi.wts.annotation.LogAnnotation;
import com.baizhi.wts.entity.Admin;
import com.baizhi.wts.entity.Log;
import com.baizhi.wts.service.LogService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect//切面（切入点+通知）
public class LogAspect {
    @Autowired
    private  HttpServletRequest request;
    @Autowired
    LogService logService;
    @Around(value = "pc1()")//通知
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed;
        System.out.println("===这里是前置通知===");

        // 获取执行操作的用户
        Admin curUser = (Admin) request.getSession(true).getAttribute("CurrentUser");
        String name = curUser.getUsername();
        // 获取执行的时间
        Date date = new Date();
        // 获取执行的结果
        Boolean flag = false;
        String result = null;
        // 获取执行的操作
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();  // 获取方法对象
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);// 获取注解对象
        String value = annotation.value();
        try {
            proceed = proceedingJoinPoint.proceed(); // 调用目标方法
            flag = true;
            return proceed;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            //log.info("执行操作的用户===>{}, 执行的时间===>{}, 执行的操作===>{}, 执行的结果===>{}", name, date, value, flag);
            if(flag == true)
                result = "成功";
            else
                result = "失败";
            Log log = new Log(null, name, value, date, result);
            logService.insert(log);
        }
    }
    //切入点
    @Pointcut(value = "@annotation(com.baizhi.wts.annotation.LogAnnotation)")
    public void pc1() {
    }
}
